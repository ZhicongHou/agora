package com.hzcong.springboot.controller;

import com.hzcong.springboot.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configuration
@EnableScheduling
public class TimerController {

    @Autowired
    private TimerService timerService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateSectionFrequency(){
        timerService.updateAttended();
        timerService.updatePurchased();
    }

}
