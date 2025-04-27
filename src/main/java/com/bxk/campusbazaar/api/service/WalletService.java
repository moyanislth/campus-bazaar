package com.bxk.campusbazaar.api.service;


import com.bxk.campusbazaar.pojo.Wallet;

import java.math.BigDecimal;

public interface WalletService {
    Wallet getWalletInfo(int userId);

    void newWallet(int userId);
    void deduct(int id, BigDecimal amount);
}
