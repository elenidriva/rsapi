package gr.codehub.rsapi.service;

import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//TODO Dto
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private ApplicantRepository applicantRepository;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    // constructor
    @Override
    public Applicant addApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

    @Override
    public boolean setApplicantStatus(int applicantIndex) throws ApplicantNotFoundException {
        Applicant applicantInDb = applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("Cannot change Applicant's status, since there is no such Applicant in the DB."));

        if (applicantInDb.getStatus().equals(Status.ACTIVE)) applicantInDb.setStatus(Status.INACTIVE);
        else applicantInDb.setStatus(Status.ACTIVE);

        applicantRepository.save(applicantInDb);
        return true;
    }

    @Override
    public Applicant getApplicant(int applicantIndex) throws ApplicantNotFoundException {
        return applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("There is no such Customer in the DB."));
    }

    @Override
    public Applicant updateApplicant(Applicant applicant, int applicantIndex) throws ApplicantNotFoundException {
        Applicant applicantInDb = applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("There is no such Applicant."));

        applicantInDb.setFirstName(applicant.getFirstName());
        applicantInDb.setLastName(applicant.getLastName());
        applicantInDb.setStatus(applicant.getStatus());
        applicantInDb.setAddress(applicant.getAddress());
        applicantInDb.setDegreeLevel(applicant.getDegreeLevel());
        applicantInDb.setExperienceLevel(applicant.getExperienceLevel());
        applicantInDb.setApplicantSkillList(applicant.getApplicantSkillList());
        applicantInDb.setRegion(applicant.getRegion());

        applicantRepository.save(applicantInDb);

        return applicantInDb;
    }

    @Override
    public List<Applicant> getApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public List<Applicant> findApplicantsByCriteria(String lastName, Region region, Date date, Skill skill) {

        return applicantRepository.findApplicantByCriteria(lastName, region, date, skill);

    }

}
