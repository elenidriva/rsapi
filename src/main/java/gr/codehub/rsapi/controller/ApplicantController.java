package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.RCMRuntimeException;
import gr.codehub.rsapi.io.ExcelApplicantReader;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.service.ApplicantService;
import gr.codehub.rsapi.service.SkillService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

/**
 * ApplicantController: Responsible for exposing all the available operations regarding an {@link Applicant}
 */
@RestController("applicant")
@RequiredArgsConstructor
public class ApplicantController {

    private final ApplicantService applicantService;
    private final SkillService skillService;

    /**
     * Adds a new Applicant
     *
     * @param applicantDto - dto object with applicant-related fields.
     * @return Applicant  - returns the inserted applicant object
     * @throws RCMRuntimeException the user tried to add an applicant without the required fields
     */
    @PostMapping
    public Applicant postApplicant(@RequestBody ApplicantDto applicantDto) throws RCMRuntimeException {
        return applicantService.addApplicant(applicantDto);
    }


    /**
     * Updates the applicant whose id is given
     *
     * @param applicantDto - dto object with applicant-related fields.
     * @return json contained the updated applicant
     * @throws RCMRuntimeException the user tried to update an applicant that does not exist
     * @pathVariable id - the id of the applicant we want to update
     */
    @PutMapping("/{id}")
    public Applicant updateApplicant(@RequestBody ApplicantDto applicantDto,
                                     @PathVariable int id) throws RCMRuntimeException {
        return applicantService.updateApplicant(applicantDto, id);
    }

    /**
     * Retrieves an applicant given an id
     *
     * @return json contained an applicant
     * @throws RCMRuntimeException the user tried to find an applicant that does not exist
     * @pathVariable id the id of the applicant
     */
    @GetMapping("/{id}")
    public Applicant getApplicant(@PathVariable int id) throws RCMRuntimeException {
        return applicantService.getApplicant(id);
    }


    /**
     * Retrieves the list of Applicants
     *
     * @return a list of applicants
     */
    @GetMapping
    public List<Applicant> getApplicants() {
        return applicantService.getApplicants();
    }

    /**
     * Finds all applicants matching the criteria given
     *
     * @param firstName       - the first name of the applicant
     * @param lastName        - the last name of the applicant
     * @param region          - the region of the applicant
     * @param applicationDate - the date applicant made the application
     * @return a list of applicants matching the given criteria or else an empty list
     */
    @GetMapping("/criteria")
    public List<Applicant> findApplicantsByCriteria(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate applicationDate) {
        return applicantService.findApplicantsByCriteria(firstName, lastName, region, applicationDate);
    }


    /**
     * Sets an applicant to inactive given an id
     *
     * @param id the id of the applicant
     * @return returns true in case the operation succeeded, or else returns false
     * @throws RCMRuntimeException the user tried to find an applicant tha does not exist
     */
    @PutMapping("/{id}/inactive")
    public boolean setApplicantInactive(@PathVariable int id) throws RCMRuntimeException {
        return applicantService.setApplicantInactive(id);
    }

    /**
     * Reads a list of applicants provided from an excel and saves them into the DB
     *
     * @return returns a list of Applicants
     * @throws FileNotFoundException if file was not found
     */
    @GetMapping(value = "/loadApplicants")
    public List<Applicant> addApplicantsFromReaderNew() throws FileNotFoundException {
        ExcelApplicantReader excelApplicantReader = new ExcelApplicantReader();
        List<Applicant> applicantList = excelApplicantReader.readFromExcel();
        List<Applicant> savedApplicants = applicantService.addApplicants(applicantList);
        skillService.addApplicantSkillsFromReader(savedApplicants);
        applicantService.addApplicantSkills(savedApplicants);
        return savedApplicants;
    }

}