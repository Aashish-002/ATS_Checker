package ai.skillmate.atschecker.controller;

import ai.skillmate.atschecker.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadResume(@RequestParam("file") MultipartFile file, Model model) {
        String result = resumeService.parseResume(file);
        model.addAttribute("result", result);
        return "result";
    }
}
