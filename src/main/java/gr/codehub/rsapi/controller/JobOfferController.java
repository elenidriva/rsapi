package gr.codehub.rsapi.controller;

import gr.codehub.rsapi.dto.JobOfferDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.io.ExcelJobOfferReader;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.SkillService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@RestController
public class JobOfferController {

    private final JobOfferService jobOfferService;
    private final SkillService skillService;



    /**
     * Endpoint for adding a job offer
     *
     * @param jobOfferDto gets from the user a dto object
     * @return json contained the added job offer
     * @throws JobOfferCreationException the user tried to add a job offer without the required fields
     */
    @PostMapping("jobOffer")
    public JobOffer addJobOffer(@RequestBody JobOfferDto jobOfferDto) throws JobOfferCreationException {
        return jobOfferService.addJobOffer(jobOfferDto);
    }

    /**
     * Endpoint for finding a job offer using job offer`s id
     *
     * @param id the id of the job offer
     * @return json contained a job offer
     * @throws JobOfferNotFoundException  the user tried to find a job offer tha does not exist
     * @throws ApplicantNotFoundException the user tried to find an applicant tha does not exist
     */
    @GetMapping("jobOffer/{id}")
    public JobOffer getJobOffer(@PathVariable int id) throws JobOfferNotFoundException {
        return jobOfferService.getJobOffer(id);
    }

    /**
     * Endpoint for updating a job offer using job offers`s id
     *
     * @param jobOfferDto gets from the user a dto object
     * @param id          the id of the job offer
     * @return json contained the updated job offer
     * @throws JobOfferUpdateException   the user tried to update a job offer and the job offer is inactive
     * @throws JobOfferNotFoundException the user tried to find a job offer and the job offer does not exist
     */
    @PutMapping("jobOffer/{id}")
    public JobOffer updateJobOffer(@RequestBody JobOfferDto jobOfferDto, @PathVariable int id)
            throws JobOfferUpdateException, JobOfferNotFoundException {
        return jobOfferService.updateJobOffer(jobOfferDto, id);
    }

    @GetMapping("jobOffer")
    public List<JobOffer> getJobOffers() {
        return jobOfferService.getJobOffers();
    }

    /**
     * Endpoint for finding a job offer by criteria
     *
     * @param positionTitle the title of the job offer
     * @param region        the region of the job offer
     * @param jobOfferDate  the date job offer was made
     * @param skill         the skills that Job offer requires
     * @return applicants by job offer
     */
    @GetMapping("jobOffer/criteria")
    public List<JobOffer> findJobOffersByCriteria(
            @RequestParam(required = false) String positionTitle,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) LocalDate jobOfferDate,
            @RequestParam(required = false) Skill skill) {
        return jobOfferService.findJobOffersByCriteria(positionTitle, region, jobOfferDate, skill);

    }

    /**
     * Endpoint takes the id of a job offer and makes it inactive
     *
     * @param id the id of the job offer
     * @return returns true if it has become inactive false if it has not
     * @throws JobOfferNotFoundException the user tried to find a job offer tha does not exist
     * @throws JobOfferIsInactive        the user tried to find a job offer and the applicant is inactive
     */
    @PutMapping("jobOffer/{id}/inactive")
    public boolean setJobOfferInactive(@PathVariable int id) throws JobOfferNotFoundException, JobOfferIsInactive {
        return jobOfferService.setJobOfferInactive(id);
    }

    @GetMapping(value = "excelJobOffer")
    public List<JobOffer> addJobOfferFromExcel() throws FileNotFoundException {
        ExcelJobOfferReader excelJobOfferReader = new ExcelJobOfferReader();
        List<JobOffer> jobOfferList = excelJobOfferReader.readFromExcel();
        List<JobOffer> savedJobOffers = jobOfferService.addJobOffers(jobOfferList);
        skillService.addJobOfferSkillsFromReader(savedJobOffers);
        jobOfferService.addJobOfferSkills(savedJobOffers);
        System.out.println(savedJobOffers);
        return savedJobOffers;
    }

}

