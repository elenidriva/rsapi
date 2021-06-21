package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.exception.RCMRuntimeException;
import gr.codehub.rsapi.model.Match;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Match service.
 */
public interface MatchService {

    /**
     * Create a manual match
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    Match createManualMatch(int applicantIndex, int jobOfferIndex) throws RCMRuntimeException;

    /**
     * Create a finalize match
     *
     * @param matchIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    Match finaliseMatch(int matchIndex) throws RCMRuntimeException;

    /**
     * Delete a given match
     *
     * @param matchIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    Match deleteMatch(int matchIndex) throws RCMRuntimeException;

    /**
     * Checking Duplicates of a given applicantId and JobOfferId
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    boolean checkForDuplicate(int applicantIndex, int jobOfferIndex) throws RCMRuntimeException;


    /**
     * Get the most recent finalized matches
     *
     * @return list of matches
     */
    List<Match> getMostRecentFinalisedMatches();

    /**
     * Find the partial matches
     *
     * @return list of JobOffersApplicantsDto
     */
    List<JobOffersApplicantsDto> findPartialMatches();

    /**
     * Find full matches
     *
     * @return list of FullMatchDto
     */
    List<FullMatchDto> findFullMatches();


    /**
     * Get the finalized matches of a date range (reporting)
     *
     * @param startDate, endDate
     * @return list of Match
     * @throws RCMRuntimeException
     */
    List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate) throws RCMRuntimeException;

    /**
     * Insert a new match
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws RCMRuntimeException
     */
    Match insertMatch(int applicantIndex, int jobOfferIndex) throws RCMRuntimeException;

    /**
     * Get the 20 proposed matches
     *
     * @return list Match
     */
    List<Match> getProposedMatches();


}
