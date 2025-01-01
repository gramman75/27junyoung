package com.example.demo.controller;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Facility;
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
public class PlayableController {

    @Autowired
    GoogleSheetService googleSheetService;

    private static final String SPREADSHEET_ID = "13iLb1Ama2DMhGfIatlA5MrN5MP8SX8KgxBXM-gOi2r0";

    private Logger logger = LoggerFactory.getLogger(this.getClass()); 

    private static final String RANGE ="master!A2:H"; 

    @GetMapping("/playable")
    public ResponseEntity<List<Facility>> get(HttpServletRequest request, HttpServletResponse response) throws GeneralSecurityException, IOException {

 
        Sheets service = googleSheetService.getService();

        ValueRange sheetResponse = service.spreadsheets().values().get(SPREADSHEET_ID, RANGE).execute();
        List<List<Object>> values = sheetResponse.getValues();
        List<Facility> convert = googleSheetService.convertToEntity(values);
        return new ResponseEntity<List<Facility>>(convert, HttpStatus.OK); 
    }

    @PostMapping("/playable")
    public ResponseEntity<Facility> save(HttpServletRequest request, HttpServletResponse response, @RequestBody Facility facility) throws IOException, GeneralSecurityException{

        AppendValuesResponse result = null;

        List<List<Object>> values = googleSheetService.convertToObject(facility);

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

    return new ResponseEntity<Facility>(facility, HttpStatus.OK);
    }
}
