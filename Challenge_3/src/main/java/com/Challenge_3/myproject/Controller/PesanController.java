package com.Challenge_3.myproject.Controller;

import com.Challenge_3.myproject.Model.*;
import com.Challenge_3.myproject.View.Tampilan;

import java.util.ArrayList;
import java.util.List;

public class PesanController {
    private Data data;
    private Tampilan tampilan;

    public PesanController(Data data, Tampilan tampilan) {
        this.data = data;
        this.tampilan = tampilan;
    }

    public int pesanItemMenu(MenuItem item, int totalHarga) {
        int jumlah = tampilan.getJumlahPesanan(item);
        if (jumlah > 0) {
            int totalHargaItem = item.getHarga() * jumlah;
            boolean pesananAda = false;
            String catatan = tampilan.getCatatanPesanan();

            for (Pesanan pesananItem : data.getPesananList()) {
                if (pesananItem.getNama().equals(item.getNama())) {
                    pesananItem.setJumlah(pesananItem.getJumlah() + jumlah);
                    pesananItem.setCatatan(catatan);
                    pesananAda = true;
                    break;
                }
            }

            if (!pesananAda) {
                Pesanan pesanan = new Pesanan(item.getNama(), item.getHarga(), jumlah, catatan);
                data.tambahPesanan(pesanan);
            }

            totalHarga += totalHargaItem;
        } else {
            tampilan.tampilkanPesan("Jumlah pesanan tidak valid. Silakan coba lagi.");
        }
        return totalHarga;
    }




    public int prosesPesanan(int totalHarga) {
        List<Pesanan> pesananData = data.getPesananList();
        if (pesananData.isEmpty()) {
            tampilan.tampilkanPesananTidakAda();
        } else {
            totalHarga = pesananData.stream()
                    .mapToInt(pesananItem -> pesananItem.getHarga() * pesananItem.getJumlah())
                    .sum();

            ArrayList<String> pesananList = new ArrayList<>();
            ArrayList<Integer> hargaMakananList = new ArrayList<>();
            ArrayList<Integer> qtyMakananList = new ArrayList();

            pesananData.forEach(pesananItem -> {
                pesananList.add(pesananItem.getNama());
                hargaMakananList.add(pesananItem.getHarga());
                qtyMakananList.add(pesananItem.getJumlah());
            });

            tampilan.cetakPesanan(pesananList, hargaMakananList, qtyMakananList, totalHarga);

            int konfirmasi = tampilan.konfirmasiPembayaran();
            if (konfirmasi == 1) {
                tampilan.cetakNota(pesananList, hargaMakananList, qtyMakananList, totalHarga);
                tampilan.tampilkanPesan("Terima kasih atas pembayaran Anda.");
                System.exit(0);
            } else if (konfirmasi == 0) {
                System.exit(0);
            }
        }
        return totalHarga;
    }

}