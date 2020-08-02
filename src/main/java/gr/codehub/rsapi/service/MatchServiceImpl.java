package gr.codehub.rsapi.service;


import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.enums.MatchStatus;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.BusinessException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.repository.ApplicantRepository;
import gr.codehub.rsapi.repository.JobOfferRepository;
import gr.codehub.rsapi.repository.MatchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final ApplicantRepository applicantRepository;
    private final JobOfferRepository jobOfferRepository;


    @Override
    public Match createManualMatch(int applicantIndex, int jobOfferIndex) throws BusinessException {
        if (checkForDuplicate(applicantIndex, jobOfferIndex)) {
            throw new BusinessException("This match already exists");
        } else {
            Match match = insertMatch(applicantIndex, jobOfferIndex);
            return match;
        }

    }

    @Override
    public Match finaliseMatch(int matchIndex) throws BusinessException {
        Match match = matchRepository.findById(matchIndex).orElseThrow(() -> new BusinessException("There is no Match with id: " + matchIndex));
        match.setMatchStatus(MatchStatus.FINALISED);
        match.setMatchDate(LocalDate.now());
        return matchRepository.save(match);
    }


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
        return match;

    }

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
        return match;
    }

    @Override
    public boolean checkForDuplicate(int applicantIndex, int jobOfferIndex) {
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
            } catch (BusinessException e) {
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
    public List<Match> getFinalisedfMatchesWithDateRange(LocalDate startDate, LocalDate endDate) throws BusinessException {
        if (startDate.equals(null) | endDate.equals(null))
            throw new BusinessException("Please define both a startDate and an endDate");
        return matchRepository.getFinalisedfMatchesWithDateRange(startDate, endDate);
    }

    @Override
    public List<Match> getProposedMatches() {
        return matchRepository.getProposedMatches();
    }
}