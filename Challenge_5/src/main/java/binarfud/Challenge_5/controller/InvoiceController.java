package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/generate")
    public ResponseEntity<Resource> generateInvoice(@RequestParam UUID orderId, @RequestParam UUID userId) {
        try {
            Resource invoicePdf = invoiceService.generateInvoice(orderId, userId);

            // Set response headers
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(invoicePdf);
        } catch (Exception e) {
            // Handle exceptions appropriately
            return ResponseEntity.status(500).body(null);
        }
    }
}
