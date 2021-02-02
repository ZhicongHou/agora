package com.hzcong.springboot.repository;


import com.hzcong.data.entities.SectionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<SectionEntity, String> {


    int deleteSectionEntityBySecId(String secID);

    boolean existsSectionEntityBySecIdAndTeaName(String secId, String teaName);


    boolean existsSectionEntitiesByTeaIdAndAuthorized(String teaId, boolean authorized);

    @Modifying
    @Query(value = "update section set authorized = :authorized where sec_id = :secId",nativeQuery = true)
    int updateSectionEntityAuthorizedBySecId(@Param("secId")String secId, @Param("authorized") boolean authorized);

    @Modifying
    @Query(value = "update section set attended = attended+1 where sec_id = :secId",nativeQuery = true)
    int updateAttendedBySecId(@Param("secId") String secId);

    @Modifying
    @Query(value = "update section set purchased = :purchased where sec_id = :secId",nativeQuery = true)
    int updatePurchasedBySecId(@Param("secId") String secId,@Param("purchased")boolean purchased);


    @Modifying
    @Query(value = "update section set paid = :paid where sec_id = :secId",nativeQuery = true)
    int updatePaidBySecId(@Param("secId") String secId,@Param("paid")boolean paid);

    SectionEntity getSectionEntityByTeaIdAndAuthorized(String teaId, boolean authorized);

    Iterable<SectionEntity> getSectionEntitiesByCourId(String courseId);

    Iterable<SectionEntity> getSectionEntitiesByTeaId(String teaId);

    @Query(value = "SELECT * FROM section where authorized=true /*?#{#pageable}*/"
            ,countQuery="select count(*) from section"
            ,nativeQuery = true)
    Page<SectionEntity> getAllAuthorizedSctionOfPage(Pageable page);


    @Query(value = "SELECT * FROM section where authorized=false /*?#{#pageable}*/"
            ,countQuery="select count(*) from section"
            ,nativeQuery = true)
    Page<SectionEntity> getAllUnAuthorizedSctionOfPage(Pageable page);

    @Query(value = "SELECT * FROM section where authorized=true and tea_id = ?1/*?#{#pageable}*/"
            ,countQuery="select count(*) from section where tea_id = ?1"
            ,nativeQuery = true)
    Page<SectionEntity> getTeacherAuthorizedSctionOfPage(String teaId, Pageable page);


    @Modifying
    @Query(value = "update section set proportion = :proportion where sec_id = :secId",nativeQuery = true)
    int updateProportionBySecId(@Param("secId")String secId,@Param("proportion")double proportion);

    @Modifying
    @Query(value = "update section set actual_amount = :actualAmount where sec_id = :secId",nativeQuery = true)
    int updateActualAmountBySecId(@Param("secId")String secId,@Param("actualAmount")double actualAmount);


    @Query(value = "SELECT * FROM section WHERE attended/frequency = 1 AND authorized=1 /*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM section WHERE attended/frequency = 1 AND authorized=1 "
            ,nativeQuery = true)
    Page<SectionEntity> findAllFinsihedSctionOfPage(Pageable page);

    @Query(value = "SELECT * FROM section WHERE attended/frequency = 1 AND authorized=1"
            ,countQuery="SELECT count(*) FROM section WHERE attended/frequency = 1 AND authorized=1 "
            ,nativeQuery = true)
    Iterable<SectionEntity> findAllFinsihedSctions();

    @Query(value = "SELECT * FROM section WHERE attended/frequency != 1  AND authorized=1 /*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM section WHERE attended/frequency != 1 AND authorized=1 "
            ,nativeQuery = true)
    Page<SectionEntity> findAllUnfinsihedSctionOfPage(Pageable page);

    @Query(value = "SELECT * FROM section WHERE attended/frequency != 1  AND authorized=1 "
            ,countQuery="SELECT count(*) FROM section WHERE attended/frequency != 1 AND authorized=1 "
            ,nativeQuery = true)
    Iterable<SectionEntity> findAllUnfinsihedSctions();

    @Query(value = "SELECT * FROM section WHERE authorized=0 /*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM section WHERE authorized=0 "
            ,nativeQuery = true)
    Page<SectionEntity> findAllUnAuthorSctionOfPage(Pageable page);

    @Query(value = "SELECT * FROM section WHERE authorized=0 "
            ,countQuery="SELECT count(*) FROM section WHERE authorized=0 "
            ,nativeQuery = true)
    Iterable<SectionEntity> findAllUnAuthorSctions();

    @Query(value = "SELECT * FROM section WHERE in_class=1 AND prohibited=0/*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM section WHERE in_class=1 AND prohibited=0 "
            ,nativeQuery = true)
    Page<SectionEntity> findAllInClassSctionOfPage(Pageable page);

    @Query(value = "SELECT * FROM section WHERE in_class=1 AND prohibited=0"
            ,countQuery="SELECT count(*) FROM section WHERE in_class=1 AND prohibited=0 "
            ,nativeQuery = true)
    Iterable<SectionEntity> findAllInClassSctions();

    @Query(value = "SELECT * FROM section WHERE prohibited=1  /*?#{#pageable}*/"
            ,countQuery="SELECT count(*) FROM section WHERE prohibited=1 "
            ,nativeQuery = true)
    Page<SectionEntity> findAllProhibitSctionOfPage(Pageable page);

    @Query(value = "SELECT * FROM section WHERE prohibited=1"
            ,countQuery="SELECT count(*) FROM section WHERE prohibited=1 "
            ,nativeQuery = true)
    Iterable<SectionEntity> findAllProhibitSctions();

    @Modifying
    @Query(value = "update section set prohibited = :prohibited where sec_id = :secId",nativeQuery = true)
    int updatePohibitedBySecId(@Param("secId")String secId,@Param("prohibited")boolean prohibited);

    @Modifying
    @Query(value = "update section set in_class = :inClass where sec_id = :secId",nativeQuery = true)
    int updateInClassBySecId(@Param("secId")String secId,@Param("inClass")boolean inClass);


    @Modifying
    @Query(value = "update section set cur_amount = cur_amount+1 where sec_id = :secId",nativeQuery = true)
    int updateCurAmountABySecId(@Param("secId") String secId);


}