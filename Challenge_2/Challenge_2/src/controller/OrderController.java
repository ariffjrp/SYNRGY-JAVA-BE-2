package controller;

import model.*;
import view.Tampilan;

import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private Data data;
    private Tampilan tampilan;

    public OrderController(Data data, Tampilan tampilan) {
        this.data = data;
        this.tampilan = tampilan;
    }

    public void jalankanAplikasi() {
        int pilihanMenu;
        int totalHarga = 0;

        do {
            pilihanMenu = tampilan.tampilkanMenu();

            if (pilihanMenu == 0) {
                System.exit(0);
            }

            if (pilihanMenu >= 1 && pilihanMenu <= 5) {
                MenuItem item = null;
                String catatan = tampilan.getCatatanPesanan();

                switch (pilihanMenu) {
                    case 1:
                        item = new Makanan("Nasi Goreng", 15000);
                        break;
                    case 2:
                        item = new Makanan("Mie Goreng", 13000);
                        break;
                    case 3:
                        item = new Makanan("Nasi + Ayam", 18000);
                        break;
                    case 4:
                        item = new Minuman("Es Teh Manis", 3000);
                        break;
                    case 5:
                        item = new Minuman("Es Jeruk", 5000);
                        break;
                }

                if (item != null) {
                    int jumlah = tampilan.getJumlahPesanan(item);

                    if (jumlah > 0) {
                        int totalHargaItem = item.getHarga() * jumlah;
                        Pesanan pesanan = new Pesanan(item.getNama(), item.getHarga(), jumlah, catatan);
                        data.tambahPesanan(pesanan);
                        totalHarga += totalHargaItem;
                    } else {
                        tampilan.tampilkanPesan("Jumlah pesanan tidak valid. Silakan coba lagi.");
                    }
                } else {
                    tampilan.tampilkanPesan("Pilihan tidak valid. Silakan coba lagi.");
                }
            } else if (pilihanMenu == 99) {
                List<Pesanan> pesananData = data.getPesananList();
                if (pesananData.isEmpty()) {
                    tampilan.tampilkanPesananTidakAda();
                    continue;
                }

                ArrayList<String> pesananList = new ArrayList<>();
                ArrayList<Integer> hargaMakananList = new ArrayList<>();
                ArrayList<Integer> qtyMakananList = new ArrayList<>();

                for (Pesanan pesananItem : pesananData) {
                    pesananList.add(pesananItem.getNama());
                    hargaMakananList.add(pesananItem.getHarga());
                    qtyMakananList.add(pesananItem.getJumlah());
                }

                tampilan.cetakPesanan(pesananList, hargaMakananList, qtyMakananList, totalHarga);

                int konfirmasi = tampilan.konfirmasiPembayaran();
                if (konfirmasi == 1) {
                    tampilan.cetakNota(pesananList, hargaMakananList, qtyMakananList, totalHarga);
                    tampilan.tampilkanPesan("Terima kasih atas pembayaran Anda.");
                    break;
                } else if (konfirmasi == 0) {
                    break;
                }
            } else {
                tampilan.tampilkanPesan("Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (true);

        tampilan.tampilkanKeluarAplikasi();
    }
}
