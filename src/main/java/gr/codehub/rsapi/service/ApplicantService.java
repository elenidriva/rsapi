package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.ApplicantException;
import gr.codehub.rsapi.model.Applicant;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Applicant service.
 */
public interface ApplicantService {

    Applicant addApplicant(ApplicantDto applicantDto) throws ApplicantException;

    boolean setApplicantInactive(int applicantIndex) throws ApplicantException;

    Applicant getApplicant(int applicantIndex) throws ApplicantException;

    Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws ApplicantException;

    List<Applicant> getApplicants();

    List<Applicant> findApplicantsByCriteria(String firstName, String lastName, Region region, LocalDate date);

    List<Applicant> addApplicants(List<Applicant> applicants);

    void addApplicantSkills(List<Applicant> applicants);

}
