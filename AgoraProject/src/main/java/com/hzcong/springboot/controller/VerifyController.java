package com.hzcong.springboot.controller;

import com.hzcong.springboot.service.VerifyService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class VerifyController {

    @Autowired
    private VerifyService verifyService;

//    @ResponseBody
//    @RequestMapping(value = "getImage",method = RequestMethod.GET)
//    public BufferedImage getImage(){
//        return verifyService.getImage();
//    }

    @RequestMapping(value = "/changeImage",method = RequestMethod.GET)
    public void changeImage(@RequestParam("test")String test, HttpServletResponse response){
        try {
            ImageIO.write(verifyService.changeVerifyMsg(), "jpg", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @ApiOperation(value="拿到当前验证码图片",notes="验证码类型为image")
    @RequestMapping(value="/getVerifyImage",method=RequestMethod.GET)
    public void getVerifyImage(HttpServletRequest request, HttpServletResponse response) {
//		request.getSession().setAttribute("image", verifyCodeService.getImage());
        try {
            ImageIO.write(verifyService.getImage(), "jpg", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
