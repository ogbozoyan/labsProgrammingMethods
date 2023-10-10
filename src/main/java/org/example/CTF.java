package org.example;

import java.util.Base64;

/**
 * @author ogbozoyan
 * @since 09.10.2023
 */
public class CTF {
    public static void main(String[] var0) {
        //H
        if (var0.length != 1) {
        } else {
            String key = "HKIo-KOiR-NrC3-RvJK";

            String var1 = key;
            if (var1.charAt(0) != 'H') {
                System.out.println("[!] invalid serial key");
            } else if ((var1.charAt(1) ^ 50) != 121) {
                System.out.println("[!] invalid serial key");
            } else if (var1.charAt(2) + 28 != 101) {
                System.out.println("[!] invalid serial key");
            } else if (var1.charAt(3) - 2 != 109) {
                System.out.println("[!] invalid serial key. First block check failed");
            } else if (var1.charAt(4) != '-') {
                System.out.println("[!] invalid serial key. Wrong delimiter");
            } else if (var1.charAt(5) != var1.charAt(2) + 2) {
                System.out.println("[!] invalid serial key");
            } else if (var1.charAt(6) > 'N' && var1.charAt(6) < 'P') {
                if (var1.charAt(7) << 1 != 210) {
                    System.out.println("[!] invalid serial key");
                } else if (var1.charAt(8) >>> 1 == 41 && var1.charAt(8) - 7 != 76) {
                    if (var1.charAt(9) != '-') {
                        System.out.println("[!] invalid serial key. Wrong delimiter");
                    } else if (String.valueOf(var1.charAt(10)).hashCode() != 78) {
                        System.out.println("[!] invalid serial key");
                    } else if ((var1.charAt(11) | 170) == 250 && var1.charAt(11) < 'x' && var1.charAt(11) > 'p') {
                        if (var1.charAt(12) != 'C') {
                            System.out.println("[!] invalid serial key");
                        } else if (var1.charAt(13) != Base64.getDecoder().decode("Mw==")[0]) {
                            System.out.println("[!] invalid serial key. Third block check failed");
                        } else if (var1.charAt(14) != '-') {
                            System.out.println("[!] invalid serial key. Wrong delimiter");
                        } else if (var1.charAt(15) != (char) (var1.charAt(7) * 2 % 128)) {
                            System.out.println("[!] invalid serial key");
                        } else if ((var1.charAt(16) | 240) == 246 && var1.charAt(16) < 'z' && var1.charAt(16) > 'h') {
                            if ((var1.charAt(17) ^ 63) != 'u') {
                                System.out.println("[!] invalid serial key");
                            } else if ((var1.charAt(17) << 8) + var1.charAt(18) != 19019) {
                                System.out.println("[!] invalid serial key. Fourth block check failed");
                            } else {
                                System.out.println("Congratulations! The serial key is correct. Here your flag: joker{" + var1 + "}");
                            }
                        } else {
                            System.out.println("[!] invalid serial key");
                        }
                    } else {
                        System.out.println("[!] invalid serial key");
                    }
                } else {
                    System.out.println("[!] invalid serial key. Second block check failed");
                }
            } else {
                System.out.println("[!] invalid serial key");
            }
        }
    }
}
