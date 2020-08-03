package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Applicant service.
 */
public interface ApplicantService {

    /**
     * Add applicant applicant.
     *
     * @param applicantDto the applicant dto
     * @return the applicant
     * @throws ApplicantCreationException the applicant creation exception
     */
    Applicant addApplicant(ApplicantDto applicantDto) throws BusinessException;

    /**
     * Sets applicant inactive.
     *
     * @param applicantIndex the applicant index
     * @return the applicant inactive
     * @throws ApplicantNotFoundException the applicant not found exception
     * @throws ApplicantIsInactive        the applicant is inactive
     */
    boolean setApplicantInactive(int applicantIndex) throws BusinessException;

    /**
     * Gets applicant.
     *
     * @param applicantIndex the applicant index
     * @return the applicant
     * @throws ApplicantNotFoundException the applicant not found exception
     */
    Applicant getApplicant(int applicantIndex) throws BusinessException;

    /**
     * Update applicant applicant.
     *
     * @param applicantDto   the applicant dto
     * @param applicantIndex the applicant index
     * @return the applicant
     * @throws ApplicantNotFoundException the applicant not found exception
     * @throws ApplicantUpdateException   the applicant update exception
     */
    Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws BusinessException;

    /**
     * Gets applicants.
     *
     * @return the applicants
     */
    List<Applicant> getApplicants();

    /**
     * Find applicants by criteria list.
     *
     * @param firstName the first name
     * @param lastName  the last name
     * @param region    the region
     * @param date      the date
     * @return the list
     * @throws ApplicantNotFoundException the applicant not found exception
     */
    List<Applicant> findApplicantsByCriteria(String firstName, String lastName, Region region, LocalDate date);

    /**
     * Delete applicant boolean.
     *
     * @param applicantIndex the applicant index
     * @return the boolean
     * @throws ApplicantNotFoundException the applicant not found exception
     */
    boolean deleteApplicant(int applicantIndex) throws BusinessException;

    /**
     * Add applicants list.
     *
     * @param applicants the applicants
     * @return the list
     */
    List<Applicant> addApplicants(List<Applicant> applicants);

    /**
     * Add applicant skills.
     *
     * @param applicants the applicants
     */
    void addApplicantSkills(List<Applicant> applicants);

    Applicant addApplicant(Applicant applicant);

}
