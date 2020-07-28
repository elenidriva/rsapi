package gr.codehub.rsapi.service;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("ImplDB")
public class SkillServiceImpl implements gr.codehub.rsapi.services.SkillService {
    @Autowired
    private SkillRepository skillRepository;

    @Override
    public List<Skill> getSkill() {
        return skillRepository.findAll();
    }

    /**
     * addSkill
     * check if skill exist
     * check if skill is null
     */

    @Override
    public Skill addSkill(int skillId) throws SkillCreationException, SkillNotFoundException, SkillIsAlreadyExistException {
        Skill skill = new Skill();
        Skill skillFromDb = skillRepository.findById(skillId).orElseThrow(() -> new SkillNotFoundException("Skill not found"));
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
