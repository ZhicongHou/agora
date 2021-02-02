package com.hzcong.springboot.controller;

import com.hzcong.springboot.service.Impl.VerifyServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/index")
public class IndexController {

    @Autowired
    public VerifyServiceImpl verifyCodeService;

    @ApiOperation(value="拿到当前图片的验证码内容",notes="验证码类型为string")
    @RequestMapping(value = "/generateVerifyCode", method = RequestMethod.GET)
    public void generateVerifyCode(HttpServletRequest request) {
        request.getSession().setAttribute("verifyCode",verifyCodeService.getVerifyCode());
    }

    @ApiOperation(value="拿到当前验证码图片",notes="验证码类型为image")
    @RequestMapping(value="/getVerifyImage",method=RequestMethod.GET)
    public void getVerifyImage(HttpServletRequest request, HttpServletResponse response) {
//		request.getSession().setAttribute("image", verifyCodeService.getImage());
        try {
            ImageIO.write(verifyCodeService.getImage(), "jpg", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
