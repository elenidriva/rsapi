package gr.codehub.rsapi.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface JobOffersApplicantsDto {

    int getJobOfferSkill();

    int getApplicantSkill();

}
