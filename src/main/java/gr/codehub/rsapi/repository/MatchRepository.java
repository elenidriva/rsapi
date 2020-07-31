package gr.codehub.rsapi.repository;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {


    @Query(value = "  SELECT ap.id applicant," +
            "  jos.jobOffer_id joboffer" +
            "  FROM [RSAPI].[dbo].Applicant ap" +
            "  INNER JOIN [RSAPI].[dbo].[ApplicantSkill] aps " +
            "  ON ap.id = aps.applicant_id" +
            "  INNER JOIN [RSAPI].[dbo].[JobOfferSkill] jos " +
            "  ON jos.skill_id = aps.skill_id " +
            "  WHERE ap.status = 0" +
            "  ORDER BY " +
            "  ap.id ASC," +
            "  jos.id ASC", nativeQuery = true)
    List<JobOffersApplicantsDto> findSkillMatches();


    @Query(nativeQuery = true, value = "execute FullMatcher")
    List<FullMatchDto> findFullMatches();

    @Query(value = " SELECT * from Match WHERE matchStatus=0 ORDER BY matchDate DESC ", nativeQuery = true)
    List<Match> getFinalisedfMatchesOrderedByDate();

    @Query(value = " SELECT * from Match  WHERE (matchDate >= :startDate AND matchDate <= :endDate) AND matchStatus=0 ", nativeQuery = true)
    List<Match> getFinalisedfMatchesWithDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);


    @Query(value = "   select top(20) sk.title title, count(ask.skill_id) as occurences " +
            "  from [RSAPI].[dbo].[ApplicantSkill] ask" +
            "  inner join [RSAPI].[dbo].[Skill] sk" +
            "  On ask.skill_id = sk.id" +
            "  group by sk.title" +
            "  order by occurences desc", nativeQuery = true)
    List<OfferedRequestedDto> getMostOfferedSkills();


    @Query(value = "select top(20) sk.title title, count(jsk.skill_id) as occurences " +
            "  from [RSAPI].[dbo].[JobOfferSkill] jsk" +
            "  inner join [RSAPI].[dbo].[Skill] sk" +
            "  On jsk.skill_id = sk.id" +
            "  group by sk.title" +
            "  order by occurences desc", nativeQuery = true)
    List<OfferedRequestedDto> getMostRequestedSkills();


    @Query(value = "SELECT DISTINCT f.skill_id as skill, sk.title as title " +
            "FROM JobOfferSkill f LEFT JOIN ApplicantSkill s ON f.skill_id=s.skill_id " +
            "INNER JOIN Skill sk ON sk.id = f.skill_id " +
            "WHERE s.skill_id is NULL"
            , nativeQuery = true)
    List<ApplicantNotMatchedDto> getNotMatchedSkills();

    @Query(value = " SELECT * from Match WHERE matchStatus=1  ", nativeQuery = true)
    List<Match> getProposedMatches();

}
