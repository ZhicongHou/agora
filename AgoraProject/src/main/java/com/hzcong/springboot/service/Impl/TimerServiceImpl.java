package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.SectionEntity;
import com.hzcong.springboot.repository.SectionRepository;
import com.hzcong.springboot.service.TimerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

@Service
public class TimerServiceImpl implements TimerService {

    @Autowired
    private SectionRepository sectionRepository;

    /**
     * 当天结束之后，更新上课的进度
     */
    @Transactional
    public boolean updateAttended() {
        /**
         * 获取当前是每周的星期几
         * java的calendar.get(Calendar.DAY_OF_WEEK)返回对应的数字为 1=星期日，2=星期一，3=星期二 ……
         * 做减一处理之后，若为0则是星期天
         */
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dow = calendar.get(Calendar.DAY_OF_WEEK)-1;
        String dayOfWeek = "";
        if(dow <= 0 ) dayOfWeek = "7";
        else dayOfWeek = String.valueOf(dow);

        ArrayList<SectionEntity> sections = (ArrayList<SectionEntity>) sectionRepository.findAll();
        for (SectionEntity section: sections) {
            String judgement = section.getJudgement();
            String[] temps = judgement.split(",");
            for(int i=0;i<temps.length;i++){
                /**
                 * 课程开始的时间和当前时间的判断
                 * 已上课程和课时的判断
                 */
                if(date.compareTo(section.getStartDate())>=0 && section.getAttended()<section.getFrequency()&& temps[i].equals(dayOfWeek)){
                    sectionRepository.updateAttendedBySecId(section.getSecId());
                }
            }
        }
        return true;
    }

    @Transactional
    public boolean updatePurchased() {
        Date now = new Date();
        ArrayList<SectionEntity> sections = (ArrayList<SectionEntity>) sectionRepository.findAll();
        for (SectionEntity section: sections) {
            Date startDate = section.getStartDate();
            if(now.compareTo(startDate)>=0 || section.getCurAmount()>=section.getUpperLimit()){
                sectionRepository.updatePurchasedBySecId(section.getSecId(), false);
            }
        }
        return true;
    }


}
