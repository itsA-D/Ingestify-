package com.etl.system.service.impl;

import com.etl.system.dto.DataAnalysisResultDTO;
import com.etl.system.entity.ProcessedData;
import com.etl.system.repository.ProcessedDataRepository;
import com.etl.system.service.ETLAnalysisService;
import com.etl.system.service.analyzer.DataAnalyzer;
import com.etl.system.service.analyzer.DataAnalyzerFactory;
import com.etl.system.util.CSVHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
public class ETLAnalysisServiceImpl implements ETLAnalysisService {

    private List<Map<String, String>> rawData;
    
    @Autowired
    private ProcessedDataRepository processedDataRepository;
    
    @Autowired
    private DataAnalyzerFactory analyzerFactory;

    @Override
    public List<DataAnalysisResultDTO> processCSVFile(MultipartFile file) throws Exception {
        this.rawData = CSVHelper.csvToMapList(file);
        List<DataAnalysisResultDTO> results = new ArrayList<>();
        
        if (rawData.isEmpty()) {
            return results;
        }
        
        Set<String> columns = rawData.get(0).keySet();
        
        for (String column : columns) {
            DataAnalyzer analyzer = analyzerFactory.createAnalyzer(column);
            DataAnalysisResultDTO result = analyzer.analyze(rawData, column);
            results.add(result);
        }
        
        // Save results to database
        saveAnalysisResults(results);
        
        return results;
    }

    @Override
    public List<Map<String, String>> getRawData() {
        return this.rawData;
    }

    @Override
    public void saveAnalysisResults(List<DataAnalysisResultDTO> results) {
        List<ProcessedData> entities = new ArrayList<>();
        Date now = new Date();
        
        for (DataAnalysisResultDTO result : results) {
            if ("NUMERIC".equals(result.getDataType())) {
                ProcessedData data = new ProcessedData();
                data.setColumnName(result.getColumnName());
                data.setMeanValue(result.getMean());
                data.setMedianValue(result.getMedian());
                data.setSkewness(result.getSkewness());
                data.setKurtosis(result.getKurtosis());
                data.setStandardDeviation(result.getStandardDeviation());
                data.setProcessedDate(now);
                data.setSourceFileName("uploaded_file.csv"); // Set actual filename if available
                entities.add(data);
            }
        }
        
        if (!entities.isEmpty()) {
            processedDataRepository.saveAll(entities);
        }
    }
}