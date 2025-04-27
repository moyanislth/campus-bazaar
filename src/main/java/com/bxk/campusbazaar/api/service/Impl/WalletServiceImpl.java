package com.bxk.campusbazaar.api.service.Impl;

import com.bxk.campusbazaar.api.mapper.WalletMapper;
import com.bxk.campusbazaar.api.service.WalletService;
import com.bxk.campusbazaar.pojo.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class WalletServiceImpl implements WalletService {
    private final WalletMapper walletMapper;

    @Autowired
    WalletServiceImpl(WalletMapper walletMapper){
        this.walletMapper = walletMapper;
    }

    @Override
    public Wallet getWalletInfo(int userId) {
        return walletMapper.selectByPrimaryKey((long) userId);
    }

    @Override
    public void newWallet(int userId) {
        Wallet wallet = new Wallet();
        wallet.setUserId((long) userId);
        wallet.setBalance(BigDecimal.valueOf(0));
        wallet.setIntegral(0);

        int result = walletMapper.insert(wallet);

        if (result != 1){
            throw new RuntimeException("创建钱包失败");
        }
    }

    @Override
    public void deduct(int id, BigDecimal amount) {
        Wallet wallet = walletMapper.selectByPrimaryKey((long) id);
        BigDecimal balance = wallet.getBalance();
        if (balance.compareTo(amount) < 0) { //余额不足
            throw new RuntimeException("余额不足");
        }
        balance = balance.subtract(amount);
        wallet.setBalance(balance);

        walletMapper.updateByPrimaryKey(wallet);
    }
}
