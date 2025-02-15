package com.example.demo.controller;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Facility;
import com.example.demo.model.Review;
import com.example.demo.service.GoogleSheetService;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ReviewController {

    @Autowired
    GoogleSheetService googleSheetService;

    private static final String SPREADSHEET_ID = "13iLb1Ama2DMhGfIatlA5MrN5MP8SX8KgxBXM-gOi2r0";

    private Logger logger = LoggerFactory.getLogger(this.getClass()); 

    private static final String RANGE ="review!A2:B"; 

    @GetMapping("/review")
    public ResponseEntity<List<Review>> get(@RequestParam(value = "id", required = false)String id) throws GeneralSecurityException, IOException {

 
        Sheets service = googleSheetService.getService();

        ValueRange sheetResponse = service.spreadsheets().values().get(SPREADSHEET_ID, RANGE).execute();
        List<List<Object>> values = sheetResponse.getValues();
        List<Review> convert = googleSheetService.convertReviewToEntity(values);

        List<Review> filter = convert.stream()
                            .filter(review->review.getId().equals(id))
                            .collect(Collectors.toList());
        return new ResponseEntity<List<Review>>(filter, HttpStatus.OK); 
    }

    @PostMapping("/review")
    public ResponseEntity<Review> save(HttpServletRequest request, HttpServletResponse response, @RequestBody Review review ) throws IOException, GeneralSecurityException{

        AppendValuesResponse result = null;

        List<List<Object>> values = googleSheetService.convertReviewToObject(review);

        Sheets service = googleSheetService.getService();


        try {
            ValueRange body = new ValueRange().setValues(values);
            result = service.spreadsheets().values().append(SPREADSHEET_ID, RANGE, body)
                        .setValueInputOption("USER_ENTERED")
                        .execute();
            logger.info(result.getUpdates().getUpdatedCells() + " cells appended.");
        } catch (GoogleJsonResponseException e) {
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 404) {
                logger.info("Spreadsheet not found with id" );
            } else {
                throw e;
        }
    }

    return new ResponseEntity<Review>(review, HttpStatus.OK);
    }
}
