package view;

import java.util.Scanner;
import model.MenuItem;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Tampilan {
    private Scanner input;
    private ArrayList<String> pesanan = new ArrayList<>();
    private ArrayList<Integer> hargaMakanan = new ArrayList<>();
    private ArrayList<Integer> qtyMakanan = new ArrayList<>();
    private int totalHarga = 0;

    public Tampilan() {
        input = new Scanner(System.in);
    }

    public int tampilkanMenu() {
        System.out.println("===========================");
        System.out.println("Selamat datang di BinaryFud");
        System.out.println("===========================");
        System.out.println("1. Nasi Goreng    | 15.000");
        System.out.println("2. Mie Goreng     | 13.000");
        System.out.println("3. Nasi + Ayam    | 18.000");
        System.out.println("4. Es Teh Manis   | 3.000");
        System.out.println("5. Es Jeruk       | 5.000");
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
        System.out.print("\nTentukan pilihan anda : ");
        return input.nextInt();
    }

    public int getJumlahPesanan(MenuItem item) {
        int jumlah = 0;
        boolean isValidInput = false;

        do {
            System.out.println("==========================");
            System.out.println("Berapa pesanan anda");
            System.out.println("==========================");
            System.out.println(item.getNama() + "  | " + formatHarga(item.getHarga()));
            System.out.println("(input 0 untuk kembali)");
            System.out.print("qty => ");

            try {
                jumlah = input.nextInt();
                isValidInput = true;
            } catch (java.util.InputMismatchException e) {
                input.nextLine();
                tampilkanPesan("Mohon masukkan angka yang valid.");
            }
        } while (!isValidInput);

        return jumlah;
    }

    public String getCatatanPesanan() {
        System.out.print("Masukkan catatan pesanan (opsional): ");
        input.nextLine();
        return input.nextLine();
    }


    public void tampilkanPesan(String pesan) {
        System.out.println(pesan);
    }

    public void tampilkanPesananTidakAda(){
        System.out.println("==========================");
        System.out.println("Minimal 1 jumlah pesanan!");
        System.out.println("==========================");
    }

    public void tampilkanKeluarAplikasi() {
        System.out.println("Terima kasih telah menggunakan aplikasi BinaryFud. Selamat tinggal!");
    }

    private String formatHarga(int harga) {
        return "Rp " + String.format("%,d", harga);
    }

    public void cetakPesanan(ArrayList<String> pesanan, ArrayList<Integer> hargaMakanan, ArrayList<Integer> qtyMakanan, int totalHarga) {
        if (!pesanan.isEmpty()) {
            System.out.println("Pesanan Anda:");
            int totalQty = qtyMakanan.stream().mapToInt(Integer::intValue).sum();

            for (int i = 0; i < pesanan.size(); i++) {
                String namaMakanan = pesanan.get(i);
                int hargaMakananItem = hargaMakanan.get(i);
                int qty = qtyMakanan.get(i);
                int totalHargaItem = hargaMakananItem * qty;
                System.out.println(namaMakanan + "      " + qty + "     " + formatHarga(totalHargaItem));
            }
            System.out.println("--------------------------------+");
            System.out.println("Total       " + totalQty + "       " + formatHarga(totalHarga));
        }
    }

    public int konfirmasiPembayaran() {
        System.out.println("===========================");
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println("===========================");
        cetakPesanan(pesanan, hargaMakanan, qtyMakanan, totalHarga);
        System.out.println("\n1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
        System.out.print("=> ");
        return input.nextInt();
    }

    public void cetakNota(ArrayList<String> pesanan, ArrayList<Integer> hargaMakanan, ArrayList<Integer> qtyMakanan, int totalHarga) {
        try {
            BufferedWriter nota = new BufferedWriter(new FileWriter("Nota.txt"));
            nota.write("===========================\n");
            nota.write("BinarFud\n");
            nota.write("===========================\n");
            nota.write("\nTerima kasih sudah memesan\ndi BinarFud\n\n");
            nota.write("Dibawah ini adalah pesanan anda\n\n");
            int totalQty = qtyMakanan.stream().mapToInt(Integer::intValue).sum();
            for (int i = 0; i < pesanan.size(); i++) {
                String namaMakanan = pesanan.get(i);
                int hargaMakananItem = hargaMakanan.get(i);
                int qty = qtyMakanan.get(i);
                int totalHargaItem = hargaMakananItem * qty;
                nota.write(namaMakanan + "      " + qty + "     " + formatHarga(totalHargaItem) + "\n");
            }
            nota.write("--------------------------------+\n");
            nota.write("Total            " + totalQty + "     " + formatHarga(totalHarga) + "\n\n");
            nota.write("Pembayaran : BinarCash \n\n");
            nota.write("===========================\n");
            nota.write("Simpan struk ini sebagai\n");
            nota.write("bukti pembayaran\n");
            nota.write("===========================\n");
            nota.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
