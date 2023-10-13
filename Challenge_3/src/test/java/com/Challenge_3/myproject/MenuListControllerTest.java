package com.Challenge_3.myproject;

import com.Challenge_3.myproject.Controller.MenuListController;
import com.Challenge_3.myproject.Model.MenuItem;
import com.Challenge_3.myproject.View.Tampilan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MenuListControllerTest {

    private MenuListController menuListController;

    @BeforeEach
    void setUp() {
        Tampilan tampilan = mock(Tampilan.class);
        menuListController = new MenuListController(tampilan);
    }

    @Test
    void testGetItemByMenuNumberValid() {
        int menuNumber = 1;
        Optional<MenuItem> menuItem = menuListController.getItemByMenuNumber(menuNumber);
        assertEquals("Nasi Goreng", menuItem.get().getNama());
    }

    @Test
    void testGetItemByMenuNumberInvalid() {
        int menuNumber = 0; // Invalid menu number
        Optional<MenuItem> menuItem = menuListController.getItemByMenuNumber(menuNumber);
        assertEquals(Optional.empty(), menuItem);
    }

    void testGetItemByMenuNumberOutOfRange() {
        int menuNumber = 10; // Menu number out of range
        Optional<MenuItem> menuItem = menuListController.getItemByMenuNumber(menuNumber);
        assertEquals(Optional.empty(), menuItem);
    }

    @Test
    void testGetItemByMenuNumberNegative() {
        int menuNumber = -1; // Negative menu number
        Optional<MenuItem> menuItem = menuListController.getItemByMenuNumber(menuNumber);
        assertEquals(Optional.empty(), menuItem);
    }

    @Test
    void testGetItemByMenuNumberUpperBound() {
        int menuNumber = 5; // Menu number at the upper bound
        Optional<MenuItem> menuItem = menuListController.getItemByMenuNumber(menuNumber);
        assertEquals("Es Jeruk", menuItem.get().getNama());
    }

    @Test
    void testGetItemByMenuNumberMiddleValue() {
        int menuNumber = 3; // Menu number in the middle
        Optional<MenuItem> menuItem = menuListController.getItemByMenuNumber(menuNumber);
        assertEquals("Nasi + Ayam", menuItem.get().getNama());
    }

    @Test
    void testGetItemByMenuNumberMultipleValid() {
        int menuNumber1 = 1;
        int menuNumber2 = 4;

        Optional<MenuItem> menuItem1 = menuListController.getItemByMenuNumber(menuNumber1);
        Optional<MenuItem> menuItem2 = menuListController.getItemByMenuNumber(menuNumber2);

        assertEquals("Nasi Goreng", menuItem1.get().getNama());
        assertEquals("Es Teh Manis", menuItem2.get().getNama());
    }


}
