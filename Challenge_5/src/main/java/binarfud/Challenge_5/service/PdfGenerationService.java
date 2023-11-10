package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.Order;
import binarfud.Challenge_5.model.Users;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PdfGenerationService {

    @Autowired
    private OrderService orderService;

    public Resource generatePdf(UUID orderId) throws JRException, IOException {
        Order order = orderService.getOrderById(orderId);

        // Load JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport("classpath:jasper/template.jrxml");

        // Set data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(order));

        // Set parameters (jika ada)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Order", order);

        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to PDF
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        // Convert to Spring Resource
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        return resource;
    }

    public Resource generateInvoicePdf(Order order, Users user) throws JRException, IOException {
        // Load JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport("classpath:jasper/invoice_template.jrxml");

        // Set data source
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(order));

        // Set parameters
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("Order", order);
        parameters.put("User", user);

        // Fill the report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Export to PDF
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        // Convert to Spring Resource
        ByteArrayResource resource = new ByteArrayResource(pdfBytes);

        return resource;
    }
}
