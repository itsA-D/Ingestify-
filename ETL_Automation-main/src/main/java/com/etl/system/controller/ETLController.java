package com.etl.system.controller;

import com.etl.system.dto.DataAnalysisResultDTO;
import com.etl.system.dto.VisualizationDTO;
import com.etl.system.service.ETLAnalysisService;
import com.etl.system.service.VisualizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

@Controller
public class ETLController {

    @Autowired
    private ETLAnalysisService etlAnalysisService;
    
    @Autowired
    private VisualizationService visualizationService;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) {
        try {
            List<DataAnalysisResultDTO> results = etlAnalysisService.processCSVFile(file);
            List<Map<String, String>> rawData = etlAnalysisService.getRawData();
            VisualizationDTO visualizations = visualizationService.generateVisualizations(results, rawData);
            etlAnalysisService.saveAnalysisResults(results);
            
            model.addAttribute("results", results);
            model.addAttribute("visualizations", visualizations);
            model.addAttribute("message", "File uploaded and processed successfully!");
            
            return "results";
        } catch (Exception e) {
            model.addAttribute("message", "Error processing file: " + e.getMessage());
            return "upload";
        }
    }
}