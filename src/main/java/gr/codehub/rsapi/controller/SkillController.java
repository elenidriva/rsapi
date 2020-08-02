package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.SkillDto;
import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.io.ExcelSkillReader;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
public class SkillController {

    private final SkillService skillService;

    @Autowired
    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping(value = "skill")
    public List<Skill> getSkills() {
        return skillService.getSkills();
    }

    @PostMapping(value = "skill")
    public Skill addSkill(@RequestBody SkillDto skillDto) throws SkillCreationException, SkillIsAlreadyExistException, SkillNotFoundException {
        return skillService.addSkill(skillDto);
    }

    @GetMapping(value = "insertSkillsFromExcel")
    public List<Skill> addSkillsFromReader() throws FileNotFoundException {
        ExcelSkillReader excelSkillReader = new ExcelSkillReader();
        List<Skill> skills = excelSkillReader.readFromExcel();
        return skillService.addSkillsFromReader(skills);
    }


    @PostMapping(value = "skillSplitt")
    public List<Skill> splitSkill(@RequestBody SkillDto skillDto) throws SkillCreationException, SkillIsAlreadyExistException, SkillNotFoundException {
        return skillService.splitSkill(skillDto);
    }

    @PostMapping(value = "skillsMerge")
    public Skill mergeSkills(@RequestParam String skillTitle1, @RequestParam String skillTitle2) throws SkillCreationException, SkillIsAlreadyExistException, SkillNotFoundException {
        return skillService.mergeSkills(new SkillDto(skillTitle1), new SkillDto(skillTitle2));
    }
}
