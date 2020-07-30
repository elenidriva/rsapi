package gr.codehub.rsapi.controller;


import gr.codehub.rsapi.dto.JobOfferDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.exception.JobOfferIsInactive;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.exception.JobOfferUpdateException;
import gr.codehub.rsapi.io.ExcelJobOfferReader;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

@RestController
public class JobOfferController {


    /* gia na sundeso to restcontroller (ayta pou fainontai sto xristi--endpoints)
    me ta services   */

    /* dimiourgo ena pedio ston controller gia na borei na to xrisimopoisei */
    /* pairno ena joboffer kai to vazo sti vasi */
    private JobOfferService jobOfferService;
    private SkillService skillService;

    @Autowired
    public JobOfferController(JobOfferService jobOfferService,SkillService skillService) {
        this.jobOfferService = jobOfferService;
        this.skillService = skillService;
    }

    // prosthetei ena joboffer
    @PostMapping("jobOffer") //to string ti patame gia na ginei i entoli
    public JobOffer addJobOffer(@RequestBody JobOfferDto jobOfferDto) throws JobOfferCreationException {
        return jobOfferService.addJobOffer(jobOfferDto);
    }

    /* uloipoisi tou na paro to joboffer vasi enos id */
    @GetMapping("jobOffer/{id}")
    public JobOffer getJobOffer(@PathVariable int id) throws JobOfferNotFoundException {
        return jobOfferService.getJobOffer(id);
    }

    /* pairnei o recruiter to jooffer vasi id kai tou allazei,
    oi allages pou borei na kanei einai aytes pou anaferontai sto joboffer(columns) */
    @PutMapping("jobOffer/{id}")
    //to pathvariable simainei oti parino apo to xristi kati
    public JobOffer updateJobOffer(@RequestBody JobOfferDto jobOfferDto, @PathVariable int id)
            throws JobOfferUpdateException, JobOfferNotFoundException {
        return jobOfferService.updateJobOffer(jobOfferDto, id);
    }

    @GetMapping("jobOffer") // end point, verb, parameters if they exist
    public List<JobOffer> getJobOffers() {
        return jobOfferService.getJobOffers();
    }

    @GetMapping("jobOffer/criteria")
    public List<JobOffer> findJobOffersByCriteria(
            @RequestParam(required = false) String positionTitle,
            @RequestParam(required = false) Region region,
            @RequestParam(required = false) LocalDate jobOfferDate,
            @RequestParam(required = false) Skill skill) {
        return jobOfferService.findJobOffersByCriteria(positionTitle, region, jobOfferDate, skill);

    }

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

