package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, Integer> {


    @Query(value = "SELECT c FROM Applicant c WHERE (:lastName is null or c.lastName = :lastName) and (:region is null or c.region = :region) and (:date is null or c.date =:date) and (:skill is null or c.skill =:skill)", nativeQuery = true)
    List<Applicant> findApplicantByCriteria(@Param("lastName") String lastName, @Param("region") Region region, @Param("date") Date date, @Param("skill") Skill skill);


}
