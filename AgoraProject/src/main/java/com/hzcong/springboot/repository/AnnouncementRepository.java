package com.hzcong.springboot.repository;

import com.hzcong.data.entities.AnnouncementEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends CrudRepository<AnnouncementEntity,String > {

    AnnouncementEntity getAnnouncementEntityBySecId(String secId);

    int deleteAnnouncementEntityBySecId(String secId);

}

