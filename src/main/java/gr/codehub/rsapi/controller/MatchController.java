package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.MatchException;
import gr.codehub.rsapi.exception.MatchNotFoundException;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class MatchController {

    MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }


    @DeleteMapping("match/{id}")
    public Match deleteMatch(@PathVariable int matchId) throws MatchNotFoundException, ApplicantNotFoundException, JobOfferNotFoundException {
        return matchService.deleteMatch(matchId);
    }

    @GetMapping("match")
    public List<Match> viewUnfinalisedMatches() {
        return matchService.viewUnfinalisedMatches();
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


    @GetMapping("getMostRecentFinalisedMatches")
    public List<Match> getMostRecentMatches() {
        return matchService.getMostRecentFinalisedMatches();
    }


    @GetMapping("partialMatches")
   public List<JobOffersApplicantsDto> findPartialMatches(){
        return matchService.findPartialMatches();
    }

    @GetMapping("fullMatch")
    public List<FullMatchDto> findFullMatches(){
        return matchService.findFullMatches();

    }


    @GetMapping("reports")
    public List<Match> getFinalisedfMatchesWithDateRange(
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate)  {
        return matchService.getFinalisedfMatchesWithDateRange(startDate,endDate );
    }

}
