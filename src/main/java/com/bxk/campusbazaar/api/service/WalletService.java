package com.bxk.campusbazaar.api.service;


import com.bxk.campusbazaar.pojo.Wallet;

public interface WalletService {
    Wallet getWalletInfo(int userId);

    void newWallet(int userId);
}
