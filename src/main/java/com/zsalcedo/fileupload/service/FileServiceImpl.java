package com.zsalcedo.fileupload.service;

import com.zsalcedo.fileupload.model.File;
import com.zsalcedo.fileupload.model.Person;
import com.zsalcedo.fileupload.repository.FileRepository;
import com.zsalcedo.fileupload.repository.PersonRepository;
import com.zsalcedo.fileupload.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class FileServiceImpl {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private Utils utils;

    public ResponseEntity<String> saveExcelFile(MultipartFile file) {
        try {
            if (file.isEmpty() || !utils.isExcelFile(file)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El archivo está vacío o no es un Excel válido");
            }

            File dbFile = utils.createNewFile(file);
            fileRepository.save(dbFile);

            List<Person> persons = utils.extractPersonsFromExcel(file);
            personRepository.saveAll(persons);

            return ResponseEntity.ok("Archivo Excel subido y guardado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo: " + e.getMessage());

        }
    }

    public ResponseEntity<String> savePdfFile(MultipartFile file) {
        try {
            if (file.isEmpty() || !utils.isPdfFile(file)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("El archivo está vacío o no es un PDF válido");
            }
            File dbFile = utils.createNewFile(file);
            fileRepository.save(dbFile);
            return ResponseEntity.ok("Archivo PDF subido y guardado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al procesar el archivo: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getAllDocuments() {
        try {
            List<File> files = fileRepository.findAll();
            return ResponseEntity.ok(files);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener la lista de documentos: " + e.getMessage());
        }
    }

}
