package gr.codehub.rsapi.service;

import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ApplicantServiceImpl implements ApplicantService {
    @Autowired
    private ApplicantRepository applicantRepository;
    // constructor
    @Override
    public Applicant addApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @Override
    public boolean setApplicantStatus(int applicantIndex) {
        return false;
    }

    @Override
    public Applicant getApplicant(int applicantIndex) {
        return applicantRepository.findById(applicantIndex).get();
    }

    @Override
    public Applicant updateApplicant(Applicant applicant, int applicantIndex) {
        Applicant applicantInDb = applicantRepository.findById(applicantIndex).get();//.orElseThrow(() -> new CustomerNotFoundException("Not such customer"));
        applicantInDb.setFirstName(applicant.getFirstName());
        applicantInDb.setLastName(applicant.getLastName());

        applicantRepository.save(applicantInDb);

        return applicantInDb;
    }

    @Override
    public List<Applicant> getApplicants() {
       return applicantRepository.findAll();
    }

    @Override
    public List<Applicant> getApplicants(Date date, Region region, String firstName, Skill skill) {
        return null;
    }

}
