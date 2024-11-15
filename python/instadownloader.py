import io
import random
import time
import urllib
from urllib import request

from aiogram import Bot, Dispatcher
from aiogram.filters import Command
from aiogram.types import Message
from selenium import webdriver
from selenium.webdriver.chrome.options import Options
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from webdriver_manager.chrome import ChromeDriverManager
from webdriver_manager.core.os_manager import ChromeType

# Initialize bot with token
TOKEN = "5870609720:AAHYF8TzuzdE65KkywO2cs1QA6d4CaDJAqc"
SESSION_ID = "pebbe9-haWjen-rijsaj"
bot = Bot(token=TOKEN)
dp = Dispatcher()

class ReelDownload:
    def __init__(self, reel_url):
        chrome_service = Service(ChromeDriverManager(chrome_type=ChromeType.CHROMIUM).install())
        chrome_options = Options()
        options = [
            "--headless",
            "--disable-gpu",
            "--window-size=1920,1200",
            "--ignore-certificate-errors",
            "--disable-extensions",
            "--no-sandbox",
            "--disable-dev-shm-usage"
        ]
        for option in options:
            chrome_options.add_argument(option)

        self.driver = webdriver.Chrome(service=chrome_service, options=chrome_options)
        self.wait_10 = WebDriverWait(self.driver, 10)
        self.reel_url = reel_url

    def download(self):
        try:
            reel_id = (self.reel_url.rsplit('/reel/', 1)[1]).rsplit('/', 1)[0]
            self.driver.get(f"https://www.instagram.com/reel/{reel_id}/")
            time.sleep(5)  # Give the page some time to load

            video_src = self.wait_10.until(
                EC.presence_of_element_located((By.XPATH,
                                                '/html/body/div[2]/div/div/div[2]/div/div/div[1]/section/main/div/div[1]/article/div/div[1]/div/div/div/div/div/div/div/video'))
            ).get_attribute('src')

            # Download the video into an in-memory buffer
            video_buffer = io.BytesIO()
            headers = {'User-Agent': random.choice([
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.182 Safari/537.36",
                "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Edge/88.0.705.81 Safari/537.36",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.192 Safari/537.36"
            ])}
            request_obj = urllib.request.Request(video_src, headers=headers)

            with urllib.request.urlopen(request_obj) as response:
                video_buffer.write(response.read())
            video_buffer.seek(0)  # Move to the beginning of the buffer
            return video_buffer
        except Exception as e:
            print("Something went wrong while downloading the reel.")
            print(e)
            return None
        finally:
            self.driver.quit()  # Close the browser


# Command to start the bot
@dp.message(Command(commands=["start"]))
async def start(message: Message):
    await message.answer("Send me a Reels URL, and I'll download it for you!")


# Handler for receiving Instagram Reels URL
@dp.message()
async def handle_message(message: Message):
    url = message.text
    await message.answer("Downloading the reel, please wait...")

    # Download the reel in memory
    downloader = ReelDownload(url)
    video_bytes = downloader.download()

    if video_bytes:
        # Send video as a file
        await bot.send_video(chat_id=message.chat.id, video=video_bytes)
        video_bytes.close()  # Clean up in-memory file
    else:
        await message.answer("Failed to download the reel. Please check the URL.")


async def main():
    # Start polling
    await dp.start_polling(bot)


if __name__ == "__main__":
    import asyncio

    asyncio.run(main())
