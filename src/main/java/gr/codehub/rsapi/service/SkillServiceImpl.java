package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("ImplDB")
public class SkillServiceImpl implements SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    /**
     * addSkill
     * check if skill exist
     * check if skill is null
     */

    @Override
    public Skill addSkill(Skill skill) throws SkillCreationException, SkillNotFoundException, SkillIsAlreadyExistException {
        Skill skillFromDb = skillRepository.findById(skill.getId()).orElseThrow(() -> new SkillNotFoundException("Skill not found"));
        if (skill == null) {
            throw new SkillCreationException("Null Skill");
        }
        if (skillFromDb.equals(skill)) {
            throw new SkillIsAlreadyExistException("Skill Already Exists");
        }
        skill.setId(skillFromDb.getId());
        skill.setTitle(skillFromDb.getTitle());
        return skillRepository.save(skill);
    }

    @Override
    public List<Skill> addSkillsFromReader(List<Skill> skills) {
        for(Skill skill: skills){
            Optional<Skill> skillOptional = skillRepository.findSkillByTitle(skill.getTitle());
            if(!skillOptional.isPresent()){
                skillRepository.save(skill);
            }
            //if the skill exists, then get its id
            else{
                skill.setId(skillOptional.get().getId());
            }
        }
        return skills;
    }

    @Override
    public void addApplicantSkillsFromReader(List<Applicant> applicants) {
        for(Applicant applicant: applicants){
            for(ApplicantSkill applicantSkill: applicant.getApplicantSkillList()){
                Optional<Skill> skillOptional = skillRepository.findSkillByTitle(applicantSkill.getSkill().getTitle());
                if(!skillOptional.isPresent()){
                    Skill savedSkill = skillRepository.save(applicantSkill.getSkill());
                    applicantSkill.setSkill(savedSkill);
                }
                //else set found skill to current applicant (to get the database id)
                else{
                    applicantSkill.setSkill(skillOptional.get());
                }
            }
        }
    }
    @Override
    public void addJobOfferSkillsFromReader(List<JobOffer> jobOffers) {
        for(JobOffer jobOffer: jobOffers){
            for(JobOfferSkill jobOfferSkill: jobOffer.getJobOfferSkillList()){
                Optional<Skill> skillOptional = skillRepository.findSkillByTitle(jobOfferSkill.getSkill().getTitle());
                if(!skillOptional.isPresent()){
                    Skill savedSkill = skillRepository.save(jobOfferSkill.getSkill());
                    jobOfferSkill.setSkill(savedSkill);
                }
                //else set found skill to current applicant (to get the database id)
                else{
                    jobOfferSkill.setSkill(skillOptional.get());
                }
            }
        }
    }


}
