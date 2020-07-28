package gr.codehub.rsapi.services;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;

import java.util.List;

public interface SkillService {
    List<Skill> getSkill();
    Skill addSkill(int id) throws SkillCreationException, SkillNotFoundException, SkillIsAlreadyExistException;

    //if we want make setStatusSkill()
}
