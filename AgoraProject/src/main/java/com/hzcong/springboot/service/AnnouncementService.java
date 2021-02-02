package com.hzcong.springboot.service;

import com.hzcong.data.entities.AnnouncementEntity;

public interface AnnouncementService {

    AnnouncementEntity getAnnouncementBySecId(String secId);

    boolean writeAnnouncementBySecId(AnnouncementEntity announcement);

    boolean deleteAnnouncementBySecId(String secId);

}
