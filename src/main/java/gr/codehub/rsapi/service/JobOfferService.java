package gr.codehub.rsapi.service;

import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.JobOfferNotFoundException;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.Skill;

import java.util.Date;
import java.util.List;

public interface JobOfferService {

    /**
     * search by date, skill, name, region
     **/

    List<JobOffer> getJobOffers(); /*otan thelo id thelo ena joboffer allios thelo polla */

    JobOffer getJobOffer(int jobOfferId);

    JobOffer updateJobOffer(JobOffer jobOffer, int jobOfferId) throws JobOfferNotFoundException;

    JobOffer addJobOffer(JobOffer jobOffer);

    List<JobOffer> findJobOffersByCriteria(String positionTitle, Region region, Date date, Skill skill);


}


