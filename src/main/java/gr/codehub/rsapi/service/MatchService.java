package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.Match;

import java.time.LocalDate;
import java.util.List;

public interface MatchService {

    Match createManualMatch(int applicantIndex, int jobOfferIndex) throws ApplicantNotFoundException, MatchException, JobOfferNotFoundException;

    Match finaliseMatch(int matchIndex) throws MatchNotFoundException;

    Match deleteMatch(int matchIndex) throws MatchNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException;

    List<Match> viewUnfinalisedMatches();

    List<Match> getMostRecentFinalisedMatches();

    List<JobOffersApplicantsDto> findPartialMatches();

    List<FullMatchDto> findFullMatches();

    List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate);

}
