package gr.codehub.rsapi.service;

import gr.codehub.rsapi.enums.MatchStatus;
import gr.codehub.rsapi.enums.MatchType;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchServiceImpl implements MatchService {

    private MatchRepository matchRepository;
    private ApplicantRepository applicantRepository;
    private ApplicantSkillRepository applicantSkillRepository;
    private JobOfferRepository jobOfferRepository;
    private JobOfferService jobOfferService;
    private ApplicantService applicantService;
    private JobOfferSkillRepository jobOfferSkillRepository;

    public MatchServiceImpl(MatchRepository matchRepository,
                            ApplicantRepository applicantRepository,
                            ApplicantSkillRepository applicantSkillRepository,
                            JobOfferRepository jobOfferRepository,
                            JobOfferService jobOfferService,
                            ApplicantService applicantService,
                            JobOfferSkillRepository jobOfferSkillRepository) {

        this.matchRepository = matchRepository;
        this.applicantRepository = applicantRepository;
        this.applicantSkillRepository = applicantSkillRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.jobOfferService = jobOfferService;
        this.applicantService = applicantService;
        this.jobOfferSkillRepository = jobOfferSkillRepository;
    }

    @Override
    public Match createManualMatch(int applicantIndex, int jobOfferIndex) throws ApplicantNotFoundException, MatchException, JobOfferNotFoundException {
        Match match = new Match();

        Applicant applicant = applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("Cannot find applicant with id:" + applicantIndex));
        if (!applicant.getStatus().equals((Status.ACTIVE)))
            throw new MatchException(" Applicant is not available for Matches, since he does not exist.");

        JobOffer jobOffer = jobOfferRepository.findById(jobOfferIndex).orElseThrow(() -> new JobOfferNotFoundException("Cannot find JobOffer with id:" + applicantIndex));
        if (!jobOffer.getStatus().equals((Status.ACTIVE)))
            throw new MatchException(" JobOffer is not available for Matches, since it does not exist.");

        match.setApplicant(applicant);
        match.setJobOffer(jobOffer);
        match.setMatchDate(LocalDate.now());
        match.setMatchType(MatchType.MANUALLY);
        match.setMatchStatus(MatchStatus.UNFINALISED); // We need to do finalisation manually in another method

        matchRepository.save(match);
        return match;

    }


    @Override
    public Match finaliseMatch(int matchIndex) throws MatchNotFoundException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new MatchNotFoundException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.FINALISED);
        //keep date for Reporter > 4.
        match.setMatchDate(LocalDate.now());
        matchRepository.save(match);

        return match;
    }

    @Override
    public Match deleteMatch(int matchIndex) throws MatchNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new MatchNotFoundException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.DELETED);
        // When we delete a Match, we want to set the Applicant and JobOffer free.
        Applicant applicant = applicantRepository.findById(match.getApplicant().getId()).orElseThrow(() -> new ApplicantNotFoundException("Cannot find matched applicant with id:" + match.getApplicant().getId()));
        JobOffer jobOffer = jobOfferRepository.findById(match.getJobOffer().getId()).orElseThrow(() -> new JobOfferNotFoundException("Cannot find JobOffer with id:" + match.getJobOffer().getId()));

        applicant.setStatus(Status.ACTIVE);
        jobOffer.setStatus(Status.ACTIVE);

        applicantRepository.save(applicant);
        jobOfferRepository.save(jobOffer);

        matchRepository.save(match);
        return match;
    }


    @Override
    public List<Match> viewUnfinalisedMatches() {
        return matchRepository
                .findAll()
                .stream()
                .filter(um -> um.getMatchStatus().equals(MatchStatus.UNFINALISED))
                .collect(Collectors.toList());

    }

    @Override
    public void autoMatchJobOfferToApplicant(int applicantIndex, int jobOfferIndex) throws JobOfferNotFoundException, ApplicantNotFoundException {
        Match match = new Match();

        Applicant applicant = applicantRepository.findById(match.getApplicant().getId()).orElseThrow(() -> new ApplicantNotFoundException("Cannot find matched applicant with id:" + match.getApplicant().getId()));
        JobOffer jobOffer = jobOfferRepository.findById(match.getJobOffer().getId()).orElseThrow(() -> new JobOfferNotFoundException("Cannot find JobOffer with id:" + match.getJobOffer().getId()));

        match.setApplicant(applicant);
        match.setJobOffer(jobOffer);
        match.setMatchDate(LocalDate.now());
        match.setMatchType(MatchType.AUTOMATICALLY);
        match.setMatchStatus(MatchStatus.UNFINALISED); // We need to do finalisation manually in another method

        applicantRepository.save(applicant);
        jobOfferRepository.save(jobOffer);

    }

    @Override
    public List<JobOffersApplicantsDto> findSkillMatches() {
        return matchRepository.findSkillMatches();
    }


    public List<Match> createAutomaticMatches() {
        List<Integer> intListOfActiveJobOffers = jobOfferRepository
                .findAll()
                .stream()
                .filter(um -> um.getStatus().equals(Status.ACTIVE))
                .map(JobOffer::getId)
                .collect(Collectors.toList());

        for (int jobOfferId : intListOfActiveJobOffers) {

        }
        return null;
    }

    public List<Match> getMostRecentFinalisedMatches() {
        return matchRepository.getFinalisedfMatchesOrderedByDate();
    }


}
