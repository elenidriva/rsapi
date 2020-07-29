package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.ApplicantCreationException;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepository;
import gr.codehub.rsapi.repository.ApplicantSkillRepository;
import gr.codehub.rsapi.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

//TODO Dto
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private ApplicantRepository applicantRepository;
    private ApplicantSkillRepository applicantSkillRepository;
    private SkillRepository skillRepository;

    @Autowired
    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantSkillRepository applicantSkillRepository, SkillRepository skillRepository) {
        this.applicantRepository = applicantRepository;
        this.applicantSkillRepository = applicantSkillRepository;
        this.skillRepository = skillRepository;
    }

    // constructor
    @Override
    public Applicant addApplicant(ApplicantDto applicantDto) throws ApplicantCreationException {
        Applicant applicant = new Applicant();
        if (applicantDto == null) throw new ApplicantCreationException("Null Exception");
        if (applicantDto.getEmail() == null || !applicantDto.getEmail().contains("@"))
            throw new ApplicantCreationException(" Email does not contain @");
        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setExperienceLevel(applicantDto.getExperienceLevel());
        applicant.setDegreeLevel(applicantDto.getDegreeLevel());
        applicant.setApplicantSkillList(applicantDto.getApplicantSkillList());
        applicant.setEmail(applicantDto.getEmail());
        applicant.setApplicationDate(applicantDto.getApplicationDate());
        applicant.setStatus(applicantDto.getStatus());

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
        return applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("There is no such Applicant in the DB."));
    }

    @Override
    public Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws ApplicantNotFoundException {

        Applicant applicant = applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("There is no such Applicant."));

        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setStatus(applicantDto.getStatus());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setDegreeLevel(applicantDto.getDegreeLevel());
        applicant.setExperienceLevel(applicantDto.getExperienceLevel());
        applicant.setApplicantSkillList(applicantDto.getApplicantSkillList());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setEmail(applicantDto.getEmail());


        applicantRepository.save(applicant);

        return applicant;
    }

    @Override
    public List<Applicant> getApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public List<Applicant> findApplicantsByCriteria(String lastName, Region region, Date date, Skill skill) {

        return applicantRepository.findApplicantByCriteria(lastName, region, date, skill);

    }

    @Override
    public void addApplicantSkills(List<Applicant> applicants) {
        for (Applicant applicant : applicants) {
            applicantSkillRepository.saveAll(applicant.getApplicantSkillList());
        }
    }

    @Override
    public List<Applicant> addApplicants(List<Applicant> applicants) {
        return applicantRepository.saveAll(applicants);
    }

    @Override
    public Applicant addApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }
}
