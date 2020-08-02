package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.FullMatchDto;
import gr.codehub.rsapi.dto.JobOffersApplicantsDto;
import gr.codehub.rsapi.exception.BusinessException;
import gr.codehub.rsapi.model.Match;
import gr.codehub.rsapi.service.MatchService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
public class MatchController {

    MatchService matchService;


    /**
     * Endpoint delete a mach by id
     *
     * @param matchId
     * @throws BusinessException
     */
    @DeleteMapping("match/{id}")
    public Match deleteMatch(@PathVariable int matchId) throws BusinessException {
        return matchService.deleteMatch(matchId);
    }

    /**
     * Endpoint to get the most recent filalized matches
     *
     * @return list and save in DB
     */
    @GetMapping("getMostRecentFinalisedMatches")
    public List<Match> getMostRecentFinalisedMatches() {
        return matchService.getMostRecentFinalisedMatches();
    }

    /**
     * Endpoint to make a mach finalized by id
     *
     * @return list of finalized matches and save in DB
     * @throws BusinessException
     * @parammatchId
     */
    @PutMapping("match")
    public Match finaliseMatch(@RequestParam int matchId) throws BusinessException {
        return matchService.finaliseMatch(matchId);

    }


    /**
     * Endpoint to create a manual match
     *
     * @param applicantId, jobOfferId
     * @return the manual match that recruiter create
     * @throws BusinessException
     */
    @PostMapping("match")
    public Match createManualMatch(@RequestParam int applicantId,
                                   @RequestParam int jobOfferId) throws BusinessException {
        return matchService.createManualMatch(applicantId, jobOfferId);
    }

    /**
     * Endpoint to find partial matches
     *
     * @return list JobOffersApplicantsDto
     */
    @GetMapping("partialMatches")
    public List<JobOffersApplicantsDto> findPartialMatches() {
        return matchService.findPartialMatches();
    }

    /**
     * Endpoint to full matches JobOffer- Applicants
     *
     * @return list FullMatchDto
     */
    @GetMapping("fullMatch")
    public List<FullMatchDto> findFullMatches() {
        return matchService.findFullMatches();

    }

    /**
     * Endpoint to get finalized matches with date range
     *
     * @param startDate, endDate
     * @return list of finalized Matches that the recruiter wanted (reporting)
     */
    @GetMapping("reports")
    public List<Match> getFinalisedfMatchesWithDateRange(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) throws BusinessException {
        return matchService.getFinalisedfMatchesWithDateRange(startDate, endDate);
    }

    /**
     * Endpoint to chek duplicates of a jobOffer with an Applicant
     *
     * @param applicantIndex, jobOfferIndex
     * @return boolean
     * @throws BusinessException
     */
    @GetMapping("check")
    public boolean checkForDuplicate(@RequestParam int applicantIndex, @RequestParam int jobOfferIndex) throws BusinessException {
        return matchService.checkForDuplicate(applicantIndex, jobOfferIndex);

    }

    /**
     * Endpoint get the 20 most proposed matches (reporting)
     *
     * @return list of proposed matches
     */
    @GetMapping("proposed")
    public List<Match> getProposedMatches() {
        return matchService.getProposedMatches();

    }


}
