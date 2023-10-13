package com.Challenge_3.myproject.Controller;

import com.Challenge_3.myproject.Model.*;
import com.Challenge_3.myproject.View.Tampilan;

import java.util.Optional;

public class MenuListController {
    private Tampilan tampilan;

    private static final MenuItem[] menuItems = {
            new Makanan("Nasi Goreng", 15000),
            new Makanan("Mie Goreng", 13000),
            new Makanan("Nasi + Ayam", 18000),
            new Minuman("Es Teh Manis", 3000),
            new Minuman("Es Jeruk", 5000)
    };

    public MenuListController(Tampilan tampilan) {
        this.tampilan = tampilan;
    }

    public Optional<MenuItem> getItemByMenuNumber(int menuNumber) {
        if (menuNumber >= 1 && menuNumber <= menuItems.length) {
            return Optional.of(menuItems[menuNumber - 1]);
        } else {
            return Optional.empty();
        }
    }

}