package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.SkillDto;
import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;

import java.util.List;

/**
 * The interface Skill service.
 */
public interface SkillService {
    /**
     * Gets skills.
     *
     * @return the skills
     */
    List<Skill> getSkills();

    /**
     * Add skill skill.
     *
     * @param skill the skill
     * @return the skill
     * @throws SkillCreationException       the skill creation exception
     * @throws SkillNotFoundException       the skill not found exception
     * @throws SkillIsAlreadyExistException the skill is already exist exception
     */
    Skill addSkill(SkillDto skillDto) throws SkillCreationException, SkillNotFoundException, SkillIsAlreadyExistException;


    List<Skill> splitSkill(SkillDto skillDto) throws SkillNotFoundException, SkillCreationException, SkillIsAlreadyExistException;

    Skill mergeSkills(SkillDto skillDto, SkillDto skillDto2) throws SkillNotFoundException;

    /**
     * Add skills from reader list.
     *
     * @param skills the skills
     * @return the list
     */
    List<Skill> addSkillsFromReader(List<Skill> skills);

    boolean deleteSkill(int skillDtoId) throws  SkillNotFoundException;

    /**
     * Add applicant skills from reader.
     *
     * @param applicants the applicant skills
     */
    void addApplicantSkillsFromReader(List<Applicant> applicants);

    /**
     * Add job offer skills from reader.
     *
     * @param savedJobOffers the saved job offers
     */
    void addJobOfferSkillsFromReader(List<JobOffer> jobOffers);

}
