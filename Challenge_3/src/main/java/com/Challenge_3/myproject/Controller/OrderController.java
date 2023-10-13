package com.Challenge_3.myproject.Controller;

import com.Challenge_3.myproject.Model.*;
import com.Challenge_3.myproject.View.Tampilan;

import java.util.Optional;

public class OrderController {
    private Data data;
    private Tampilan tampilan;
    private boolean kembaliKeMenuUtama = false; // Tambahkan flag untuk kembali ke menu utama

    public OrderController(Data data, Tampilan tampilan) {
        this.data = data;
        this.tampilan = tampilan;
    }

    public void jalankanAplikasi() {
        int totalHarga = 0;

        while (true) {
            int pilihanMenu;

            if (!kembaliKeMenuUtama) {
                pilihanMenu = tampilan.tampilkanMenu();
            } else {
                pilihanMenu = 99; // Menu otomatis berpindah ke Pesan dan Bayar setelah input "Y"
                kembaliKeMenuUtama = false; // Reset flag
            }

            if (pilihanMenu == 0) {
                System.exit(0);
            } else if (pilihanMenu == 99) {
                PesanController pesanController = new PesanController(data, tampilan);
                pesanController.prosesPesanan(totalHarga);
            } else {
                MenuListController menuController = new MenuListController(tampilan);
                Optional<MenuItem> item = menuController.getItemByMenuNumber(pilihanMenu);
                if (item.isPresent()) {
                    PesanController pesanController = new PesanController(data, tampilan);
                    totalHarga = pesanController.pesanItemMenu(item.get(), totalHarga);
                } else {
                    tampilan.tampilkanPesan("Pilihan tidak valid. Silakan coba lagi.");
                }
            }
        }
    }
}