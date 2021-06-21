package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.enums.MatchStatus;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.RCMRuntimeException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.repository.ApplicantRepository;
import gr.codehub.rsapi.repository.JobOfferRepository;
import gr.codehub.rsapi.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final ApplicantRepository applicantRepository;
    private final JobOfferRepository jobOfferRepository;

    /**
     * Create a manual match
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    @Override
    public Match createManualMatch(int applicantIndex, int jobOfferIndex) throws RCMRuntimeException {
        if (checkForDuplicate(applicantIndex, jobOfferIndex)) {
            throw new RCMRuntimeException("This match already exists");
        } else {
            Match match = insertMatch(applicantIndex, jobOfferIndex);
            log.info("Successfully creating manual matches");
            return match;
        }

    }

    /**
     * Create a finalize match
     *
     * @param matchIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    @Override
    public Match finaliseMatch(int matchIndex) throws RCMRuntimeException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new RCMRuntimeException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.FINALISED);
        match.setMatchDate(LocalDate.now());
        log.info("Successfully finalized  matches");
        return matchRepository.save(match);
    }


    /**
     * Insert a new match
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    @Override
    public Match insertMatch(int applicantIndex, int jobOfferIndex) throws RCMRuntimeException {

        Match match = new Match();

        Applicant applicant = applicantRepository.findById(applicantIndex).orElseThrow(() -> new RCMRuntimeException("Cannot find applicant with id:" + applicantIndex));
        if (!applicant.getStatus().equals((Status.ACTIVE)))
            throw new RCMRuntimeException(" Applicant is not available for Matches, since he does not exist.");

        JobOffer jobOffer = jobOfferRepository.findById(jobOfferIndex).orElseThrow(() -> new RCMRuntimeException("Cannot find JobOffer with id:" + applicantIndex));
        if (!jobOffer.getStatus().equals((Status.ACTIVE)))
            throw new RCMRuntimeException(" JobOffer is not available for Matches, since it does not exist.");

        match.setApplicant(applicant);
        match.setJobOffer(jobOffer);
        match.setMatchDate(LocalDate.now());
        match.setMatchStatus(MatchStatus.UNFINALISED);
        match.setStatus(Status.ACTIVE);

        matchRepository.save(match);
        log.info("Successfully insert  matches");
        return match;

    }

    /**
     * Delete a given match
     *
     * @param matchIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    @Override
    public Match deleteMatch(int matchIndex) throws RCMRuntimeException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new RCMRuntimeException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.DELETED);

        Applicant applicant = applicantRepository.findById(match.getApplicant().getId()).orElseThrow(() -> new RCMRuntimeException("Cannot find matched applicant with id:" + match.getApplicant().getId()));
        JobOffer jobOffer = jobOfferRepository.findById(match.getJobOffer().getId()).orElseThrow(() -> new RCMRuntimeException("Cannot find JobOffer with id:" + match.getJobOffer().getId()));

        applicant.setStatus(Status.ACTIVE);
        jobOffer.setStatus(Status.ACTIVE);

        applicantRepository.save(applicant);
        jobOfferRepository.save(jobOffer);

        matchRepository.save(match);
        log.info("Successfully delete  matches");
        return match;
    }

    /**
     * Checking Duplicates of a given applicantId and JobOfferId
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    @Override
    public boolean checkForDuplicate(int applicantIndex, int jobOfferIndex) {
        log.info("Successfully checking duplicate  matches");
        return matchRepository.findAll().stream().anyMatch(o -> o.getApplicant().getId() == applicantIndex && o.getJobOffer().getId() == jobOfferIndex);
    }

    /**
     * Find the partial matches
     *
     * @return list of JobOffersApplicantsDto
     */
    @Override
    public List<JobOffersApplicantsDto> findPartialMatches() {
        log.info("Successfully finding partial matches");
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
            } catch (RCMRuntimeException e) {
                log.info("Successfully finding full matches");
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
     * @throws RCMRuntimeException
     */
    @Override
    public List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate) throws RCMRuntimeException {
        if (startDate.equals(null) | endDate.equals(null))
            throw new RCMRuntimeException("Please define both a startDate and an endDate");
        log.info("Successfully getting finalized matches with date range");
        return matchRepository.getFinalisedfMatchesWithDateRange(startDate, endDate);
    }

    /**
     * Get the 20 proposed matches
     *
     * @return list Match
     */
    @Override
    public List<Match> getProposedMatches() {
        log.info("Successfully getting proposed");
        return matchRepository.getProposedMatches();
    }
}