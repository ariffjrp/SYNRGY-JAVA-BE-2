package binarfud.Challenge_5.controller;

import binarfud.Challenge_5.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.UUID;

@RestController
@Component
@RequestMapping("/api/invoices")
public class InvoiceController {

    @Autowired
    InvoiceService invoiceService;

    @PostMapping("/generate")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Resource> generateInvoice(@RequestParam UUID orderId, @RequestParam UUID userId,
                                                    Authentication authentication, Principal principal) {
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
