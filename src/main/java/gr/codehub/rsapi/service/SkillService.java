package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.SkillDto;
import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkills();

    Skill addSkill(SkillDto skillDto) throws SkillCreationException, SkillNotFoundException, SkillIsAlreadyExistException;

    List<Skill> splitSkill(SkillDto skillDto) throws SkillNotFoundException, SkillCreationException, SkillIsAlreadyExistException;

    Skill mergeSkills(SkillDto skillDto, SkillDto skillDto2) throws SkillNotFoundException;

    List<Skill> addSkillsFromReader(List<Skill> skills);

    boolean deleteSkill(int skillDtoId) throws  SkillNotFoundException;

    void addApplicantSkillsFromReader(List<Applicant> applicants);

    void addJobOfferSkillsFromReader(List<JobOffer> jobOffers);

}
