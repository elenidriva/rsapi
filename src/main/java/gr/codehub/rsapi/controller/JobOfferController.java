package gr.codehub.rsapi.controller;


import gr.codehub.rsapi.dto.JobOfferDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferIsInactive;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.JobOfferUpdateException;
import gr.codehub.rsapi.exception.RCMRuntimeException;
import gr.codehub.rsapi.io.ExcelJobOfferReader;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.SkillService;
import lombok.RequiredArgsConstructor;
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
 * JobOfferController: Responsible for exposing all the available operations regarding a {@link JobOffer}
 */
@RequiredArgsConstructor
@RestController("jobOffer")
public class JobOfferController {

    private final JobOfferService jobOfferService;
    private final SkillService skillService;

    /**
     * Adds a new job offer
     *
     * @param jobOfferDto - the dto object of a job offer
     * @return the newly added job offer's info
     * @throws JobOfferCreationException the user tried to add a job offer without the required fields
     */
    @PostMapping
    public JobOffer addJobOffer(@RequestBody JobOfferDto jobOfferDto) throws RCMRuntimeException, JobOfferCreationException {
        return jobOfferService.addJobOffer(jobOfferDto);
    }

    /**
     * Retrieves a job offer given an id
     *
     * @pathVariable id - the id of the job offer
     * @return json contained a job offer
     * @throws JobOfferNotFoundException  the user tried to find a job offer tha does not exist
     * @throws RCMRuntimeException the user tried to find an applicant tha does not exist
     */
    @GetMapping("/{id}")
    public JobOffer getJobOffer(@PathVariable int id) {
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
    @PutMapping("/{id}")
    public JobOffer updateJobOffer(@RequestBody JobOfferDto jobOfferDto,
                                   @PathVariable int id)
            throws RCMRuntimeException, JobOfferUpdateException {
        return jobOfferService.updateJobOffer(jobOfferDto, id);
    }

    @GetMapping
    public List<JobOffer> getJobOffers() {
        return jobOfferService.getJobOffers();
    }

    /**
     * Endpoint for finding a job offer by criteria
     *
     * @param positionTitle the title of the job offer
     * @param region        the region of the job offer
     * @param jobOfferDate  the date job offer was made
     * @return applicants by job offer
     */
    @GetMapping("/criteria")
    public List<JobOffer> findJobOffersByCriteria(
            @RequestParam(required = false) String positionTitle,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) LocalDate jobOfferDate) {
        return jobOfferService.findJobOffersByCriteria(positionTitle, region, jobOfferDate);

    }

    /**
     * Endpoint takes the id of a job offer and makes it inactive
     *
     * @param id the id of the job offer
     * @return returns true if it has become inactive false if it has not
     * @throws JobOfferNotFoundException the user tried to find a job offer tha does not exist
     * @throws JobOfferIsInactive        the user tried to find a job offer and the applicant is inactive
     */
    @PutMapping("/{id}/inactive")
    public boolean setJobOfferInactive(@PathVariable int id) throws RCMRuntimeException, JobOfferIsInactive {
        return jobOfferService.setJobOfferInactive(id);
    }

    /**
     * Endpoint to read JobOffer data from Excel file
     *
     * @return returns list of JobOffers and save in DB
     * @throws FileNotFoundException if file did not found
     */
    @GetMapping(value = "/loadJobOffer")
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
