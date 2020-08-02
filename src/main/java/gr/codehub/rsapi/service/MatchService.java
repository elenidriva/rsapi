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

public interface MatchService {

    Match createManualMatch(int applicantIndex, int jobOfferIndex) throws BusinessException;

    Match finaliseMatch(int matchIndex) throws BusinessException;

    Match deleteMatch(int matchIndex) throws BusinessException;

    boolean checkForDuplicate(int applicantIndex, int jobOfferIndex) throws MatchException, ApplicantNotFoundException, JobOfferNotFoundException;

    List<Match> getMostRecentFinalisedMatches();

    List<JobOffersApplicantsDto> findPartialMatches();

    List<FullMatchDto> findFullMatches();

    List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate) throws BusinessException;

    Match insertMatch(int applicantIndex, int jobOfferIndex) throws BusinessException;

    List<Match> getProposedMatches();


}
