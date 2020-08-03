package gr.codehub.rsapi.service;


import gr.codehub.rsapi.dto.ApplicantNotMatchedDto;
import gr.codehub.rsapi.dto.OfferedRequestedDto;

import java.util.List;

/**
 * The interface Report service.
 */
public interface ReportService {

    /**
     * Get the most offered skills
     *
     * @return list OfferedRequestedDto
     */
    List<OfferedRequestedDto> getMostOfferedSkills();

    /**
     * Get the most requested skills
     *
     * @return list OfferedRequestedDto
     */
    List<OfferedRequestedDto> getMostRequestedSkills();

    /**
     * Get a list of applicants that do not match with JobOffer
     *
     * @return list ApplicantNotMatchedDto
     */
    List<ApplicantNotMatchedDto> getNotMatchedSkills();
}
