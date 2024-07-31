package ai.skillmate.ATS_Checker.controller;

import ai.skillmate.ATS_Checker.service.PDFService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    @Autowired
    private PDFService pdfService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPDF(@RequestParam("file") MultipartFile file) {
        try {
            String result = pdfService.processPDF(file);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to process PDF: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
