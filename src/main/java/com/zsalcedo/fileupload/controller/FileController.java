package com.zsalcedo.fileupload.controller;

import com.zsalcedo.fileupload.service.FileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @PostMapping("/upload/excel")
    @Operation(summary = "Subir un archivo Excel y extraer información de personas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo Excel subido y guardado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El archivo está vacío o no es un Excel válido")
    })
    public ResponseEntity<String> uploadFile(@RequestParam("file")  MultipartFile file) {
           return fileService.saveExcelFile(file);
    }

    @PostMapping("/upload/pdf")
    @Operation(summary = "Subir un archivo PDF")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Archivo PDF subido y guardado exitosamente"),
            @ApiResponse(responseCode = "400", description = "El archivo está vacío o no es un PDF válido")
    })
    public ResponseEntity<String> uploadPdfFile(@RequestParam("file") MultipartFile file) {
            return  fileService.savePdfFile(file);
    }

    @GetMapping("/documents")
    @Operation(summary = "Obtener una lista de todos los documentos")
    @ApiResponse(responseCode = "200", description = "Lista de documentos obtenida exitosamente")
    public ResponseEntity<?> getAllDocuments() {
        return  fileService.getAllDocuments();
    }
}
