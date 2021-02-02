package com.hzcong.springboot.service;

import java.awt.image.BufferedImage;

public interface VerifyService {

     BufferedImage changeVerifyMsg();

     String getVerifyCode();

     BufferedImage getImage();

     boolean checkVerifyCode(String code);
}
