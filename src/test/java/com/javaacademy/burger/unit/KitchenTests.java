package com.javaacademy.burger.unit;

import com.javaacademy.burger.Kitchen;
import com.javaacademy.burger.dish.Dish;
import com.javaacademy.burger.exception.KitchenHasNoGasException;
import com.javaacademy.burger.exception.UnsupportedDishTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.javaacademy.burger.dish.DishType.BURGER;
import static com.javaacademy.burger.dish.DishType.FUAGRA;

public class KitchenTests {
    @Test
    @DisplayName("Успешное приготовление бургера")
    public void cookBurgerSuccess() {
        //given
        Kitchen kitchen = new Kitchen();

        //when
        kitchen.cook(BURGER);

        //then
        Dish cookedBurger = kitchen.getCompletedDishes().get(BURGER).peek();
        Assertions.assertNotNull(cookedBurger);
        Assertions.assertEquals(BURGER, cookedBurger.getDishType());
    }

    @Test
    @DisplayName("Приготовление бургера, на кухне выключили газ")
    public void cookBurgerFailed() {
        //given
        Kitchen kitchen = new Kitchen();

        //when
        kitchen.setHasGas(false);

        //then
        Assertions.assertThrows(KitchenHasNoGasException.class, () -> kitchen.cook(BURGER));
    }

    @Test
    @DisplayName("Приготовление фуагра. Безуспешно")
    public void cookFuagraFailed() {
        //given
        Kitchen kitchen = new Kitchen();

        //then
        Assertions.assertThrows(UnsupportedDishTypeException.class, () -> kitchen.cook(FUAGRA));
    }
}
