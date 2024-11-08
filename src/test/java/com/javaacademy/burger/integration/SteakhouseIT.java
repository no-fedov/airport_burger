package com.javaacademy.burger.integration;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.PayTerminal;
import com.javaacademy.burger.Paycheck;
import com.javaacademy.burger.Steakhouse;
import com.javaacademy.burger.Waitress;
import com.javaacademy.burger.dish.Dish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static com.javaacademy.burger.Currency.MOZAMBICAN_DOLLARS;
import static com.javaacademy.burger.Currency.RUB;
import static com.javaacademy.burger.Currency.USD;
import static com.javaacademy.burger.dish.DishType.BURGER;
import static com.javaacademy.burger.dish.DishType.FRIED_POTATO;
import static com.javaacademy.burger.dish.DishType.RIBS;
import static org.mockito.ArgumentMatchers.any;

public class SteakhouseIT {
    @Test
    @DisplayName("Клиент купил бургер за рубли. Успешная оплата")
    public void clientBoughtBurgerForRubles() {
        //given
        Waitress waitress = new Waitress();
        Kitchen kitchen = new Kitchen();
        PayTerminal payTerminal = new PayTerminal();
        Steakhouse steakhouse = new Steakhouse(waitress, kitchen, payTerminal);
        Paycheck referenceCheck = new Paycheck(BigDecimal.valueOf(300), RUB, BURGER);

        //when
        Paycheck paycheck = steakhouse.makeOrder(BURGER, RUB);
        Dish dish = steakhouse.takeOrder(paycheck);

        //then
        Assertions.assertEquals(referenceCheck, paycheck);
        Assertions.assertEquals(BURGER, dish.getDishType());
    }

    @Test
    @DisplayName("Санэпидемстанция делат заказ ребер")
    public void sanitaryInspectionStationOrderRibs() {
        //given
        Waitress waitress = new Waitress();
        Kitchen kitchen = new Kitchen();
        PayTerminal payTerminalMock = Mockito.mock(PayTerminal.class);
        Steakhouse steakhouse = new Steakhouse(waitress, kitchen, payTerminalMock);
        Mockito.when(payTerminalMock.pay(RIBS, RUB))
                .thenReturn(new Paycheck(BigDecimal.valueOf(700), RUB, RIBS));

        //when
        Paycheck paycheck = steakhouse.makeOrder(RIBS, RUB);
        Dish dish = steakhouse.takeOrder(paycheck);

        //then
        Assertions.assertEquals(BigDecimal.valueOf(700), paycheck.getTotalAmount());
        Assertions.assertEquals(RUB, paycheck.getCurrency());
        Assertions.assertEquals(RIBS, paycheck.getDishType());
        Assertions.assertEquals(RIBS, dish.getDishType());
    }

    @Test
    @DisplayName("Налоговая проверяет оплату рублями при заказе ребрышек")
    public void taxServiceCheckPaymentInRublesWhenOrderingRibsSuccess() {
        //given
        Waitress waitressMock = Mockito.mock(Waitress.class);
        Mockito.when(waitressMock.giveOrderToKitchen(any(), any())).thenReturn(true);
        Kitchen kitchenMock = Mockito.mock(Kitchen.class);
        PayTerminal payTerminalSpy = Mockito.spy(PayTerminal.class);
        Steakhouse steakhouse = new Steakhouse(waitressMock, kitchenMock, payTerminalSpy);
        Paycheck referenceCheck = new Paycheck(BigDecimal.valueOf(700), RUB, RIBS);

        //when
        Paycheck paycheck = steakhouse.makeOrder(RIBS, RUB);

        //then
        Assertions.assertEquals(referenceCheck, paycheck);
    }

    @Test
    @DisplayName("Налоговая проверяет оплату картошки долларами")
    public void taxServiceChecksPaymentPotatoesInDollarsSuccess() {
        //given
        Waitress waitressMock = Mockito.mock(Waitress.class);
        Mockito.when(waitressMock.giveOrderToKitchen(any(), any())).thenReturn(true);
        Kitchen kitchenMock = Mockito.mock(Kitchen.class);
        PayTerminal payTerminalSpy = Mockito.spy(PayTerminal.class);
        Paycheck referenceCheck = new Paycheck(BigDecimal.ONE, USD, FRIED_POTATO);
        Mockito.doReturn(referenceCheck)
                .when(payTerminalSpy).pay(FRIED_POTATO, USD);
        Steakhouse steakhouse = new Steakhouse(waitressMock, kitchenMock, payTerminalSpy);

        //when
        Paycheck paycheck = steakhouse.makeOrder(FRIED_POTATO, USD);

        //then
        Assertions.assertEquals(referenceCheck, paycheck);
    }

    @Test
    @DisplayName("Налоговая проверяет оплату бургера за мозамбикские доллары")
    public void taxServiceChecksPaymentBurgerInMozambicanDollarsSuccess() {
        //given
        Waitress waitressMock = Mockito.mock(Waitress.class);
        Mockito.when(waitressMock.giveOrderToKitchen(any(), any())).thenReturn(true);
        Kitchen kitchenMock = Mockito.mock(Kitchen.class);
        PayTerminal payTerminalSpy = Mockito.spy(PayTerminal.class);
        Paycheck referenceCheck = new Paycheck(BigDecimal.ONE, MOZAMBICAN_DOLLARS, BURGER);
        Mockito.doReturn(referenceCheck)
                .when(payTerminalSpy).pay(BURGER, MOZAMBICAN_DOLLARS);
        Steakhouse steakhouse = new Steakhouse(waitressMock, kitchenMock, payTerminalSpy);

        //when
        Paycheck paycheck = steakhouse.makeOrder(BURGER, MOZAMBICAN_DOLLARS);

        //then
        Assertions.assertEquals(referenceCheck, paycheck);
    }
}
