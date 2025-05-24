package com.bxk.campusbazaar.api.controller.common;


import com.bxk.campusbazaar.api.service.WalletService;
import com.bxk.campusbazaar.tools.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
/**
 * 钱包业务
 */
@RestController("/api/wallet")
public class WalletController {
    private final WalletService walletService;

    @Autowired
    WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/newWallet")
    public Response<Object> newWallet(@RequestParam int userId) {
        walletService.newWallet(userId);
        return Response.success();
    }

    @GetMapping("/getWalletInfo")
    public Response<Object> getWalletInfo(@RequestParam int userId) {
        return Response.success(walletService.getWalletInfo(userId));
    }
}
