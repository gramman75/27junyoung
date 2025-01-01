package com.example.demo.service;

import com.example.demo.model.Facility;
// import com. at.interceptor.common.util.GoogleSheetUtil;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import org.springframework.stereotype.Component;

@Component
public class GoogleSheetService {
	private static final String CREDENTIALS_FILE_PATH = "googlesheet/google_spread_sheet_key.json";
	private static final String APPLICATION_NAME = "google-sheet-project";

	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException {
        ClassLoader loader = GoogleSheetService.class.getClassLoader();
        GoogleCredential credential = GoogleCredential.fromStream(new FileInputStream(loader.getResource(CREDENTIALS_FILE_PATH).getFile()))
                .createScoped(SCOPES);

        return credential;
    }

    public Sheets getService() throws IOException, GeneralSecurityException{
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build(); 
        
        return service;
    }

    public List<Facility> convertToEntity(List<List<Object>> values){
        List<Facility> facilities = new ArrayList<>();
        if (values == null || values.isEmpty()) {
            return facilities;
            } else {
            for (List row : values) {
                facilities.add(new Facility(row.get(0), row.get(1), row.get(2), row.get(3), row.get(4), row.get(5), row.get(6), row.get(7)));
            }
            }

        return facilities;
    }

    public List<List<Object>> convertToObject(Facility facility){
        List<List<Object>> values = new ArrayList<>();

        List<Object> value = new ArrayList<>();
        
        value.add(facility.getId());
        value.add(facility.getName());
        value.add(facility.getLocation());
        value.add(facility.getAvailable());
        value.add(facility.getAccessibility());
        value.add(facility.getFee());
        value.add(facility.getInfo());
        value.add(facility.getCoordinate());

        values.add(value);

        return values;
    }
    // public static void main(String[] args) throws IOException, GeneralSecurityException {
    //         final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    //     // String spreadSheetId = "13iLb1Ama2DMhGfIatlA5MrN5MP8SX8KgxBXM-gOi2r0";
    //     String range = "sheet1!A2:G"; // Sheet1의 A1부터 C3까지

    //     // List<List<Object>> values = Arrays.asList(
    //     //         Arrays.asList("Name", "Age", "Gender"),
    //     //         Arrays.asList("Alice", 25, "Female"),
    //     //         Arrays.asList("Bob", 30, "Male"),
    //     //         Arrays.asList("choi", 32, "fmale1111111111"));
    //     // ValueRange data = new ValueRange().setValues(values);

    //     // Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
    //     //         .setApplicationName(APPLICATION_NAME)
    //     //         .build();

    //     Sheets service = getService();

    //     ValueRange response = service.spreadsheets().values().get(SPREADSHEET_ID, range).execute();
    //     List<List<Object>> values = response.getValues();
    //     // service.spreadsheets().values().update(spreadSheetId, range, data)
    //     //         .setValueInputOption("USER_ENTERED")
    //     //         .execute();
    //     if (values == null || values.isEmpty()) {
    //         System.out.println("No data found.");
    //         } else {
    //         System.out.println("Name, Major");
    //         for (List row : values) {
    //             // Print columns A and E, which correspond to indices 0 and 4.
    //             System.out.printf("%s, %s\n", row.get(0), row.get(1));
    //         }
    //         }
    // }
}