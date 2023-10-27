package binarfud.Challenge_4.service;

import binarfud.Challenge_4.model.Merchant;
import binarfud.Challenge_4.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    private final static Logger logger = LoggerFactory.getLogger(MerchantService.class);

    public List<Merchant> getOpenMerchants() {
        return merchantRepository.findByOpenIsTrue();
    }



    public Merchant create(Merchant merchant){
        merchant = merchantRepository.save(merchant);
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
}
