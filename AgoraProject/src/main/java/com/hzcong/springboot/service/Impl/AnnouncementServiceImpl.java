package com.hzcong.springboot.service.Impl;

import com.hzcong.data.entities.AnnouncementEntity;
import com.hzcong.springboot.repository.AnnouncementRepository;
import com.hzcong.springboot.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Override
    public AnnouncementEntity getAnnouncementBySecId(String secId) {
        return announcementRepository.getAnnouncementEntityBySecId(secId);
    }

    @Override
    public boolean writeAnnouncementBySecId(AnnouncementEntity announcement) {
        return announcementRepository.save(announcement)!=null;
    }

    @Override
    @Transactional
    public boolean deleteAnnouncementBySecId(String secId) {
        return announcementRepository.deleteAnnouncementEntityBySecId(secId)!=0;
    }
}
