package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.ApplicantCreationException;
import gr.codehub.rsapi.exception.ApplicantIsInactive;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.ApplicantUpdateException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepository;
import gr.codehub.rsapi.repository.ApplicantSkillRepository;
import gr.codehub.rsapi.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

//TODO Dto
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private ApplicantRepository applicantRepository;
    private ApplicantSkillRepository applicantSkillRepository;
    private SkillRepository skillRepository;
    @Autowired
    public ApplicantServiceImpl(ApplicantRepository applicantRepository, ApplicantSkillRepository applicantSkillRepository,SkillRepository skillRepository) {
        this.applicantRepository = applicantRepository;
        this.applicantSkillRepository = applicantSkillRepository;
        this.skillRepository = skillRepository;
    }

    // constructor
    @Override
    public Applicant addApplicant(ApplicantDto applicantDto) throws ApplicantCreationException {
        Applicant applicant = new Applicant();
        if (applicantDto == null) throw new ApplicantCreationException("You did not add any of the required fields");

        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setExperienceLevel(applicantDto.getExperienceLevel());
        applicant.setDegreeLevel(applicantDto.getDegreeLevel());
        applicant.setApplicantSkillList(applicantDto.getApplicantSkillList());
        applicant.setApplicationDate(LocalDate.now());
        applicant.setStatus(Status.ACTIVE);

        applicantRepository.save(applicant);
        return applicant;
    }

    @Override
    public boolean setApplicantInactive(int applicantIndex) throws ApplicantNotFoundException, ApplicantIsInactive {
        Applicant applicantInDb = applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("Cannot find applicant with id:" + applicantIndex));
        Applicant applicant;
        if (applicantInDb.getStatus().equals(Status.INACTIVE))
            throw new ApplicantIsInactive("Applicant with id:" + applicantIndex + " is already inactive.");
        else applicantInDb.setStatus(Status.ACTIVE);
        applicant = applicantInDb;
        applicant.setStatus(Status.INACTIVE);
        applicantRepository.save(applicant);
        return true;
    }

    @Override
    public Applicant getApplicant(int applicantIndex) throws ApplicantNotFoundException {
        return applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("There is no such Applicant in the DB."));
    }

    @Override
    public Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws ApplicantNotFoundException, ApplicantUpdateException {

        Applicant applicantInDb = applicantRepository.findById(applicantIndex).orElseThrow(() -> new ApplicantNotFoundException("Cannot find applicant with id:" + applicantIndex));
        if (applicantInDb.getStatus() == Status.INACTIVE)
            throw new ApplicantUpdateException("Failed to update Applicant, because the Applicant is inactive");
        applicantInDb.setFirstName(applicantDto.getFirstName());
        applicantInDb.setLastName(applicantDto.getLastName());
        applicantInDb.setStatus(Status.ACTIVE);
        applicantInDb.setAddress(applicantDto.getAddress());
        applicantInDb.setDegreeLevel(applicantDto.getDegreeLevel());
        applicantInDb.setExperienceLevel(applicantDto.getExperienceLevel());
        applicantInDb.setApplicantSkillList(applicantDto.getApplicantSkillList());
        applicantInDb.setRegion(applicantDto.getRegion());


        applicantRepository.save(applicantInDb);

        return applicantInDb;
    }

    @Override
    public List<Applicant> getApplicants() {
        return applicantRepository.findAll();
    }

    @Override
    public List<Applicant> findApplicantsByCriteria(String firstName, String lastName, Region region, LocalDate date, Skill skill) {

        return applicantRepository.findApplicantByCriteria(firstName, lastName, region, date, skill);

    }

    @Override
    public boolean deleteApplicant(int applicantIndex) throws ApplicantNotFoundException {
        applicantRepository.deleteById(applicantIndex);
        return true;
    }


    public boolean insertApplicantSkill( Applicant applicant, Skill skill) {
        ApplicantSkill applicantSkill = new ApplicantSkill();
        applicantSkill.setApplicant(applicant);
        Skill skillInDb = skillRepository.findBySkillTitle(skill.getTitle());
        if (skillInDb == null) {
            skillRepository.save(new Skill(skillInDb.getTitle()));
            return true;
        }
        return false;
    }

    @Override
    public List<Applicant> addApplicants(List<Applicant> applicants) {
        return applicantRepository.saveAll(applicants);
    }

    //PERHAPS TO BE MOVED

    @Override
    public void addApplicantSkills(List<Applicant> applicants){
        for(Applicant applicant: applicants){
            applicantSkillRepository.saveAll(applicant.getApplicantSkillList());
        }
    }

    @Override
    public Applicant addApplicant(Applicant applicant) {
        return applicantRepository.save(applicant);
    }

//
//                Skill skillInDb = skillRepository.f
//                findByName(skill).orElseThrow(() -> new ApplicantNotFoundException("Cannot find applicant with id:" + applicantIndex));



}
