package gr.codehub.rsapi.controller;


import gr.codehub.rsapi.exception.JobOfferCreationException;
import gr.codehub.rsapi.io.ExcelApplicantReader;
import gr.codehub.rsapi.io.ExcelJobOfferReader;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.service.JobOfferService;
import gr.codehub.rsapi.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController

public class JobOfferController {

    /* gia na sundeso to restcontroller (ayta pou fainontai sto xristi--endpoints)
    me ta services   */
    @Autowired
    /* dimiourgo ena pedio ston controller gia na borei na to xrisimopoisei */
    /* pairno ena joboffer kai to vazo sti vasi */
    private JobOfferService jobOfferService;

    @Autowired
    private SkillService skillService;

    // prosthetei ena joboffer
    @PostMapping(value = "jobOffer") //to string ti patame gia na ginei i entoli
    public JobOffer addJobOffer(@RequestBody JobOffer jobOffer)
            throws JobOfferCreationException {
        return jobOfferService.addJobOffer(jobOffer);
    }

    /* uloipoisi tou na paro to joboffer vasi enos id */
    @GetMapping("jobOffer/{id}")
    public JobOffer getJobOffer(@PathVariable int id)
            throws JobOfferNotFoundException {
        return jobOfferService.getJobOffer(id);
    }

    /* pairnei o recruiter to jooffer vasi id kai tou allazei,
    oi allages pou borei na kanei einai aytes pou anaferontai sto joboffer(columns) */
    @PutMapping(value = "jobOffer/{id}")
    //to pathvariable simainei oti parino apo to xristi kati
    public JobOffer updateJobOffer(@RequestBody JobOffer jobOffer, @PathVariable int id)
            throws Exception {
        return jobOfferService.updateJobOffer(jobOffer, id);
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
