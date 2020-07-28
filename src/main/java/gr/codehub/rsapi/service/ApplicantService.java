package gr.codehub.rsapi.service;

import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;

import java.util.Date;
import java.util.List;

public interface ApplicantService {

    Applicant addApplicant(Applicant applicant);

    boolean setApplicantStatus(int applicantIndex) throws ApplicantNotFoundException;  //set inactive

    Applicant getApplicant(int applicantIndex) throws ApplicantNotFoundException;

    Applicant updateApplicant(Applicant applicant, int applicantIndex) throws ApplicantNotFoundException;

    List<Applicant> getApplicants();

    List<Applicant> findApplicantsByCriteria(String lastName, Region region, Date date, Skill skill);

}
