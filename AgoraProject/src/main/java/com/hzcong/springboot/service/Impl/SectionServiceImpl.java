package com.hzcong.springboot.service.Impl;


import com.hzcong.data.entities.SectionEntity;
import com.hzcong.springboot.repository.SectionRepository;
import com.hzcong.springboot.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository sectionRepository;

//    @Transactional
    @Cacheable(value = "section",key = "#id", unless="#result == null")
    public SectionEntity getSectionById(String id){
        System.out.println("进入getSectionById");
        System.out.println(id);
        return  sectionRepository.findOne(id);
    }

    @Transactional
    @CachePut(value = "section",key = "#section.secId", unless="#result == null")
    public boolean addSection(SectionEntity section){
        return sectionRepository.save(section)!=null;
    }

    @Transactional
    @CacheEvict(value = "section",key = "#secId", beforeInvocation = true)
    public boolean deleteSectionById(String secId){
        return sectionRepository.deleteSectionEntityBySecId(secId)!=0;
    }


    //    @Transactional
    public Iterable<SectionEntity> getTeacherSectionsByTeaId(String teaId){
        return sectionRepository.getSectionEntitiesByTeaId(teaId);
    }


//    @Transactional
    public Iterable<SectionEntity> getAllSections(){
        return sectionRepository.findAll();
    }



    @Transactional
    public boolean authorizeSection(String secId){
        return sectionRepository.updateSectionEntityAuthorizedBySecId(secId, true)!=0;
    }

    @Transactional
    public boolean updatePurchased(String secId, boolean purchased) {
        return sectionRepository.updatePurchasedBySecId(secId, purchased)!=0;
    }


    @Transactional
    public boolean updatePaidBySecId(String secId, boolean paid) {
        return sectionRepository.updatePaidBySecId(secId, paid)!=0;
    }

    @Override
    public Iterable<SectionEntity> getSectionEntitiesByCourId(String courseId) {
        return sectionRepository.getSectionEntitiesByCourId(courseId);
    }


    @Override
    public boolean existsSectionsByTeaIdAndAuthorized(String teaId, boolean authorized) {
        return sectionRepository.existsSectionEntitiesByTeaIdAndAuthorized(teaId,authorized);
    }


    @Override
    public Page<SectionEntity> getAllAuthorizedSectionOfPage(int pageNum, int pageSize) {
        System.out.println("进入获取列表的地方");
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.getAllAuthorizedSctionOfPage(pageable);
    }

    @Override
    public Page<SectionEntity> getAllUnAuthorizedSctionOfPage(int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.getAllUnAuthorizedSctionOfPage(pageable);
    }

    @Override
    public Page<SectionEntity> getTeacherAuthorizedSectionOfPage(String teaId, int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.getTeacherAuthorizedSctionOfPage(teaId,pageable);
    }

    @Transactional
    public boolean updateProportionBySecId(String secId, double proportion) {
        return sectionRepository.updateProportionBySecId(secId, proportion)!=0;
    }

    @Transactional
    public boolean updateActualAmountBySecId(String secId, double actualAmount) {
        return sectionRepository.updateActualAmountBySecId(secId, actualAmount)!=0;
    }

    @Override
    public SectionEntity getAppliedSectionByTeaId(String teaId) {
        return sectionRepository.getSectionEntityByTeaIdAndAuthorized(teaId,false);
    }

    @Override
    public boolean existsSectionBySecIdAndTeaName(String secId, String teaName) {
        return sectionRepository.existsSectionEntityBySecIdAndTeaName(secId,teaName);
    }

    @Transactional
    public Page<SectionEntity> getUnfinishedSectionOfPage(int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.findAllUnfinsihedSctionOfPage(pageable);
    }

    @Transactional
    public Page<SectionEntity> getFinishedSectionOfPage(int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.findAllFinsihedSctionOfPage(pageable);
    }

    @Transactional
    public Page<SectionEntity> getUnAuthorSectionOfPage(int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.findAllUnAuthorSctionOfPage(pageable);
    }

    @Transactional
    public boolean updateProhibitedBySecId(String secId, boolean prohibited) {
        return sectionRepository.updatePohibitedBySecId(secId, prohibited)!=0;
    }

    @Transactional
    public boolean updateInClassBySecId(String secId, boolean inClass) {
        return sectionRepository.updateInClassBySecId(secId, inClass)!=0;
    }

    @Transactional
    public Page<SectionEntity> getProhibitSctionOfPage(int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.findAllProhibitSctionOfPage(pageable);
    }

    @Transactional
    public Page<SectionEntity> getInClassSctionOfPage(int pageNum, int pageSize) {
        Pageable pageable = new PageRequest(pageNum,pageSize);
        return sectionRepository.findAllInClassSctionOfPage(pageable);
    }

    @Transactional
    public Iterable<SectionEntity> getAllFinsihedSctions() {
        return sectionRepository.findAllFinsihedSctions();
    }

    @Transactional
    public Iterable<SectionEntity> getAllUnfinsihedSctions() {
        return sectionRepository.findAllUnfinsihedSctions();
    }

    @Transactional
    public Iterable<SectionEntity> getAllUnAuthorSctions() {
        return sectionRepository.findAllUnAuthorSctions();
    }

    @Transactional
    public Iterable<SectionEntity> getAllInClassSctions() {
        return sectionRepository.findAllInClassSctions();
    }

    @Transactional
    public Iterable<SectionEntity> getAllProhibitSctions() {
        return sectionRepository.findAllProhibitSctions();
    }

    @Override
    @Transactional
    public boolean updateCurAmount(String secId) {
        return sectionRepository.updateCurAmountABySecId(secId)!=0;
    }

    @Override
    @Transactional
    public boolean deleteSection(String secId) {
        return sectionRepository.deleteSectionEntityBySecId(secId)!=0;
    }



}

