package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.Order;
import binarfud.Challenge_5.model.Users;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
public class InvoiceService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private PdfGenerationService pdfGenerationService;

    public Resource generateInvoice(UUID orderId, UUID userId) throws JRException, IOException {
        Order order = orderService.getOrderById(orderId);
        Users user = userService.getUserById(userId);

        if (order != null && user != null) {
            // Jika order dan user ditemukan, lanjutkan dengan pembuatan invoice PDF
            return pdfGenerationService.generateInvoicePdf(order, user);
        } else {
            // Jika order atau user tidak ditemukan, kembalikan null atau resource kosong
            return null;
        }
    }
}
