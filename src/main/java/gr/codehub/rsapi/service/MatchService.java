package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.repository.JobOffersApplicantsDto;

import java.util.List;

public interface MatchService {

    public Match createManualMatch(int applicantIndex, int jobOfferIndex) throws ApplicantNotFoundException, MatchException, JobOfferNotFoundException; // exceptions for job/aaplicant not found
    // Status needs to be active and be set to inactive & matchDate & MatchType & MatchStatus

    public Match finaliseMatch(int matchIndex) throws MatchNotFoundException; // exception not found
    //finalise it and set matchDate

    public Match deleteMatch(int matchIndex) throws MatchNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException; // exception not found
    // Set matchStatus to deleted and we need to make the job and applicant active again so that they are available to get matched again

    //editMatch ??

    public List<Match> createAutomaticMatches();

    public List<Match> viewUnfinalisedMatches(); // MatcherEngine > 2 View

    public void autoMatchJobOfferToApplicant( int applicantIndex, int jobOfferIndex) throws JobOfferNotFoundException, ApplicantNotFoundException;

    List<JobOffersApplicantsDto> findSkillMatches();

    List<Match> getMostRecentFinalisedMatches();
}
