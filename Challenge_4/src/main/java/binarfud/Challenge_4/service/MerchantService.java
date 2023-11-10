package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Merchant;
import binarfud.Challenge_4.model.Order;
import binarfud.Challenge_4.repository.MerchantRepository;
import binarfud.Challenge_4.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    @Autowired
    OrderRepository orderRepository;

    private final static Logger logger = LoggerFactory.getLogger(MerchantService.class);

    public List<Merchant> getOpenMerchants() {
        List<Merchant> optionalMerchant = merchantRepository.findByOpenIsTrue();
        if (optionalMerchant.isEmpty()){
            return null;
        }else {
            return merchantRepository.findByOpenIsTrue();
        }
    }

    public Merchant getMerchantById(UUID merchantId) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(merchantId);
        if (optionalMerchant.isPresent()) {
            return optionalMerchant.get();
        } else {
            return null;
        }
    }


    public Merchant create(Merchant merchant){
        merchant.setLocation(merchant.getLocation());
        merchant.setName(merchant.getName());
        merchant.setIncome(0);
        merchant.setOpen(false);
        merchantRepository.save(merchant);
        logger.info(String.valueOf(merchant));
        return merchant;
    }

    public Merchant update(UUID id, boolean open) {
        Optional<Merchant> optionalMerchant = merchantRepository.findById(id);
        if (optionalMerchant.isPresent()) {
            Merchant merchantToUpdate = optionalMerchant.get();
            merchantToUpdate.setOpen(open);
            logger.info(String.valueOf(merchantToUpdate));
            return merchantRepository.save(merchantToUpdate);
        } else {
            logger.error(null);
            return null;
        }
    }

    public void deleteMerchant(UUID merchantId) {
        merchantRepository.deleteById(merchantId);
    }


    public Double calculateMerchantProfit(UUID merchantId, Date startDate, Date endDate) {
        return orderRepository.calculateMerchantProfit(merchantId, startDate, endDate);
    }

    public List<Order> getOrdersByMerchantAndDateRange(UUID merchantId, Date startDate, Date endDate) {
        return orderRepository.findByMerchantIdAndCreatedDateBetween(merchantId, startDate, endDate);
    }
}
