package com.bxk.campusbazaar.api.service;

import com.bxk.campusbazaar.pojo.Merchant;
import com.bxk.campusbazaar.pojo.DTO.MerchantCredentialsDTO;

public interface MerchantService {
    Merchant getMerchant(int id) ;

    MerchantCredentialsDTO getMerchantCredentials(int id);

    String getMerchantName(int id);
}
