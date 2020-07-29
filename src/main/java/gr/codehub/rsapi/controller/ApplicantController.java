package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.ApplicantCreationException;
import gr.codehub.rsapi.exception.ApplicantIsInactive;
import gr.codehub.rsapi.exception.ApplicantNotFoundException;
import gr.codehub.rsapi.exception.ApplicantUpdateException;
import gr.codehub.rsapi.io.ExcelApplicantReader;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.ApplicantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ApplicantController {

    @Autowired
    private ApplicantService applicantService;


    @PostMapping("applicant")
    public Applicant addApplicant(@RequestBody ApplicantDto applicantDto) throws ApplicantCreationException {

        return applicantService.addApplicant(applicantDto);
    }

    @PutMapping("applicant/{id}")
    public Applicant updateApplicant(@RequestBody ApplicantDto applicantDto, @PathVariable int id) throws ApplicantNotFoundException, ApplicantUpdateException {
        return applicantService.updateApplicant(applicantDto, id);
    }

    @GetMapping("applicant/{id}")
    public Applicant getApplicant(@PathVariable int id) throws ApplicantNotFoundException {
        return applicantService.getApplicant(id);
    }

    @GetMapping("applicant") // end point, verb, parameters if they exist
    public List<Applicant> getApplicants() {
        return applicantService.getApplicants();
    }

    //    @PutMapping("applicant/{id}/status")
//    public Applicant updateApplicant(@RequestBody Applicant applicant, @PathVariable int id) throws ApplicantNotFoundException {
//        return applicantService.updateApplicant(applicant, id);
//    }
    @GetMapping("applicant/criteria")
    public List<Applicant> findApplicantsByCriteria(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) LocalDate applicationDate,
            @RequestParam(required = false) Skill skill)  {
        return applicantService.findApplicantsByCriteria(firstName, lastName, region, applicationDate, skill);
    }


    @DeleteMapping("applicant/{id}")
    public boolean deleteApplicant(@PathVariable int id) throws ApplicantNotFoundException {
        return applicantService.deleteApplicant(id);
    }

    @PutMapping("applicant/{id}/inactive")
    public boolean setApplicantInactive(@PathVariable int id) throws ApplicantNotFoundException, ApplicantIsInactive {
        return applicantService.setApplicantInactive(id);
    }


    @GetMapping(value = "excelApplicants")
    public List<Applicant> addApplicantsFromReaderNew() throws FileNotFoundException {
        ExcelApplicantReader excelApplicantReader = new ExcelApplicantReader();
        List<Applicant> applicantList = excelApplicantReader.readFromExcel();
        List<Applicant> savedApplicants = applicantService.addApplicants(applicantList);
        skillService.addApplicantSkillsFromReader(savedApplicants);
        applicantService.addApplicantSkills(savedApplicants);
        return savedApplicants;
    }

}

