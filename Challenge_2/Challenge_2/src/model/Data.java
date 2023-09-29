package model;

import java.util.ArrayList;
import java.util.List;

public class Data {
    private List<Pesanan> pesananList;

    public Data() {
        pesananList = new ArrayList<>();
    }

    public void tambahPesanan(Pesanan pesanan) {
        pesananList.add(pesanan);
    }

    public List<Pesanan> getPesananList() {
        return pesananList;
    }
}
