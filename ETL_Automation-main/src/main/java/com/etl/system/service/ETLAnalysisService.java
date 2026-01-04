package com.etl.system.service;

import com.etl.system.dto.DataAnalysisResultDTO;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

public interface ETLAnalysisService {
    List<DataAnalysisResultDTO> processCSVFile(MultipartFile file) throws Exception;
    List<Map<String, String>> getRawData();
    void saveAnalysisResults(List<DataAnalysisResultDTO> results);
}