package ai.skillmate.atschecker.service;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Scanner;

@Service
public class ResumeService {

    public String parseResume(MultipartFile file) {
        try {
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            StringBuilder resumeText = new StringBuilder();

            doc.getParagraphs().forEach(paragraph -> resumeText.append(paragraph.getText()).append("\n"));

            String keywords = "Java, Spring, REST, Hibernate";  // Example keywords
            return checkKeywords(resumeText.toString(), keywords);

        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to parse resume.";
        }
    }

    private String checkKeywords(String resumeText, String keywords) {
        Scanner scanner = new Scanner(resumeText);
        String[] keywordArray = keywords.split(", ");
        int matchedKeywords = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            for (String keyword : keywordArray) {
                if (line.contains(keyword)) {
                    matchedKeywords++;
                }
            }
        }
        return "Matched " + matchedKeywords + " out of " + keywordArray.length + " keywords.";
    }
}
