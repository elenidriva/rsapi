package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.ApplicantCreationException;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;

import java.util.Date;
import java.util.List;

public interface ApplicantService {

    Applicant addApplicant(ApplicantDto applicantDto) throws ApplicantCreationException;

    boolean setApplicantStatus(int applicantIndex) throws ApplicantNotFoundException;  //set inactive

    Applicant getApplicant(int applicantIndex) throws ApplicantNotFoundException;

    Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws ApplicantNotFoundException;

    List<Applicant> getApplicants();

    List<Applicant> findApplicantsByCriteria(String lastName, Region region, Date date, Skill skill);

    List<Applicant> addApplicants(List<Applicant> applicants);

    //PERHAPS TO BE MOVED
    void addApplicantSkills(List<Applicant> applicants);

    Applicant addApplicant(Applicant applicant);
}
