package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.enums.MatchStatus;
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

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final ApplicantRepository applicantRepository;
    private final ApplicantSkillRepository applicantSkillRepository;
    private final JobOfferRepository jobOfferRepository;
    private final JobOfferService jobOfferService;
    private final ApplicantService applicantService;
    private final JobOfferSkillRepository jobOfferSkillRepository;

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
        if (checkForDuplicate(applicantIndex, jobOfferIndex)) {
            throw new MatchException("This match is already exist");
        } else {
            Match match = insertMatch(applicantIndex, jobOfferIndex);
            return match;
        }

    }

    @Override
    public Match finaliseMatch(int matchIndex) throws MatchNotFoundException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new MatchNotFoundException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.FINALISED);
        match.setMatchDate(LocalDate.now());
        return matchRepository.save(match);
    }


    @Override
    public Match insertMatch(int applicantIndex, int jobOfferIndex) throws ApplicantNotFoundException, MatchException, JobOfferNotFoundException {

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
        match.setMatchStatus(MatchStatus.UNFINALISED);
        match.setStatus(Status.ACTIVE);

        matchRepository.save(match);
        return match;

    }

    @Override
    public Match deleteMatch(int matchIndex) throws MatchNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new MatchNotFoundException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.DELETED);

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
    public boolean checkForDuplicate(int applicantIndex, int jobOfferIndex) throws MatchException, ApplicantNotFoundException, JobOfferNotFoundException {
        return matchRepository.findAll().stream().anyMatch(o -> o.getApplicant().getId() == applicantIndex && o.getJobOffer().getId() == jobOfferIndex);
    }


    @Override
    public List<JobOffersApplicantsDto> findPartialMatches() {
        return matchRepository.findSkillMatches();
    }


    public List<FullMatchDto> findFullMatches() {
        List<FullMatchDto> list = matchRepository.findFullMatches();
        list.forEach(record -> {
            try {
                createManualMatch(record.getApp(), record.getJob());
            } catch (ApplicantNotFoundException | MatchException | JobOfferNotFoundException e) {
                e.printStackTrace();
            }
        });
        return matchRepository.findFullMatches();

    }

    @Override
    public List<Match> getMostRecentFinalisedMatches() {
        return matchRepository.getFinalisedfMatchesOrderedByDate();
    }

    @Override
    public List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate) {
        return matchRepository.getFinalisedfMatchesWithDateRange(startDate, endDate);
    }

    @Override
    public List<Match> getProposedMatches() {
        return matchRepository.getProposedMatches();
    }
}