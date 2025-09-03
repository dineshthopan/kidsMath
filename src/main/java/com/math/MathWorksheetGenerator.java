package com.math;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;

public class MathWorksheetGenerator {

    public static final String ADDITION_TYPE = "Addition";
    public static final String SUBTRACTION_TYPE = "Subtraction";
    public static final String MULTIPLICATION_TYPE = "Multiplication";
    public static int RANDOM_NUMBER_FROM = 100;
    public static int RANDOM_NUMBER_TO = 999;


    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        try {
            // Generate addition and subtraction worksheet
            String filename = String.format("math_add_sub_worksheet_%d.pdf", System.currentTimeMillis());
            generateAdditionSubtractionWorksheet(filename, 10, 3);
            System.out.println("Addition and Subtraction Worksheet generated successfully.");

            // Generate multiplication worksheet
            filename = String.format("math_multiplication_worksheet_%d.pdf", System.currentTimeMillis());
            generateMultiplicationWorksheet(filename, 10, 2);
            System.out.println("Multiplication Worksheet generated successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateAdditionSubtractionWorksheet(String filename, int numPages, int digitSize) throws IOException {

        // Get today's date and format it
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));

        PDDocument document = new PDDocument();
        int columnsPerPage = 4;
        int rowsPerPage = 16;

        for (int pageIdx = 0; pageIdx < numPages; pageIdx++) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Generate pages
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Set math type based on page index
                String mathType = getMathType(pageIdx);
                PDFont font = PDType1Font.TIMES_ROMAN;
                contentStream.setFont(font, 15);

                // Set initial position for the header
                float startX = 50;
                float startY = 750; // Adjust this value to position the first
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText(String.format("Type: %s              Score: ____/16          Date: __/__/%s ", mathType, formattedDate));
                contentStream.endText();

                //Generate Random numbers
                if(digitSize == 2){ //Generate 2 digit random. Defaults to 3 digit
                    RANDOM_NUMBER_FROM = 10;
                    RANDOM_NUMBER_TO = 99;
                }
                int streamSize = 32;
                int[] randomIntegers = generateRandomNumbers(streamSize, RANDOM_NUMBER_FROM, RANDOM_NUMBER_TO);

                int i = streamSize - 1;
                contentStream.setFont(font, 25);
                for (int row = 1; row <= rowsPerPage; row++) {
                    for (int col = 0; col < columnsPerPage; col++) {
                        // Adjust Y position for each row
                        float y = startY - row * 40;
                        String value;
                        contentStream.beginText();
                        if (row == 3 || row == 4 || row == 7 || row == 8 || row == 11 || row == 12 || row == 15 || row == 16) {
                            float x = (startX + col * 150) - 20;
                            contentStream.newLineAtOffset(x, y);
                            contentStream.showText("---------");
                        } else {
                            float x = startX + col * 150;
                            value = String.format("%2d", randomIntegers[i]);
                            contentStream.newLineAtOffset(x, y);
                            contentStream.showText(value);
                            i--;
                        }
                        contentStream.endText();
                    }
                }
            }
        }

        // Save the document
        document.save(filename);
        document.close();
    }


    private static void generateMultiplicationWorksheet(String filename, int numPages, int digitSize) throws IOException {
        // Get today's date and format it
        String formattedDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy"));

        PDDocument document = new PDDocument();
        int columnsPerPage = 4;
        int rowsPerPage = 18;

        for (int pageIdx = 0; pageIdx < numPages; pageIdx++) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Generate pages
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                PDFont font = PDType1Font.TIMES_ROMAN;
                contentStream.setFont(font, 15);

                // Set initial position for the header
                float startX = 50;
                float startY = 750; // Adjust this value to position the first
                contentStream.beginText();
                contentStream.newLineAtOffset(startX, startY);
                contentStream.showText(String.format("Type: %s              Score: ____/16          Date: __/__/%s ", MULTIPLICATION_TYPE, formattedDate));
                contentStream.endText();

                //Generate Random numbers
                if(digitSize == 2){ //Generate 2 digit random. Defaults to 3 digit
                    RANDOM_NUMBER_FROM = 10;
                    RANDOM_NUMBER_TO = 99;
                }
                int streamSize = 32;
                int[] randomIntegers = generateRandomNumbers(streamSize, RANDOM_NUMBER_FROM, RANDOM_NUMBER_TO);

                int i = streamSize - 1;
                contentStream.setFont(font, 25);
                for (int row = 1; row <= rowsPerPage; row++) {
                    for (int col = 0; col < columnsPerPage; col++) {
                        // Adjust Y position for each row
                        float y = startY - row * 40;
                        String value;
                        contentStream.beginText();
                        if (row == 3 || row == 5 || row == 6 || row == 9 || row == 11 || row == 12 || row == 15 || row == 17 || row == 18) {
                            float x = (startX + col * 150) - 20;
                            contentStream.newLineAtOffset(x, y);
                            contentStream.showText("---------");
                        }else if(row == 4 || row == 10 || row == 16){
                            // do nothing
                        }else {
                            float x = startX + col * 150;
                            value = String.format("%2d", randomIntegers[i]);
//                            if( row % 2 == 0){
//                                value = String.format("%2d", randomSingleIntegers[i]);
//                            }
                            contentStream.newLineAtOffset(x, y);
                            contentStream.showText(value);
                            i--;
                        }
                        contentStream.endText();
                    }
                }
            }
        }

        // Save the document
        document.save(filename);
        document.close();

    }

    public static String getMathType(int pageIndex) {
        String mathType;
        if (pageIndex == 0) {
            mathType = ADDITION_TYPE; // addition
        } else if (pageIndex == 1) {
            mathType = SUBTRACTION_TYPE; // subtraction
        } else {
            mathType = (pageIndex % 2 == 0) ? ADDITION_TYPE : SUBTRACTION_TYPE;
        }
        return mathType;
    }

    private static int[] generateRandomNumbers(int count, int RANDOM_NUMBER_FROM, int RANDOM_NUMBER_TO) {
        int[] randomNumbers = new int[count];
        for (int i = 0; i < count; i++) {
            randomNumbers[i] = RANDOM.nextInt(RANDOM_NUMBER_TO - RANDOM_NUMBER_FROM) + RANDOM_NUMBER_FROM;
        }
        Arrays.sort(randomNumbers);
        return randomNumbers;
    }
}
