package model;

public class Pesanan {
    private String nama;
    private int harga;
    private int jumlah;
    private String catatan;

    public Pesanan(String nama, int harga, int jumlah, String catatan){
        this.nama = nama;
        this.harga = harga;
        this.jumlah = jumlah;
        this.catatan = catatan;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    @Override
    public String toString() {
        return "Nama: " + nama + "\nHarga: " + harga + "\nJumlah: " + jumlah + "\nCatatan: " + catatan;
    }
}
