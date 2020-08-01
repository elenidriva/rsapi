package gr.codehub.rsapi.io;

import gr.codehub.rsapi.enums.DegreeLevel;
import gr.codehub.rsapi.enums.ExperienceLevel;
import gr.codehub.rsapi.enums.Region;
import gr.codehub.rsapi.enums.Status;
import gr.codehub.rsapi.model.Applicant;
import gr.codehub.rsapi.model.ApplicantSkill;
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


public class ExcelApplicantReader implements Reader<Applicant> {
    @Override
    public List<Applicant> readFromExcel() throws FileNotFoundException {
        Logger.log("Read Applicants from Given Excel File");
        FileInputStream data = new FileInputStream(new File("datarsapi.xlsx"));
        List<Applicant> applicantList = new ArrayList<>();
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(data);
            XSSFSheet sheet = workbook.getSheetAt(0);
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
                Applicant applicant = new Applicant();
                applicant.setApplicantSkillList(new ArrayList<>());

                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    cellValues.add(cell.getStringCellValue());
                    if (cell.getColumnIndex() >= 6) {
                        Skill skill = new Skill();
                        skill.setTitle(cell.getStringCellValue());
                        ApplicantSkill applicantSkill = new ApplicantSkill();
                        applicantSkill.setSkill(skill);
                        applicantSkill.setApplicant(applicant);
                        applicant.getApplicantSkillList().add(applicantSkill);

                    }
                }
                extractApplicant(applicant, cellValues);
                applicantList.add(applicant);
            }

            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return applicantList;
    }

    private Applicant extractApplicant(Applicant applicant, List<String> cellValues) {

        applicant.setFirstName(cellValues.get(0));
        applicant.setLastName(cellValues.get(1));
        applicant.setAddress(cellValues.get(2));
        applicant.setRegion(Region.findRegionByLocation(cellValues.get(3)));
        applicant.setDegreeLevel(DegreeLevel.findDegreeLevel(cellValues.get(4)));
        applicant.setExperienceLevel(ExperienceLevel.findDExpLevel(cellValues.get(5)));
        applicant.setStatus(Status.ACTIVE);
        applicant.setApplicationDate(LocalDate.now());
        return applicant;
    }
}