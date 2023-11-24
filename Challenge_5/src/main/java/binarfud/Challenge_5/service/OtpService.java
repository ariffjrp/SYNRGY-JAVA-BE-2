package binarfud.Challenge_5.service;

import binarfud.Challenge_5.model.dto.EmailDTO;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

    private final static Logger logger = LoggerFactory.getLogger(OtpService.class);
    private OtpGenerator otpGenerator;
    private EmailService emailService;
    private UserService userService;

    private LoadingCache<String, Integer> otpCache;

    public OtpService(OtpGenerator otpGenerator, EmailService emailService, UserService userService) {
        this.otpGenerator = otpGenerator;
        this.emailService = emailService;
        this.userService = userService;

        // Inisialisasi cache dengan waktu kadaluarsa 5 menit (misalnya)
        this.otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build(new CacheLoader<String, Integer>() {
                    @Override
                    public Integer load(String s) throws Exception {
                        return 0;
                    }
                });
    }

    public Boolean generateOtp(String key) {
        // generate otp
        Integer otpValue = otpGenerator.generateOTP(key);
        if (otpValue == -1) {
            logger.error("OTP generator is not working...");
            return false;
        }

        logger.info("Generated OTP: {}", otpValue);

        // Simpan OTP ke cache
        otpCache.put(key, otpValue);

        // fetch user e-mail from database
        String userEmail = userService.findEmailByUsername(key);
        List<String> recipients = new ArrayList<>();
        recipients.add(userEmail);

        // generate emailDTO object
        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setSubject("Spring Boot OTP Password.");
        emailDTO.setContent("OTP Password: " + otpValue);
        emailDTO.setRecipients(recipients);

        // send generated e-mail
        return emailService.sendSimpleMessage(emailDTO);
    }

    public Boolean verifyOtp(String key, Integer userEnteredOtp) {
        Integer savedOtp = otpCache.getIfPresent(key);

        if (savedOtp != null && savedOtp.equals(userEnteredOtp)) {
            // Verifikasi berhasil, hapus OTP dari cache setelah diverifikasi
            otpCache.invalidate(key);
            return true;
        } else {
            // Verifikasi gagal
            return false;
        }
    }
}
