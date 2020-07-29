package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkills();

    Skill addSkill(Skill skill) throws SkillCreationException, SkillNotFoundException, SkillIsAlreadyExistException;

    List<Skill> addSkillsFromReader(List<Skill> skills);

    void addApplicantSkillsFromReader(List<ApplicantSkill> applicantSkills);

    //Merge skill --> package utility --> creating method with contains

    //if we want make setStatusSkill()
}
