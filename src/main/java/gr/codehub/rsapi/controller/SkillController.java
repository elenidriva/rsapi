package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.exception.SkillCreationException;
import gr.codehub.rsapi.exception.SkillIsAlreadyExistException;
import gr.codehub.rsapi.exception.SkillNotFoundException;
import gr.codehub.rsapi.model.Skill;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import gr.codehub.rsapi.service.SkillService;

import java.util.List;

@RestController
public class SkillController {

private  SkillService skillService;

//Constructor
public SkillController(@Qualifier("ImplDB") SkillService skillService){
    this.skillService = skillService;
}

@GetMapping(value = "skill")
public List<Skill> getSkills(){
    return skillService.getSkills();
}

@PostMapping(value = "skill")
public Skill addSkill(@RequestBody Skill skill) throws SkillCreationException, SkillIsAlreadyExistException, SkillNotFoundException{
    return skillService.addSkill(skill);
}


}
