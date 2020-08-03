package gr.codehub.rsapi.service;

import gr.codehub.rsapi.dto.ApplicantDto;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.exception.*;
import gr.codehub.rsapi.logging.SLF4JExample;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.repository.ApplicantRepository;
import gr.codehub.rsapi.repository.ApplicantSkillRepository;
import gr.codehub.rsapi.repository.SkillRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Service
public class ApplicantServiceImpl implements ApplicantService {

    private final ApplicantRepository applicantRepository;
    private final ApplicantSkillRepository applicantSkillRepository;
    private final SkillRepository skillRepository;
    private final SkillService skillService;
    private static final Logger logger = LoggerFactory.getLogger(SLF4JExample.class);


    /**
     * This method takes the data from dto and passes the data needed to create the applicant and saves it in the base
     *
     * @param applicantDto gets from the user a dto object
     * @return applicant with id and saves it to the data base
     * @throws ApplicantCreationException the user tried to create an applicant without the required fields
     */
    @Override
    public Applicant addApplicant(ApplicantDto applicantDto) throws BusinessException {
        Applicant applicant = new Applicant();
        if (applicantDto == null) throw new ApplicantCreationException("You did not add any of the required fields");

        applicant.setFirstName(applicantDto.getFirstName());
        applicant.setLastName(applicantDto.getLastName());
        applicant.setAddress(applicantDto.getAddress());
        applicant.setRegion(applicantDto.getRegion());
        applicant.setExperienceLevel(applicantDto.getExperienceLevel());
        applicant.setDegreeLevel(applicantDto.getDegreeLevel());
        applicant.setApplicantSkillList(applicantDto.getApplicantSkillList());
        applicant.setApplicationDate(LocalDate.now());
        applicant.setStatus(Status.ACTIVE);

        applicantRepository.save(applicant);
        System.out.println(applicant.getId());

        Applicant applFromRep = applicantRepository.findById(applicant.getId()).orElseThrow(() -> new BusinessException("Cannot find applicant with id:" + applicant.getId()));
        ApplicantSkill appSkill = new ApplicantSkill();
        appSkill.setApplicant(applFromRep);
        applicantDto.getApplicantSkillList().forEach(o -> {
            Skill skillFromDb = skillRepository.findBySkillTitle(o.getSkill().getTitle());
            if (skillFromDb != null) {
                appSkill.setSkill(skillFromDb);
                applicantSkillRepository.save(appSkill);
            } else {
                try {
                    applicantRepository.deleteById(applicant.getId());
                    throw new BusinessException("Please insert a skill that exists in the DB. Your applicant profile was not created.");
                } catch (BusinessException e) {
                    logger.info("Successfully add Applicant");
                }
            }
        });


        return applicant;
    }

    public List<ApplicantSkill> addAppSkillToApplicant(List<ApplicantSkill> applicantSkillList, Applicant applicant) {
        for (ApplicantSkill applicantSkill : applicantSkillList) {
            applicantSkill.setApplicant(applicant);
        }
        logger.info("Successfully add Skills to applicant");
        return applicantSkillRepository.saveAll(applicantSkillList);
    }

    /**
     * This method takes an id from the user and searches the repository if it exists
     * If the status is inactive we tell him it's Inactive and he does it active
     *
     * @param applicantIndex the applicant id given by the user
     * @return to successfully change the status to inactive returns true
     * @throws ApplicantNotFoundException the user tried to find an applicant with id that does not exist
     * @throws ApplicantIsInactive        the user tried to do inactive an applicant that is already inactive
     */
    @Override
    public boolean setApplicantInactive(int applicantIndex) throws BusinessException {
        Applicant applicantInDb = applicantRepository.findById(applicantIndex).orElseThrow(() -> new BusinessException("Cannot find applicant with id:" + applicantIndex));
        Applicant applicant;
        if (applicantInDb.getStatus().equals(Status.INACTIVE))
            throw new ApplicantIsInactive("Applicant with id:" + applicantIndex + " is already inactive.");
        else applicantInDb.setStatus(Status.ACTIVE);
        applicant = applicantInDb;
        applicant.setStatus(Status.INACTIVE);
        applicantRepository.save(applicant);
        logger.info("Successfully setting applicant inactive");
        return true;
    }


    /**
     * This method searches the base if there is an applicant with this id and if there is it returns it
     *
     * @param applicantIndex the id of applicant given by the user
     * @return the appliacnt based on the id given by the user
     * @throws ApplicantNotFoundException The user tried to find an applicant that does not exist in the data base
     */
    @Override
    public Applicant getApplicant(int applicantIndex) throws BusinessException {
        logger.info("Successfully getting applicant");
        return applicantRepository.findById(applicantIndex).orElseThrow(() -> new BusinessException("There is no such Applicant in the DB."));
    }

    /**
     * Takes the data from dto and passes the data needed to make update the applicant
     *
     * @param applicantDto   gets from the user a dto object
     * @param applicantIndex takes the ID of an applicant and finds if exists
     * @return the applicant updated and saves it to the base
     * @throws ApplicantNotFoundException The user tried to update an applicant that does not exist
     * @throws ApplicantUpdateException   The user tried to update the applicant but the applicant is inactive
     */
    @Override
    public Applicant updateApplicant(ApplicantDto applicantDto, int applicantIndex) throws BusinessException {

        Applicant applicantInDb = applicantRepository.findById(applicantIndex).orElseThrow(() -> new BusinessException("Cannot find applicant with id:" + applicantIndex));
        if (applicantInDb.getStatus() == Status.INACTIVE)
            throw new ApplicantUpdateException("Failed to update Applicant, because the Applicant is inactive");
        applicantInDb.setFirstName(applicantDto.getFirstName());
        applicantInDb.setLastName(applicantDto.getLastName());
        applicantInDb.setStatus(Status.ACTIVE);
        applicantInDb.setAddress(applicantDto.getAddress());
        applicantInDb.setDegreeLevel(applicantDto.getDegreeLevel());
        applicantInDb.setExperienceLevel(applicantDto.getExperienceLevel());
        applicantInDb.setApplicantSkillList(applicantDto.getApplicantSkillList());
        applicantInDb.setRegion(applicantDto.getRegion());

        applicantRepository.save(applicantInDb);
        logger.info("Successfully updating applicant");

        return applicantInDb;
    }

    @Override
    public List<Applicant> getApplicants() {
        logger.info("Successfully getting list of applicants");
        return applicantRepository.findAll();
    }

    /**
     * This method finds the applicants from the applicant
     * repository based on the criteria given by the user
     *
     * @param firstName the first name of the applicant
     * @param lastName  the last name of the applicant
     * @param region    the region of the applicant
     * @param date      the date that appllicant made the application
     * @return a list of applicants based on the criteria that become from the user
     */
    @Override
    public List<Applicant> findApplicantsByCriteria(String firstName, String lastName, Region region, LocalDate date) {

        logger.info("Successfully find applicant by criteria");
        return applicantRepository.findApplicantByCriteria(firstName, lastName, region, date);

    }


    /**
     * This method searches the base if there is an applicant with this id and if there is it delete it
     *
     * @param applicantIndex takes the ID of an applicant and finds if exists
     * @return true if applicant was actually deleted
     * @throws ApplicantNotFoundException the user tried to delete an applicant with id that does not exist in data base
     */
    @Override
    public boolean deleteApplicant(int applicantIndex) throws BusinessException {
        applicantRepository.findById(applicantIndex).orElseThrow(() -> new BusinessException("Cannot find applicant with id:" + applicantIndex));
        applicantRepository.deleteById(applicantIndex);
        logger.info("Successfully delete applicant");
        return true;
    }

    /**
     * @param applicant
     * @param skill
     * @return
     */
    public boolean insertApplicantSkill(Applicant applicant, Skill skill) {
        ApplicantSkill applicantSkill = new ApplicantSkill();
        applicantSkill.setApplicant(applicant);
        Skill skillInDb = skillRepository.findBySkillTitle(skill.getTitle());
        if (skillInDb == null) {
            skillRepository.save(new Skill(skillInDb.getTitle()));
            return true;
        }
        logger.info("Successfully insert applicant skill");
        return false;
    }

    /**
     * ayti i methodos vazei se kathe applicant ola ta applicant skills
     * kai sozei stin vasi
     *
     * @param applicants oso uparxoyn applicants sunexise na sozeis ola ta applicant skills tous
     */
    @Override
    public void addApplicantSkills(List<Applicant> applicants) {
        for (Applicant applicant : applicants) {
            applicantSkillRepository.saveAll(applicant.getApplicantSkillList());
            logger.info("Successfully adding applicant skills");
        }
    }

    @Override
    public List<Applicant> addApplicants(List<Applicant> applicants) {
        logger.info("Successfully add applicants");
        return applicantRepository.saveAll(applicants);
    }

    @Override
    public Applicant addApplicant(Applicant applicant) {
        logger.info("Successfully adding applicant");
        return applicantRepository.save(applicant);
    }


}



