package com.zsalcedo.fileupload.utils;

import com.zsalcedo.fileupload.model.File;
import com.zsalcedo.fileupload.model.Person;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class Utils {

    public String getFileNameWithoutExtension(String originalFilename) {
        if (originalFilename != null) {
            int lastIndex = originalFilename.lastIndexOf('.');
            return originalFilename.substring(0, lastIndex);
        }
        return "";

    }

    public String getFileExtension(String originalFilename) {
        if (originalFilename != null) {
            int lastIndex = originalFilename.lastIndexOf('.');//ropa.pdf
            return originalFilename.substring(lastIndex + 1);
        }
        return "";
    }

    public boolean isExcelFile(MultipartFile file) {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())){
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public List<Person> extractPersonsFromExcel(MultipartFile file) throws IOException {
        List<Person> persons = new ArrayList<>();

        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                Row currentRow = sheet.getRow(i);
                if (currentRow == null) {
                    continue;
                }

                Person person = new Person();
                person.setCodigo((int) currentRow.getCell(0).getNumericCellValue());
                person.setName(currentRow.getCell(1).getStringCellValue());
                person.setAge((int) currentRow.getCell(2).getNumericCellValue());
                person.setEmail(currentRow.getCell(3).getStringCellValue());
                persons.add(person);
            }
        }

        return persons;
    }

    public boolean isPdfFile(MultipartFile file) {
        return file.getContentType() != null && file.getContentType().equals("application/pdf");
    }

    public File createNewFile(MultipartFile file) {
        File dbFile = new File();
        Utils utils = new Utils();
        dbFile.setName(utils.getFileNameWithoutExtension(file.getOriginalFilename()));
        dbFile.setExtension(utils.getFileExtension(file.getOriginalFilename()));
        dbFile.setPeso(file.getSize());
        dbFile.setDateUploaded(LocalDate.now());
        return dbFile;
    }


}
