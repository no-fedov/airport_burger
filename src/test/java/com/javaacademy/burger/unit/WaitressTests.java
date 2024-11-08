package com.javaacademy.burger.unit;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.Waitress;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.javaacademy.burger.dish.DishType.BURGER;
import static com.javaacademy.burger.dish.DishType.FUAGRA;

public class WaitressTests {
    @Test
    @DisplayName("Официант принял заказ на бургер")
    public void waitressTakeOrderOnBurgerSuccess() {
        //given
        Kitchen kitchen = Mockito.mock(Kitchen.class);
        Waitress waitress = new Waitress();

        //when
        boolean result = waitress.giveOrderToKitchen(BURGER, kitchen);

        //then
        Assertions.assertTrue(result);
    }

    @Test@DisplayName("У официанта заказали фуагра, он не принял заказ")
    public void waitressTakeOrderOnFuagraFailure() {
        //given
        Kitchen kitchen = Mockito.mock(Kitchen.class);
        Waitress waitress = new Waitress();

        //when
        boolean result = waitress.giveOrderToKitchen(FUAGRA, kitchen);

        //then
        Assertions.assertFalse(result);
    }
}
