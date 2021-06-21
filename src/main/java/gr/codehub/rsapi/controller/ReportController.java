package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController("reports")
public class ReportController {

    private final ReportService reportService;


    /**
     * Endpoint get the 20 most requested matches (reporting)
     *
     * @return list of requested matches
     */
    @GetMapping("/getMostRequestedSkills")
    public List<OfferedRequestedDto> getMostRequestedSkills() {
        return reportService.getMostRequestedSkills();
    }

    /**
     * Endpoint get the most offered Skills
     *
     * @return list of offered skills
     */
    @GetMapping("/getMostOfferedSkills")
    public List<OfferedRequestedDto> getMostOfferedSkills() {
        return reportService.getMostOfferedSkills();
    }


    /**
     * Endpoint get not matches Applicant with JobOffer
     *
     * @return list of ApplicantNotMatchedDto
     */
    @GetMapping("/getNotMatchedSkills")
    public List<ApplicantNotMatchedDto> getNotMatchedSkills() {
        return reportService.getNotMatchedSkills();

    }


}