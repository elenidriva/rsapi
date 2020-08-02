# RS-API 
This is a simple Recruitment Service API. The Recruitment agency application consists of the following 3 sub-systems:
* Central Repository - Applicants, Job offers, Skills
* Matching Services
* Reporting Services


## Table of Contents
* [General info](#general-info)
* [End points] (#end-points)
* [Technologies & Tools](#technologies--tools)
* [Setup](#setup)
* [Run](#run)
* [Future improvements & To dos](#future-improvements--to-dos)
* [License](#license)
## General info
This is a simple Recruitment service API that provides various functionalities to the job applicants as well as to the recruiters. Each Applicant can insert data to the System including also a list of the skills.
The job offer end-point works accordingly. The system supports Matching funcationalities and proposals, such as Full Match (all job offer skills required to cover a position are matched fully by an applicant) and Partial Match.
The system offers statistic results and reporting services, e.g., most requested and most offered skills, reports according to specified range of dates.
## End points
* Reading files from Excel
``` 
 GET /excelApplicants (addApplicantsFromReaderNew)
 GET /excelJobOffer (addJobOfferFromExcel)
 GET /insertSkillsFromExcel (addSkillsFromReader)
 ```
* Applicant
``` 
 GET/applicant (getApplicants)
 GET/applicant/{id} (getApplicant)
 GET /applicant (getApplicants) 
 GET/applicant/criteria/ (findApplicantsByCriteria) - criteria supported: any combination of applicationDate, firstName, lastName, region
 POST/applicant (addApplicant)
 PUT/applicant/{id} (updateApplicant)
 PUT/applicant/{id}/inactive (setApplicantInactive) - criteria supported: any combination of jobOfferDate, positionTitle, region
```

* JobOffer
```

 GET/jobOffer (getJobOffers)
 GET/jobOffer/{id} (getJobOffer)
 GET/jobOffer/criteria (findJobOffersByCriteria)
 POST/jobOffer (addJobOffer)
 PUT/jobOffer/{id} (updateJobOffer)
 PUT/jobOffer/{id}/inactive (setJobOfferInactive)
```
* Skill
``` 
 GET/skill (getSkills)
 POST/skill (addSkill)
 POST/skillsMerge (mergeSkills)
 POST/skillSplit (splitSkill)
 ```

* Match
```
 GET/fullMatch (findFullMatches)
 GET/partialMatches (findPartialMatches)
 GET/proposed (getProposedMatches)
 GET/MostRecentFinalisedMatches (getMostRecentFinalisedMatches)
 GET/reports (getFinalisedMatchesWithDateRange)
 POST/match (createManualMatch)
 PUT/match (finaliseMatch)
 DELETE/match/{id} (deleteMatch)
 ```
* Report
```
 GET/notMatchedSkills
 GET/offered (getMostOfferedSkills)
 GET/requested (getMostRequestedSkills)
```

## Technologies & Tools
* Spring MVC
* Spring Boot
* Spring Data JPA
* Maven
* Microsoft SQL Server Management Studio 2018
* Java 8+
* Tomcat - embedded web server
* Lombok
* Logger
* Swagger

## Setup
To run this project, you just need to simply download it and import it in a programming environment (i.e. Eclipse IDE for Enterprise Java Developers).
Via command line:
```
$ cd  ../where_you_want_it_to_be_saved
$ git clone https://github.com/elenidriva/rsapi.git
```
Or just download and import existing maven project in your programming environment.
## Run
If not automatically configured, follow the instructions below:
Run as > Maven build

Add Goals: ```tomcat7:run```

In order to access the DB according to the configuration, use the following:

Username: sa

Password: passw0rd

## Future improvements & To dos
* Include a View (front-end with .jsp) in order to complete the MCV pattern and make the web application more user-friendly and complete
* Include Spring Security for authentication & security purposes
* Improve performance with multi-threading
* Split functionality of Applicant and applicant's form

### License
The application is under the MIT license.
