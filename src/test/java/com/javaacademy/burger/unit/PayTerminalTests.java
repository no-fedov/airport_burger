package com.javaacademy.burger.unit;

import com.javaacademy.burger.PayTerminal;
import com.javaacademy.burger.Paycheck;
import com.javaacademy.burger.exception.NotAcceptedCurrencyException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.javaacademy.burger.Currency.MOZAMBICAN_DOLLARS;
import static com.javaacademy.burger.Currency.RUB;
import static com.javaacademy.burger.dish.DishType.BURGER;

public class PayTerminalTests {
    @Test
    @DisplayName("Успешная оплата бургера в рублях")
    public void paymentBurgerInRublesSuccess() {
        //given
        PayTerminal payTerminal = new PayTerminal();
        Paycheck referenceCheck = new Paycheck(BigDecimal.valueOf(300), RUB, BURGER);

        //when
        Paycheck paycheck = payTerminal.pay(BURGER, RUB);

        //then
        Assertions.assertEquals(referenceCheck, paycheck);
    }

    @Test
    @DisplayName("Неуспешная оплата бургера в мозамбикских долларах")
    public void paymentBurgerInMozambicanDollarsFailure() {
        //given
        PayTerminal payTerminal = new PayTerminal();

        //then
        Assertions.assertThrows(NotAcceptedCurrencyException.class, () -> payTerminal.pay(BURGER, MOZAMBICAN_DOLLARS));
    }
}
