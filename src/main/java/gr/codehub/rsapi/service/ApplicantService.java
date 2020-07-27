package gr.codehub.rsapi.service;

import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;

import java.util.Date;
import java.util.List;

public interface ApplicantService {

    Applicant addApplicant(Applicant applicant);

    boolean setApplicantStatus(int applicantIndex);  //set inactive

    Applicant getApplicant(int applicantIndex);

    Applicant updateApplicant(Applicant applicant, int applicantIndex);
// and more


    List<Applicant> getApplicants();

    List<Applicant> getApplicants(Date date, Region region, String firstName, Skill skill);
}
