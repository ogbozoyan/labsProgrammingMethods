def calculate_annuity_payment(P, r, n):
    """
    Расчет аннуитетного платежа.
    P - сумма кредита,
    r - месячная процентная ставка,
    n - количество месяцев.
    """
    return P * (r * (1 + r)**n) / ((1 + r)**n - 1)

P = 146000  # Сумма кредита
annual_interest_rate = 29  # Годовая процентная ставка
months = 5 * 12  # Количество месяцев

monthly_interest_rate = annual_interest_rate / 12 /100  # Месячная процентная ставка

annuity_payment = calculate_annuity_payment(P, monthly_interest_rate, months)
print(f"Ежемесячный аннуитетный платеж: {annuity_payment:.2f} руб.")
