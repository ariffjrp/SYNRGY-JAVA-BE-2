package view;

import java.util.Scanner;

import model.MenuItem;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import Utils.Konstan;

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
        System.out.println(Konstan.LINE_SEPARATOR);
        System.out.println("Selamat datang di BinaryFud");
        System.out.println(Konstan.LINE_SEPARATOR);
        System.out.println("1. Nasi Goreng    | 15.000");
        System.out.println("2. Mie Goreng     | 13.000");
        System.out.println("3. Nasi + Ayam    | 18.000");
        System.out.println("4. Es Teh Manis   | 3.000");
        System.out.println("5. Es Jeruk       | 5.000");
        System.out.println("99. Pesan dan Bayar");
        System.out.println("0. Keluar aplikasi");
        System.out.print( Konstan.NEW_LINE + "Tentukan pilihan anda : ");
        return input.nextInt();
    }

    public int getJumlahPesanan(MenuItem item) {
        int jumlah = 0;
        boolean isValidInput = false;

        do {
            System.out.println(Konstan.LINE_SEPARATOR);
            System.out.println("Berapa pesanan anda");
            System.out.println(Konstan.LINE_SEPARATOR);
            System.out.println(item.getNama() + Konstan.TAB + Konstan.PAGAR + Konstan.TAB + formatHarga(item.getHarga()));
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

    public void tampilkanPesananTidakAda() {
        System.out.println(Konstan.LINE_SEPARATOR);
        System.out.println("Minimal 1 jumlah pesanan!");
        System.out.println(Konstan.LINE_SEPARATOR);
        Scanner scanner = new Scanner(System.in);

        String pilihan = "";

        while (!pilihan.equals("Y") && !pilihan.equals("N")) {
            System.out.println("Mohon masukkan input:");
            System.out.println("(Y) Untuk lanjut");
            System.out.println("(N) Untuk keluar");
            System.out.print("=> ");

            pilihan = scanner.nextLine().toUpperCase();

            if (!pilihan.equals("Y") && !pilihan.equals("N")) {
                System.out.println("Mohon masukkan input yang valid (Y/N).");
            }
        }

        if (pilihan.equals("N")) {
            System.exit(0);
        }
    }

    private String formatHarga(int harga) {
        return "Rp " + String.format("%,d", harga);
    }

    public void cetakPesanan(ArrayList<String> pesanan, ArrayList<Integer> hargaMakanan, ArrayList<Integer> qtyMakanan, int totalHarga) {
        if (!pesanan.isEmpty()) {
            System.out.println("Pesanan Anda:");
            int totalQty = 0;

            for (int i = 0; i < pesanan.size(); i++) {
                String namaMakanan = pesanan.get(i);
                int hargaMakananItem = hargaMakanan.get(i);
                int qty = qtyMakanan.get(i);

                // Ambil catatan tambahan dari objek Pesanan
                String catatanTambahan = ""; // Tambahkan ini untuk catatan (jika ada)

                String catatan = catatanTambahan.isEmpty() ? "" : " (" + catatanTambahan + ")"; // Tambahkan catatan jika ada

                int totalHargaItem = hargaMakananItem * qty;
                System.out.println(namaMakanan + " (x" + qty + ")" + catatan + Konstan.TAB + formatHarga(totalHargaItem));
                totalQty += qty;
            }

            System.out.println(Konstan.PLUS_LINE);
            System.out.println("Total" + Konstan.TAB + totalQty + Konstan.TAB + formatHarga(totalHarga));
        }
    }

    public int konfirmasiPembayaran() {
        System.out.println(Konstan.LINE_SEPARATOR);
        System.out.println("Konfirmasi & Pembayaran");
        System.out.println(Konstan.LINE_SEPARATOR);
        cetakPesanan(pesanan, hargaMakanan, qtyMakanan, totalHarga);
        System.out.println( Konstan.NEW_LINE +"1. Konfirmasi dan Bayar");
        System.out.println("2. Kembali ke menu utama");
        System.out.println("0. Keluar aplikasi");
        System.out.print("=> ");
        return input.nextInt();
    }

    public void cetakNota(ArrayList<String> pesanan, ArrayList<Integer> hargaMakanan, ArrayList<Integer> qtyMakanan, int totalHarga) {
        try {
            BufferedWriter nota = new BufferedWriter(new FileWriter("Nota.txt"));

            nota.write(Konstan.LINE_SEPARATOR + Konstan.NEW_LINE);
            nota.write("BinarFud" + Konstan.NEW_LINE);
            nota.write(Konstan.LINE_SEPARATOR + Konstan.NEW_LINE);

            nota.write(Konstan.NEW_LINE + "Terima kasih sudah memesan di BinarFud" + Konstan.NEW_LINE + Konstan.NEW_LINE);
            nota.write("Dibawah ini adalah pesanan anda:" + Konstan.NEW_LINE + Konstan.NEW_LINE);

            nota.write("Nama Menu" + Konstan.TAB + "Qty" + Konstan.TAB + "Harga" + Konstan.NEW_LINE);
            nota.write(Konstan.PLUS_LINE + Konstan.NEW_LINE);

            int totalQty = 0;
            for (int i = 0; i < pesanan.size(); i++) {
                String namaMakanan = pesanan.get(i);
                int hargaMakananItem = hargaMakanan.get(i);
                int qty = qtyMakanan.get(i);
                int totalHargaItem = hargaMakananItem * qty;
                nota.write(namaMakanan + Konstan.TAB + qty + Konstan.TAB + formatHarga(totalHargaItem) + Konstan.NEW_LINE);
                totalQty += qty;

                String catatan = pesanan.get(i);
                if (!catatan.isEmpty()) {
                    nota.write("Catatan: " + catatan + Konstan.NEW_LINE);
                }
            }

            nota.write(Konstan.PLUS_LINE + Konstan.NEW_LINE);
            nota.write("Total" + Konstan.TAB + totalQty + Konstan.TAB + formatHarga(totalHarga) + Konstan.NEW_LINE + Konstan.NEW_LINE);
            nota.write("Pembayaran : BinarCash" + Konstan.NEW_LINE + Konstan.NEW_LINE);
            nota.write(Konstan.LINE_SEPARATOR + Konstan.NEW_LINE);
            nota.write("Simpan struk ini sebagai bukti pembayaran" + Konstan.NEW_LINE);
            nota.write(Konstan.LINE_SEPARATOR + Konstan.NEW_LINE);

            nota.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

