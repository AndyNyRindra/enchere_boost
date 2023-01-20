package com.enchere.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Utilities {

    public static final SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");

    public static String formatDate (Date date) {
        return DateFor.format(date);
    }

    public static String formatNumberWithTwoDigitsAfterComa(double number) {
        return String.format("%.2f", number).replace(",", ".");

    }

    public static String formatNumber(double number) {
        String ans = "";
        String numberString = formatNumberWithTwoDigitsAfterComa(number);
        String[] numberStringArray = numberString.split("[.]");
        int count = 0;
        for (int i = numberStringArray[0].length() - 1; i >= 0; i--) {
            ans = numberStringArray[0].charAt(i) + ans;
            count++;
            if (count == 3 && i != 0) {
                ans = " " + ans;
                count = 0;
            }
        }
        if (numberStringArray[1].equals("00")) {
            return ans;
        }
        return ans + "." + numberStringArray[1];
    }

    public static List<String> newLine(String s, int nbChar) {
        List<String> result = new ArrayList<String>();
        int nbLine = 1;
        int begin = 0;
        for (int i = 0; i < s.length(); i++) {
            if ((i > nbLine * nbChar && s.charAt(i) == ' ') || i == s.length()) {
                result.add(s.substring(begin, i));
                begin = i+1;
                nbLine++;
            }
        }
        if (begin < s.length()) result.add(s.substring(begin, s.length()));
        System.out.println(result.size());
        return result;
    }

    public static HttpHeaders getPdfHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        // Here you have to set the actual filename of your pdf
        String filename = fileName;
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        return headers;
    }

    public static byte[] pdfToBytes(String path) throws IOException {
        byte[] contents = Files.readAllBytes(Paths.get(path));
        return contents;
    }
}
