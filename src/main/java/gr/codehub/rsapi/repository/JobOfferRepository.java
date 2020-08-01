package gr.codehub.rsapi.repository;


import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {

    @Query(value = "SELECT c FROM " +
            "JobOffer c WHERE" +
            " (:positionTitle is null or c.positionTitle = :positionTitle) " +
            " and (:region is null or c.region = :region)" +
            " and (:jobOfferDate is null or c.jobOfferDate =:jobOfferDate)" +
            " and (:skill is null or c.skill =:skill)", nativeQuery = true)
    List<JobOffer> findJobOffersByCriteria
            (@Param("positionTitle") String positionTitle, @Param("region") Region
                    region, @Param("jobOfferDate") LocalDate jobOfferDate, @Param("skill") Skill skill);


    @Query(value = "SELECT c FROM Applicant c" +
            " WHERE(:firstName is null or c.firstName = :firstName)" +
            " and (:lastName is null or c.lastName = :lastName)" +
            " and (:region is null or c.region = :region)" +
            " and (:applicationDate is null or c.applicationDate =:applicationDate)" +
            " and (:skill is null or c.skill =:skill)", nativeQuery = true)
    List<Applicant> findApplicantByCriteria(@Param("firstName") String firstName, @Param("lastName") String lastName, @Param("region") Region region, @Param("applicationDate") LocalDate applicationDate, @Param("skill") Skill skill);


}
