package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;

import java.util.List;

public interface ReportService {

    List<OfferedRequestedDto> getMostOfferedSkills();

    List<OfferedRequestedDto> getMostRequestedSkills();

    List<ApplicantNotMatchedDto> getNotMatchedSkills();
}
