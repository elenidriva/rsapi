package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.ApplicantCreationException;
import gr.codehub.rsapi.exception.ApplicantIsInactive;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.ApplicantUpdateException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;

import java.time.LocalDate;
import java.util.List;

public interface ApplicantService {

    Applicant addApplicant(ApplicantDto applicantDto) throws ApplicantCreationException;

    boolean setApplicantInactive(int applicantIndex) throws ApplicantNotFoundException, ApplicantIsInactive;

    Applicant getApplicant(int applicantIndex) throws ApplicantNotFoundException;

    Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws ApplicantNotFoundException, ApplicantUpdateException;

    List<Applicant> getApplicants();

    List<Applicant> findApplicantsByCriteria(String firstName, String lastName, Region region, LocalDate date, Skill skill);

    boolean deleteApplicant(int applicantIndex) throws ApplicantNotFoundException;
}
