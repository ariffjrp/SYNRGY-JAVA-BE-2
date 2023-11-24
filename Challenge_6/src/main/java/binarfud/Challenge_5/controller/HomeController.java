package binarfud.Challenge_5.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HomeController {

    @Autowired
    MerchantController merchantController;
    public void home(){
        System.out.println("Menggunakan endpoint uji dengan postman");
    }
}
