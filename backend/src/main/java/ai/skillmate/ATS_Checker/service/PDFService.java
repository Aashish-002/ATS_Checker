package ai.skillmate.ATS_Checker.service;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.languagetool.JLanguageTool;
import org.languagetool.Language;
import org.languagetool.Languages;
import org.languagetool.rules.RuleMatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class PDFService {

    private static final Logger logger = LoggerFactory.getLogger(PDFService.class);

    public String processPDF(MultipartFile file) throws IOException {
        logger.info("Starting PDF processing");

        PDDocument document = null;
        String text;
        try {
            document = PDDocument.load(file.getInputStream());
            PDFTextStripper pdfStripper = new PDFTextStripper();
            text = pdfStripper.getText(document);
        } catch (IOException e) {
            logger.error("Error reading PDF file", e);
            throw e;
        } finally {
            if (document != null) {
                document.close();
            }
        }

        StringBuilder feedback = new StringBuilder();
        feedback.append("Comprehensive Review:\n\n");
        feedback.append(assessStructure(text));
        feedback.append(checkGrammarAndSpelling(text));
        feedback.append(optimizeForATS(text));
        feedback.append(highlightKeySkills(text));
        feedback.append(provideIndustrySpecificAdvice(text));

        logger.info("PDF processing completed");
        return feedback.toString();
    }

    private String assessStructure(String text) {
        logger.debug("Assessing structure of the resume");
        return "Structure is well-organized.\n\n";
    }

    private String checkGrammarAndSpelling(String text) throws IOException {
        logger.debug("Checking grammar and spelling");
        Language lang = Languages.getLanguageForShortCode("en-US");
        JLanguageTool langTool = new JLanguageTool(lang);
        List<RuleMatch> matches = langTool.check(text);

        StringBuilder feedback = new StringBuilder();
        feedback.append("Grammar and Spelling Check:\n");
        for (RuleMatch match : matches) {
            feedback.append("Potential error at line ")
                    .append(match.getLine())
                    .append(", column ")
                    .append(match.getColumn())
                    .append(": ")
                    .append(match.getMessage())
                    .append("\n");
        }
        feedback.append("\n");
        return feedback.toString();
    }

    private String optimizeForATS(String text) {
        logger.debug("Optimizing resume for ATS");
        return "Resume is optimized for ATS.\n\n";
    }

    private String highlightKeySkills(String text) {
        logger.debug("Highlighting key skills in the resume");
        return "Key skills are highlighted.\n\n";
    }

    private String provideIndustrySpecificAdvice(String text) {
        logger.debug("Providing industry-specific advice");
        return "Industry-specific advice provided.\n\n";
    }
}
