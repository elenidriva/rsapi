package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.SkillDto;
import gr.codehub.rsapi.exception.BusinessException;
import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.io.ExcelSkillReader;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@AllArgsConstructor
@RestController
public class SkillController {

    private final SkillService skillService;


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
    public Skill mergeSkills(@RequestParam String skillTitle1, @RequestParam String skillTitle2) throws BusinessException {
        return skillService.mergeSkills(new SkillDto(skillTitle1), new SkillDto(skillTitle2));
    }
}
