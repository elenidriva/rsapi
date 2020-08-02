package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.BusinessException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchException;
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
     * @throws BusinessException
     */
    Match createManualMatch(int applicantIndex, int jobOfferIndex) throws BusinessException;

    /**
     * Create a finalize match
     *
     * @param matchIndex
     * @return Match
     * @throws BusinessException
     */
    Match finaliseMatch(int matchIndex) throws BusinessException;

    /**
     * Delete a given match
     *
     * @param matchIndex
     * @return Match
     * @throws BusinessException
     */
    Match deleteMatch(int matchIndex) throws BusinessException;

    /**
     * Checking Duplicates of a given applicantId and JobOfferId
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws BusinessException
     */
    boolean checkForDuplicate(int applicantIndex, int jobOfferIndex) throws BusinessException;


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
     * @throws BusinessException
     */
    List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate) throws BusinessException;

    /**
     * Insert a new match
     *
     * @param applicantIndex, jobOfferIndex
     * @return Match
     * @throws BusinessException
     */
    Match insertMatch(int applicantIndex, int jobOfferIndex) throws BusinessException;

    /**
     * Get the 20 proposed matches
     *
     * @return list Match
     */
    List<Match> getProposedMatches();


}
