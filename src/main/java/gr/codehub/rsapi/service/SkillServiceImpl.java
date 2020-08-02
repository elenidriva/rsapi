package gr.codehub.rsapi.service;


import gr.codehub.rsapi.dto.SkillDto;
import gr.codehub.rsapi.exception.BusinessException;
import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.*;
import gr.codehub.rsapi.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    @Override
    public List<Skill> getSkills() {
        return skillRepository.findAll();
    }

    /**
     * This method takes the skill from the repository and passes the data needed
     * to create the applicant and saves it in the base
     *
     * @param skillDto
     * @return
     * @throws SkillCreationException       the user tried to create a skill without the required fields
     * @throws SkillNotFoundException       the user tried to find an applicant with id that does not exist
     * @throws SkillIsAlreadyExistException the user tried to add a skill tha already exist
     */
    @Override
    public Skill addSkill(SkillDto skillDto) throws BusinessException {
        Skill skill = new Skill();
        Skill skillFromDb = skillRepository.findById(skill.getId()).orElseThrow(() -> new BusinessException("Skill not found"));
        if (skillDto == null) {
            throw new SkillCreationException("Null Skill");
        }
        if (skillFromDb.equals(skillDto)) {
            throw new SkillIsAlreadyExistException("Skill Already Exists");
        }
        skill.setId(skillFromDb.getId());
        skill.setTitle(skillFromDb.getTitle());
        return skillRepository.save(skill);
    }


    @Override
    public List<Skill> splitSkill(SkillDto skillDto) throws BusinessException {
        Skill skill = new Skill();
        Skill skillFromDb = skillRepository.findSkillByTitle(skillDto.getTitle()).orElseThrow(() -> new BusinessException("Skill not found"));
        if (skillDto == null) {
            throw new SkillCreationException("Null Skill");
        }
        List<Skill> newSkillsArray = new ArrayList<>();
        String skillTitle = skillDto.getTitle();
        String[] skills = skillTitle.split(" ");
        skillFromDb.setTitle(skills[0]);
        skillRepository.save(skillFromDb);
        newSkillsArray.add(skillFromDb);
        boolean firstTime = true;
        for (String skillName : skills) {
            if (firstTime) {
                firstTime = false;
                continue;
            }
            Skill splitSkill = new Skill(skillName);
            skillRepository.save(splitSkill);
            newSkillsArray.add(splitSkill);
        }
        return newSkillsArray;
    }

    @Override
    public Skill mergeSkills(SkillDto skillDto, SkillDto skillDto2) throws BusinessException {
        Skill skillFromDb = skillRepository.findSkillByTitle(skillDto.getTitle()).orElseThrow(() -> new BusinessException("Skill not found"));
        Skill skillFromDb2 = skillRepository.findSkillByTitle(skillDto2.getTitle()).orElseThrow(() -> new BusinessException("Skill not found"));
        deleteSkill(skillFromDb.getId());
        skillFromDb2.setTitle(skillFromDb.getTitle() + " " + skillFromDb2.getTitle());
        skillRepository.save(skillFromDb2);
        return skillFromDb2;
    }

    @Override
    public List<Skill> addSkillsFromReader(List<Skill> skills) {
        for (Skill skill : skills) {
            Optional<Skill> skillOptional = skillRepository.findSkillByTitle(skill.getTitle());
            if (!skillOptional.isPresent()) {
                skillRepository.save(skill);
            } else {
                skill.setId(skillOptional.get().getId());
            }
        }
        return skills;
    }

    @Override
    public boolean deleteSkill(int skillDtoId) throws BusinessException {
        skillRepository.findById(skillDtoId).orElseThrow(() -> new BusinessException("Cannot find applicant with id:" + skillDtoId));


        skillRepository.deleteById(skillDtoId);
        return true;
    }


    @Override
    public void addApplicantSkillsFromReader(List<Applicant> applicants) {
        for (Applicant applicant : applicants) {
            for (ApplicantSkill applicantSkill : applicant.getApplicantSkillList()) {
                Optional<Skill> skillOptional = skillRepository.findSkillByTitle(applicantSkill.getSkill().getTitle());
                if (!skillOptional.isPresent()) {
                    Skill savedSkill = skillRepository.save(applicantSkill.getSkill());
                    applicantSkill.setSkill(savedSkill);
                } else {
                    applicantSkill.setSkill(skillOptional.get());
                }
            }
        }
    }

    @Override
    public void addJobOfferSkillsFromReader(List<JobOffer> jobOffers) {
        for (JobOffer jobOffer : jobOffers) {
            for (JobOfferSkill jobOfferSkill : jobOffer.getJobOfferSkillList()) {
                Optional<Skill> skillOptional = skillRepository.findSkillByTitle(jobOfferSkill.getSkill().getTitle());
                if (!skillOptional.isPresent()) {
                    Skill savedSkill = skillRepository.save(jobOfferSkill.getSkill());
                    jobOfferSkill.setSkill(savedSkill);
                } else {
                    jobOfferSkill.setSkill(skillOptional.get());
                }
            }
        }
    }


}
