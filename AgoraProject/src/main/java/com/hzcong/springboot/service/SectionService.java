package com.hzcong.springboot.service;

import com.hzcong.data.entities.SectionEntity;
import org.springframework.data.domain.Page;

public interface SectionService {

    SectionEntity getSectionById(String id);

    boolean addSection(SectionEntity section);

    boolean authorizeSection(String secId);

    boolean updatePurchased(String secId,boolean purchased);

    boolean deleteSectionById(String secId);

    boolean updatePaidBySecId(String secId,boolean paid);

    Iterable<SectionEntity> getSectionEntitiesByCourId(String courseId);


    boolean existsSectionsByTeaIdAndAuthorized(String teaId, boolean authorized);

    Iterable<SectionEntity> getAllSections();

    Iterable<SectionEntity> getTeacherSectionsByTeaId(String teaId);

    Page<SectionEntity> getAllUnAuthorizedSctionOfPage(int pageNum, int pageSize);

    Page<SectionEntity> getAllAuthorizedSectionOfPage(int pageNum, int pageSize);

    Page<SectionEntity> getTeacherAuthorizedSectionOfPage(String teaId,int pageNum,int pageSize);

    boolean updateProportionBySecId(String secId,double proportion);

    boolean updateActualAmountBySecId(String secId,double actualAmount);

    SectionEntity getAppliedSectionByTeaId(String teaId);


    boolean existsSectionBySecIdAndTeaName(String secId, String teaName);

    Page<SectionEntity> getUnfinishedSectionOfPage(int pageNum,int pageSize);

    Page<SectionEntity> getFinishedSectionOfPage(int pageNum,int pageSize);

    Page<SectionEntity> getUnAuthorSectionOfPage(int pageNum,int pageSize);

    boolean updateProhibitedBySecId(String secId,boolean prohibited);

    boolean updateInClassBySecId(String secId,boolean inClass);

    Page<SectionEntity> getProhibitSctionOfPage(int pageNum,int pageSize);

    Page<SectionEntity> getInClassSctionOfPage(int pageNum,int pageSize);

    Iterable<SectionEntity> getAllFinsihedSctions();

    Iterable<SectionEntity> getAllUnfinsihedSctions();

    Iterable<SectionEntity> getAllUnAuthorSctions();

    Iterable<SectionEntity> getAllInClassSctions();

    Iterable<SectionEntity> getAllProhibitSctions();

    boolean updateCurAmount(String secId);

    boolean deleteSection(String secId);


}
