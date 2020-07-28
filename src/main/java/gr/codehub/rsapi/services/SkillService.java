package gr.codehub.rsapi.services;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkills();

    Skill addSkill(Skill skill) throws SkillCreationException, SkillNotFoundException, SkillIsAlreadyExistException;

    //Merge skill --> package utility --> creating method with contains

    //if we want make setStatusSkill()
}
