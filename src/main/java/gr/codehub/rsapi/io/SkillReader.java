package gr.codehub.rsapi.io;

import gr.codehub.rsapi.model.Skill;

import java.util.List;

/**
 * Interface to create method readSkill and return it into List
 */

public interface SkillReader {
    public List<Skill> readSkills();
}
