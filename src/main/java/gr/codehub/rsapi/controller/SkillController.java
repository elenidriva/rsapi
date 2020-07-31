package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.io.ExcelSkillReader;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class SkillController {

    private SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping(value = "skill")
    public List<Skill> getSkills() {
        return skillService.getSkills();
    }

    @PostMapping(value = "skill")
    public Skill addSkill(@RequestBody Skill skill) throws SkillCreationException, SkillIsAlreadyExistException, SkillNotFoundException {
        return skillService.addSkill(skill);
    }

    @GetMapping(value = "excel")
    public List<Skill> addSkillsFromReader() throws FileNotFoundException {
        ExcelSkillReader excelSkillReader = new ExcelSkillReader();
        List<Skill> skills = excelSkillReader.readFromExcel();
        return skillService.addSkillsFromReader(skills);
    }
}
