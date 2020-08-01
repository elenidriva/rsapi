package gr.codehub.rsapi.controller;
import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;
import gr.codehub.rsapi.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
public class ReportController {

    private ReportService reportService;


    @GetMapping("requested")
    public List<OfferedRequestedDto> getMostRequestedSkills() {
        return reportService.getMostRequestedSkills();
    }

    @GetMapping("offered")
    public List<OfferedRequestedDto> getMostOfferedSkills() {
        return reportService.getMostOfferedSkills();
    }


    @GetMapping("notMatchedSkills")
    public List<ApplicantNotMatchedDto> getNotMatchedSkills() {
        return reportService.getNotMatchedSkills();

    }


}