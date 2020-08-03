package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.logging.SLF4JExample;
import gr.codehub.rsapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ReportController {

    private ReportService reportService;
    private static final Logger logger = LoggerFactory.getLogger(SLF4JExample.class);

    /**
     * Endpoint get the 20 most requested matches (reporting)
     *
     * @return list of requested matches
     */
    @GetMapping("requested")
    public List<OfferedRequestedDto> getMostRequestedSkills() {
        return reportService.getMostRequestedSkills();
    }

    /**
     * Endpoint get the most offered Skills
     *
     * @return list of offered skills
     */
    @GetMapping("offered")
    public List<OfferedRequestedDto> getMostOfferedSkills() {
        return reportService.getMostOfferedSkills();
    }


    /**
     * Endpoint get not matches Applicant with JobOffer
     *
     * @return list of ApplicantNotMatchedDto
     */
    @GetMapping("notMatchedSkills")
    public List<ApplicantNotMatchedDto> getNotMatchedSkills() {
        return reportService.getNotMatchedSkills();

    }


}
