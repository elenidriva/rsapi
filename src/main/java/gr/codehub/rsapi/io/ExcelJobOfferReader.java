package gr.codehub.rsapi.io;

import gr.codehub.rsapi.enums.ExperienceLevel;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.model.JobOffer;
import gr.codehub.rsapi.model.JobOfferSkill;
import gr.codehub.rsapi.model.Skill;
import gr.codehub.rsapi.utility.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExcelJobOfferReader implements Reader<JobOffer> {
    @Override
    public List<JobOffer> readFromExcel() throws FileNotFoundException {
        Logger.log("Read JobOffers from Given Excel File");
        FileInputStream data = new FileInputStream(new File("datarsapi.xlsx"));
        List<JobOffer> jobOfferList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(data);
            XSSFSheet sheet = workbook.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();
            boolean firstTime = true;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (firstTime) {
                    firstTime = false;
                    continue;
                }
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> cellValues = new ArrayList<>();
                JobOffer jobOffer = new JobOffer();
                jobOffer.setJobOfferSkillList(new ArrayList<>());

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cellValues.add(cell.getStringCellValue());
                    if (cell.getColumnIndex() >= 4) {
                        Skill skill = new Skill();
                        skill.setTitle(cell.getStringCellValue());
                        JobOfferSkill jobOfferSkill = new JobOfferSkill();
                        jobOfferSkill.setSkill(skill);
                        jobOfferSkill.setJobOffer(jobOffer);
                        jobOffer.getJobOfferSkillList().add(jobOfferSkill);
                        //applicantSkills.add(applicantSkill);
                    }
                }
                extractJobOffer(jobOffer, cellValues);
                jobOfferList.add(jobOffer);
            }
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jobOfferList;
    }

    private JobOffer extractJobOffer(JobOffer jobOffer, List<String> cellValues) {
        jobOffer.setCompany(cellValues.get(0));
        jobOffer.setPositionTitle(cellValues.get(1));
        jobOffer.setRegion(Region.findRegionByLocation(cellValues.get(2)));
        jobOffer.setExperienceLevel(ExperienceLevel.findDExpLevel(cellValues.get(3)));
        jobOffer.setStatus(Status.ACTIVE);
        jobOffer.setJobOfferDate(LocalDate.now());
        return jobOffer;
    }
}