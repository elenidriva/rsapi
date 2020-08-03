package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.SkillDto;
import gr.codehub.rsapi.exception.BusinessException;
import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.io.ExcelSkillReader;
import gr.codehub.rsapi.logging.SLF4JExample;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.SkillService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@AllArgsConstructor
@RestController
public class SkillController {

    private final SkillService skillService;
    private static final Logger logger = LoggerFactory.getLogger(SLF4JExample.class);

    /**
     * Endpoint for finding Skill list
     *
     * @return a list of skills
     */
    @GetMapping(value = "skill")
    public List<Skill> getSkills() {
        logger.info("Getting skills");
        return skillService.getSkills();
    }

    /**
     * Endpoint to add new skill
     *
     * @param skillDto
     * @return added skill and save it in DB
     */
    @PostMapping(value = "skill")
    public Skill addSkill(@RequestBody SkillDto skillDto) throws BusinessException, SkillIsAlreadyExistException, SkillCreationException {
        logger.info("Adding skills");
        return skillService.addSkill(skillDto);
    }


    /**
     * Endpoint to split skill from given skill list
     *
     * @param skillDto
     * @return added the merged skill and save in DB
     */
    @PostMapping(value = "skillSplit")
    public List<Skill> splitSkill(@RequestBody SkillDto skillDto) throws BusinessException, SkillCreationException {
        logger.info("Splitting skills");
        return skillService.splitSkill(skillDto);
    }

    /**
     * Endpoint to merge skill from given skill list
     *
     * @param skillTitle1
     * @param  skillTitle2
     * @return added the merged skill and save in DB
     */
    @PostMapping(value = "skillsMerge")
    public Skill mergeSkills(@RequestParam String skillTitle1, @RequestParam String skillTitle2) throws BusinessException {
        logger.info("Merging skills");
        return skillService.mergeSkills(new SkillDto(skillTitle1), new SkillDto(skillTitle2));
    }

    /**
     * Endpoint to read Skill data from Excel file
     *
     * @return returns list of Skills and save in DB
     * @throws FileNotFoundException if file did not found
     */
    @GetMapping(value = "insertSkillsFromExcel")
    public List<Skill> addSkillsFromReader() throws FileNotFoundException {
        ExcelSkillReader excelSkillReader = new ExcelSkillReader();
        List<Skill> skills = excelSkillReader.readFromExcel();
        logger.info("Import skills");
        return skillService.addSkillsFromReader(skills);
    }

}
