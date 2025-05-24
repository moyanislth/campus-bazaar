package com.bxk.campusbazaar.api.controller.common;


import com.bxk.campusbazaar.api.service.UserProfileService;
import com.bxk.campusbazaar.tools.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Log4j2
@Component
@RestController
@RequestMapping("/api/user_profile")
@CrossOrigin
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    UserProfileController(UserProfileService userProfileService){
        this.userProfileService = userProfileService;
    }

    @GetMapping("/getProfile")
    public Response<Object> getProfile(@RequestParam int id){
        return Response.success(userProfileService.getProfile(id));
    }

    @PatchMapping("/updateProfile")
    public Response<Object> updateProfile(@RequestParam String avatar,
                                          @RequestParam String bio,
                                         @RequestParam String wechat,
                                         @RequestParam String deliveryAddress){

        try {
            userProfileService.updateProfile(avatar, bio, wechat, deliveryAddress);
        }catch (Exception e){
            return Response.fail("出错了");
        }

        return Response.success();
    }
}
