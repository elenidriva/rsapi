package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    @Query(value = "SELECT xd.jobOffer_id," +
            "  dx.applicant_id" +
            "  FROM [RSAPI].[dbo].[JobOfferSkill] xd" +
            "  INNER JOIN  [RSAPI].[dbo].[ApplicantSkill] dx ON xd.skill_id = dx.skill_id " +
            "  ORDER BY " +
            "  xd.id ASC," +
            "  dx.id DESC", nativeQuery = true)
    List<JobOffersApplicantsDto> findSkillMatches();

    @Query(value = " SELECT * from Match WHERE matchStatus=0 ORDER BY matchDate DESC", nativeQuery = true)
    List<Match> getFinalisedfMatchesOrderedByDate();

}