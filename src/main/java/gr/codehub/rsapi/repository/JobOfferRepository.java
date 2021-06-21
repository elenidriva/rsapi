package gr.codehub.rsapi.repository;


import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.JobOffer;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@EntityScan(basePackages = {"JobOffer"})
public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {

    @Query(value = "SELECT c FROM " +
            "JobOffer c WHERE" +
            " (:positionTitle is null or c.positionTitle = :positionTitle) " +
            " and (:region is null or c.region = :region)" +
            " and (:jobOfferDate is null or c.jobOfferDate =:jobOfferDate)"
    )
    List<JobOffer> findJobOffersByCriteria
            (@Param("positionTitle") String positionTitle, @Param("region") Region
                    region, @Param("jobOfferDate") LocalDate jobOfferDate);

}
