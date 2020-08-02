package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.io.ExcelApplicantReader;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
public class ApplicantController {

    private ApplicantService applicantService;
    private SkillService skillService;


    /**
     * Provides an endpoint that allows the user to insert an applicant
     *
     * @param applicantDto The applicantDto object
     * @return Applicant  It returns the inserted applicant object
     * @throws ApplicantCreationException the user tried to add an applicant without the required fields
     */
    @PostMapping("applicant")
    public Applicant addApplicant(@RequestBody ApplicantDto applicantDto) throws BusinessException {

        return applicantService.addApplicant(applicantDto);
    }


    /**
     * Endpoint for updating an applicant using applicant`s id
     *
     * @param applicantDto gets from the user a dto object
     * @param id           the id of the applicant
     * @return json contained the updated applicant
     * @throws ApplicantNotFoundException the user tried to update an applicant tha does not exists
     * @throws ApplicantUpdateException   the user tried to update an applicant and the applicant is inactive
     */
    @PutMapping("applicant/{id}")
    public Applicant updateApplicant(@RequestBody ApplicantDto applicantDto, @PathVariable int id) throws BusinessException {
        return applicantService.updateApplicant(applicantDto, id);
    }

    /**
     * Endpoint for finding an applicant using applicant`s id
     *
     * @param id the id of the applicant
     * @return json contained an applicant
     * @throws ApplicantNotFoundException the user tried to find an applicant that does not exists
     */
    @GetMapping("applicant/{id}")
    public Applicant getApplicant(@PathVariable int id) throws BusinessException {
        return applicantService.getApplicant(id);
    }


    /**
     * Endpoint for finding an applicant
     *
     * @return a list of applicants
     */
    @GetMapping("applicant")
    public List<Applicant> getApplicants() {
        return applicantService.getApplicants();
    }

    /**
     * Endpoint for finding an applicant by criteria
     *
     * @param firstName       the first name of the applicant
     * @param lastName        the last name of the applicant
     * @param region          the region of the applicant
     * @param applicationDate the date applicant made the application
     * @param skill           the skills of the applicant
     * @return applicants by criteria
     */
    @GetMapping("applicant/criteria")
    public List<Applicant> findApplicantsByCriteria(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate applicationDate){
        return applicantService.findApplicantsByCriteria(firstName, lastName, region, applicationDate);
    }



    /**
     * Endpoint for deleting an applicant using his id
     *
     * @param id the id of the applicant
     * @return the applicant deleted
     * @throws ApplicantNotFoundException the user tried to find an applicant that does not exist
     */
    @DeleteMapping("applicant/{id}")
    public boolean deleteApplicant(@PathVariable int id) throws BusinessException {
        return applicantService.deleteApplicant(id);
    }

    /**
     * Endpoint takes the id of an applicant and makes him inactive
     *
     * @param id the id of the applicant
     * @return returns true if it has become inactive false if it has not been
     * @throws ApplicantNotFoundException the user tried to find an applicant tha does not exist
     * @throws ApplicantIsInactive        the user tried to find an applicant and the applicant is inactive
     */
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
