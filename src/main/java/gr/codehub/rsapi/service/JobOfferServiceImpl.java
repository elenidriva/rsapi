package gr.codehub.rsapi.service;

import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.JobOfferRepository;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * ayta pou borei na xrisimopoiei o pelatis
 **/
@Service
public class JobOfferServiceImpl implements JobOfferService {

    /**
     * to autowired sundeei to repository sto service
     **/
    @Autowired
    private JobOfferRepository jobOfferRepository;

    @Override
    public JobOffer addJobOffer(JobOffer jobOffer) {

        return jobOfferRepository.save(jobOffer);
    }

    /**
     * gurnao to apotelesma se optional kai apo to optional pairno to joboffer
     * kai kano to update pou thelo kai meta to sozo sto database
     **/

    @Override
    public JobOffer updateJobOffer(JobOffer jobOffer, int jobOfferId) throws JobOfferNotFoundException {
        JobOffer jobOfferInDb = jobOfferRepository.findById(jobOfferId)
                .orElseThrow(
                        () -> new JobOfferNotFoundException("Not such job offer"));
        jobOfferInDb.setJobOfferDate(jobOffer.getJobOfferDate());
        jobOfferInDb.setRegion(jobOffer.getRegion());
        jobOfferInDb.setExperienceLevel(jobOffer.getExperienceLevel());
        jobOfferInDb.setDegreeLevel(jobOffer.getDegreeLevel());
        jobOfferInDb.setPositionTitle(jobOffer.getPositionTitle());
        jobOfferInDb.setCompany(jobOffer.getCompany());
        jobOfferInDb.setDescription(jobOffer.getDescription());
        // allazei to status kai ginetai update to joboffer opos kai pano
        jobOfferInDb.setStatus(jobOffer.getStatus());
        jobOfferInDb.setJobOfferSkillList(jobOffer.getJobOfferSkillList());

        return jobOfferRepository.save(jobOfferInDb);
//        if (jobOfferInDb.getStatus() == Status.INACTIVE) {
//            jobOfferInDb.setStatus(Status.ACTIVE);
//        } else {
//            jobOfferInDb.setStatus(Status.INACTIVE);
//        }
//        return jobOfferRepository.save(jobOfferInDb);
    }

    /** allazo to status, thelei exception gia to active **/
    /**
     * to statusIndex einai to id enos joboffer to opoio vazei o xristis
     * elegxei an uparxei to
     **/


    @Override
    public List<JobOffer> getJobOffers() {

        return jobOfferRepository.findAll();
    }

    /* epistrfei lista me ta job offers vasi ton kritirion pou dinei */
    @Override
    public List<JobOffer> findJobOffersByCriteria
    (String positionTitle, Region region, Date date, Skill skill) {
        return jobOfferRepository.findJobOffersByCriteria(positionTitle, region, date, skill);
    }

    // epistrefei to joboffer me to sugkekrimeno id
    @Override
    public JobOffer getJobOffer(int jobOfferId) {
        Optional<JobOffer> jobOfferInDbOptional = jobOfferRepository.findById(jobOfferId);
        JobOffer jobOfferInDb = jobOfferInDbOptional.get();

        return jobOfferInDb;
    }

}
