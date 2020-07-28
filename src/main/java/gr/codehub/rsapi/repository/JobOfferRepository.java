package gr.codehub.rsapi.repository;


import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {

    /* ftiaxsame mia custom methodo(query) to opoio mas fernei ta job offers me vasi kapoia kritiria */
    /* to :region p.x einai to region tis param giati thelo na peraso mesa apo ti java sto query tis sql
    metvlites  */

    @Query(value = "SELECT c FROM " +
            "JobOffer c WHERE" +
            " (:positionTitle is null or c.positionTitle = :positionTitle) " +
            "and (:region is null or c.region = :region)" +
            " and (:date is null or c.date =:date) and (:skill is null or c.skill =:skill)", nativeQuery = true)
    List<JobOffer> findJobOffersByCriteria
            (@Param("positionTitle") String positionTitle, @Param("region") Region
                    region, @Param("date") Date date, @Param("skill") Skill skill);
}
