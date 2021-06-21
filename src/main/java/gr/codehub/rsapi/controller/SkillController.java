package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.SkillDto;
import gr.codehub.rsapi.exception.RCMRuntimeException;
import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.io.ExcelSkillReader;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;

@RequiredArgsConstructor
@RestController("skill")
public class SkillController {

    private final SkillService skillService;

    /**
     * Endpoint for finding Skill list
     *
     * @return a list of skills
     */
    @GetMapping
    public List<Skill> getSkills() {
        return skillService.getSkills();
    }

    /**
     * Endpoint to add new skill
     *
     * @param skillDto
     * @return added skill and save it in DB
     */
    @PostMapping
    public Skill addSkill(@RequestBody SkillDto skillDto) throws RCMRuntimeException, SkillIsAlreadyExistException, SkillCreationException {
        return skillService.addSkill(skillDto);
    }


    /**
     * Endpoint to split skill from given skill list
     *
     * @param skillDto
     * @return added the merged skill and save in DB
     */
    @PostMapping(value = "/split")
    public List<Skill> splitSkill(@RequestBody SkillDto skillDto) throws RCMRuntimeException, SkillCreationException {
        return skillService.splitSkill(skillDto);
    }

    /**
     * Endpoint to merge skill from given skill list
     *
     * @param skillTitle1
     * @param skillTitle2
     * @return added the merged skill and save in DB
     */
    @PostMapping(value = "/merge")
    public Skill mergeSkills(@RequestParam String skillTitle1,
                             @RequestParam String skillTitle2) throws RCMRuntimeException {
        return skillService.mergeSkills(new SkillDto(skillTitle1), new SkillDto(skillTitle2));
    }

    /**
     * Endpoint to read Skill data from Excel file
     *
     * @return returns list of Skills and save in DB
     * @throws FileNotFoundException if file did not found
     */
    @GetMapping(value = "/insertFromExcel")
    public List<Skill> addSkillsFromReader() throws FileNotFoundException {
        ExcelSkillReader excelSkillReader = new ExcelSkillReader();
        List<Skill> skills = excelSkillReader.readFromExcel();
        return skillService.addSkillsFromReader(skills);
    }

}
