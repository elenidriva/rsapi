package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.SkillRepository;
import gr.codehub.rsapi.services.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
