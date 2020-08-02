package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
public class MatchController {

    MatchService matchService;


    @DeleteMapping("match/{id}")
    public Match deleteMatch(@PathVariable int matchId) throws MatchNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException {
        return matchService.deleteMatch(matchId);
    }

    @GetMapping("getMostRecentFinalisedMatches")
    public List<Match> getMostRecentFinalisedMatches() {
        return matchService.getMostRecentFinalisedMatches();
    }

    @PutMapping("match")
    public Match finaliseMatch(@RequestParam int matchId) throws MatchNotFoundException {
        return matchService.finaliseMatch(matchId);

    }

    @PostMapping("match")
    public Match createManualMatch(@RequestParam int applicantId,
                                   @RequestParam int jobOfferId) throws MatchException, ApplicantNotFoundException, JobOfferNotFoundException {
        return matchService.createManualMatch(applicantId, jobOfferId);
    }


    @GetMapping("partialMatches")
    public List<JobOffersApplicantsDto> findPartialMatches() {
        return matchService.findPartialMatches();
    }

    @GetMapping("fullMatch")
    public List<FullMatchDto> findFullMatches() {
        return matchService.findFullMatches();

    }

    @GetMapping("reports")
    public List<Match> getFinalisedfMatchesWithDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws BusinessException {
        return matchService.getFinalisedfMatchesWithDateRange(startDate, endDate);
    }

    @GetMapping("check")
    public boolean checkForDuplicate(@RequestParam int applicantIndex, @RequestParam int jobOfferIndex) throws MatchException, ApplicantNotFoundException, JobOfferNotFoundException {
        return matchService.checkForDuplicate(applicantIndex, jobOfferIndex);

    }

    @GetMapping("proposed")
    public List<Match> getProposedMatches() throws MatchException, ApplicantNotFoundException, JobOfferNotFoundException {
        return matchService.getProposedMatches();

    }


}
