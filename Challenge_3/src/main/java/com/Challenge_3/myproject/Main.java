package com.Challenge_3.myproject;

import com.Challenge_3.myproject.Controller.OrderController;
import com.Challenge_3.myproject.Model.Data;
import com.Challenge_3.myproject.Model.UserModel;
import com.Challenge_3.myproject.View.Tampilan;

public class Main {
    public static void main(String[] args) {
        try {
            Data data = new Data();
            Tampilan tampilan = new Tampilan();
            OrderController orderController = new OrderController(data, tampilan);

            UserModel userModel = new UserModel();
            userModel.createTables();

            orderController.jalankanAplikasi();
        } catch (Exception e) {
            System.err.println("Terjadi kesalahan: " + e.getMessage());
        }
    }
}
