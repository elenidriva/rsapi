package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.enums.MatchStatus;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.BusinessException;
import gr.codehub.rsapi.logging.SLF4JExample;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.repository.ApplicantRepository;
import gr.codehub.rsapi.repository.JobOfferRepository;
import gr.codehub.rsapi.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final ApplicantRepository applicantRepository;
    private final JobOfferRepository jobOfferRepository;
    private static final Logger logger = LoggerFactory.getLogger(SLF4JExample.class);


    /**
     * Create a manual match
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws BusinessException
     */
    @Override
    public Match createManualMatch(int applicantIndex, int jobOfferIndex) throws BusinessException {
        if (checkForDuplicate(applicantIndex, jobOfferIndex)) {
            throw new BusinessException("This match already exists");
        } else {
            Match match = insertMatch(applicantIndex, jobOfferIndex);
            logger.info("Successfully creating manual matches");
            return match;
        }

    }

    /**
     * Create a finalize match
     *
     * @param matchIndex
     * @return Match
     * @throws BusinessException
     */
    @Override
    public Match finaliseMatch(int matchIndex) throws BusinessException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new BusinessException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.FINALISED);
        match.setMatchDate(LocalDate.now());
        logger.info("Successfully finalized  matches");
        return matchRepository.save(match);
    }


    /**
     * Insert a new match
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws BusinessException
     */
    @Override
    public Match insertMatch(int applicantIndex, int jobOfferIndex) throws BusinessException {

        Match match = new Match();

        Applicant applicant = applicantRepository.findById(applicantIndex).orElseThrow(() -> new BusinessException("Cannot find applicant with id:" + applicantIndex));
        if (!applicant.getStatus().equals((Status.ACTIVE)))
            throw new BusinessException(" Applicant is not available for Matches, since he does not exist.");

        JobOffer jobOffer = jobOfferRepository.findById(jobOfferIndex).orElseThrow(() -> new BusinessException("Cannot find JobOffer with id:" + applicantIndex));
        if (!jobOffer.getStatus().equals((Status.ACTIVE)))
            throw new BusinessException(" JobOffer is not available for Matches, since it does not exist.");

        match.setApplicant(applicant);
        match.setJobOffer(jobOffer);
        match.setMatchDate(LocalDate.now());
        match.setMatchStatus(MatchStatus.UNFINALISED);
        match.setStatus(Status.ACTIVE);

        matchRepository.save(match);
        logger.info("Successfully insert  matches");
        return match;

    }

    /**
     * Delete a given match
     *
     * @param matchIndex
     * @return Match
     * @throws BusinessException
     */
    @Override
    public Match deleteMatch(int matchIndex) throws BusinessException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new BusinessException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.DELETED);

        Applicant applicant = applicantRepository.findById(match.getApplicant().getId()).orElseThrow(() -> new BusinessException("Cannot find matched applicant with id:" + match.getApplicant().getId()));
        JobOffer jobOffer = jobOfferRepository.findById(match.getJobOffer().getId()).orElseThrow(() -> new BusinessException("Cannot find JobOffer with id:" + match.getJobOffer().getId()));

        applicant.setStatus(Status.ACTIVE);
        jobOffer.setStatus(Status.ACTIVE);

        applicantRepository.save(applicant);
        jobOfferRepository.save(jobOffer);

        matchRepository.save(match);
        logger.info("Successfully delete  matches");
        return match;
    }

    /**
     * Checking Duplicates of a given applicantId and JobOfferId
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws BusinessException
     */
    @Override
    public boolean checkForDuplicate(int applicantIndex, int jobOfferIndex) {
        logger.info("Successfully checking duplicate  matches");
        return matchRepository.findAll().stream().anyMatch(o -> o.getApplicant().getId() == applicantIndex && o.getJobOffer().getId() == jobOfferIndex);
    }

    /**
     * Find the partial matches
     *
     * @return list of JobOffersApplicantsDto
     */
    @Override
    public List<JobOffersApplicantsDto> findPartialMatches() {
        logger.info("Successfully finding partial matches");
        return matchRepository.findSkillMatches();
    }


    /**
     * Find full matches
     *
     * @return list of FullMatchDto
     */
    public List<FullMatchDto> findFullMatches() {
        List<FullMatchDto> list = matchRepository.findFullMatches();
        list.forEach(record -> {
            try {
                createManualMatch(record.getApp(), record.getJob());
            } catch (BusinessException e) {
                logger.info("Successfully finding full matches");
            }
        });
        return matchRepository.findFullMatches();

    }

    /**
     * Get the most recent finalized matches
     *
     * @return list of matches
     */
    @Override
    public List<Match> getMostRecentFinalisedMatches() {
        return matchRepository.getFinalisedfMatchesOrderedByDate();
    }

    /**
     * Get the finalized matches of a date range (reporting)
     *
     * @param startDate, endDate
     * @return list of Match
     * @throws BusinessException
     */
    @Override
    public List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate) throws BusinessException {
        if (startDate.equals(null) | endDate.equals(null))
            throw new BusinessException("Please define both a startDate and an endDate");
        logger.info("Successfully getting finalized matches with date range");
        return matchRepository.getFinalisedfMatchesWithDateRange(startDate, endDate);
    }

    /**
     * Get the 20 proposed matches
     *
     * @return list Match
     */
    @Override
    public List<Match> getProposedMatches() {
        logger.info("Successfully getting proposed");
        return matchRepository.getProposedMatches();
    }
}