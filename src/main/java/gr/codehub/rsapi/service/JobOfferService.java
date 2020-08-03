package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.JobOfferDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.model.JobOffer;

import java.time.LocalDate;
import java.util.List;

/**
 * The interface Job offer service.
 */
public interface JobOfferService {

    List<JobOffer> getJobOffers();

    /**
     * Gets job offer.
     *
     * @param jobOfferId the job offer id
     * @return the job offer
     * @throws JobOfferNotFoundException the job offer not found exception
     */
    JobOffer getJobOffer(int jobOfferId);

    /**
     * Update job offer job offer.
     *
     * @param jobOfferDto the job offer dto
     * @param jobOfferId  the job offer index
     * @return the job offer
     * @throws JobOfferNotFoundException the job offer not found exception
     * @throws JobOfferUpdateException   the job offer update exception
     */
    JobOffer updateJobOffer(JobOfferDto jobOfferDto, int jobOfferId) throws BusinessException, JobOfferUpdateException;


    /**
     * Add job offer job offer.
     *
     * @param jobOfferDto the job offer
     * @return the job offer
     * @throws JobOfferCreationException the job offer creation exception
     */
    JobOffer addJobOffer(JobOfferDto jobOfferDto) throws BusinessException, JobOfferCreationException;

    /**
     * Find job offers by criteria list.
     *
     * @param positionTitle the position title
     * @param region        the region
     * @param date          the date
     * @return the list
     */
    List<JobOffer> findJobOffersByCriteria(String positionTitle, Region region, LocalDate date);

    /**
     * Sets job offer inactive.
     *
     * @param jobOfferIndex the job offer index
     * @return the job offer inactive
     * @throws JobOfferNotFoundException the job offer not found exception
     * @throws JobOfferIsInactive        the job offer is inactive
     */
    boolean setJobOfferInactive(int jobOfferIndex) throws BusinessException, JobOfferIsInactive;


    /**
     * Add job offer skills.
     *
     * @param jobOffers the job offers
     */
    void addJobOfferSkills(List<JobOffer> jobOffers);

    /**
     * Add job offers list.
     *
     * @param jobOffers the job offers
     * @return the list
     */
    List<JobOffer> addJobOffers(List<JobOffer> jobOffers);

}

