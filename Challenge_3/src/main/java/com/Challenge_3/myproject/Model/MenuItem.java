package com.Challenge_3.myproject.Model;

public class MenuItem {
    private String nama;
    private int harga;

    public MenuItem(String nama, int harga) {
        this.nama = nama;
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }
}
