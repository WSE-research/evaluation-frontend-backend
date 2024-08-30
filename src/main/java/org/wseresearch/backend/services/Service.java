package org.wseresearch.backend.services;

import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;
import org.wseresearch.backend.helper.*;
import org.wseresearch.backend.repositories.ExperimentsRepository;

@org.springframework.stereotype.Service
public class    Service {

    @Autowired
    private ExperimentsRepository experimentsRepository;
    private Map<String,Integer> correctnessExperiments;
    private Map<String,Understandability_Metric> understandabilityExperiments;
    private final Logger logger = LoggerFactory.getLogger(Service.class);

    public Service() throws IOException {
        correctnessExperiments = initCorrectnessExperiments("correctness_tuples.txt");
        understandabilityExperiments = initUnderstandabilityExperiments("understandability_tuples.tuples");
    }

    public ExperimentsDTO login(String id) {
        // Login, validate if userId exist
        if(checkUserExist(id)) {
            return this.experimentsRepository.findById(id).get();
        } else {
            throw new RuntimeException();
        }
    }

    public void storeCorrectnessEntry(String hash, Integer errors, String id) {
        ExperimentsDTO currentExperimentSetting = this.findExperimentsByUser(id);
        currentExperimentSetting.setCorrectnessExperiment(hash, errors);
        this.experimentsRepository.save(currentExperimentSetting);
    }

    public void storeExperiment(ExperimentsDTO experimentsDTO) {
        this.experimentsRepository.insert(experimentsDTO);
    }

    public ExperimentsDTO createUser() {
        String id = UUID.randomUUID().toString();
        ExperimentsDTO experimentsDTO = new ExperimentsDTO(id, correctnessExperiments, /*understandabilityExperiments*/null);//TODO!
        try {
            experimentsRepository.insert(experimentsDTO);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return experimentsDTO;
    }

    public ExperimentsDTO findExperimentsByUser(String id) {
        return this.experimentsRepository.findById(id).get();
    }

    public boolean checkUserExist(String id) {
        return experimentsRepository.existsById(id);
    }

    // TODO!
    private Map<String, Understandability_Metric> initUnderstandabilityExperiments(String experiment) throws IOException {
        Map<String, Understandability_Metric> understandabilityExperiments = new HashMap<>();
        try(InputStream inputStream = new ClassPathResource(experiment).getInputStream()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while((line = reader.readLine()) != null) {
                    List<String> hashes = List.of(line.split("\\s+"));
                    understandabilityExperiments.put(String.join("-",hashes),new Understandability_Metric());
                }
            }
        }
        return understandabilityExperiments;
    }


    public void storeUnderstandabilityEntry(String temperature, String id, String tuple, Understandability_Metric understandabilityMetric) {
        ExperimentsDTO experimentsDTO = this.findExperimentsByUser(id);
        experimentsDTO.setUnderstandabilityExperiment(temperature, tuple, understandabilityMetric);
        try {
            // Assuming savedEntity is the          result of the save operation
            ExperimentsDTO savedEntity = experimentsRepository.save(experimentsDTO);
            if (savedEntity != null && savedEntity.getId() != null) {
                logger.info("Save operation was successful.");
            } else {
                logger.info("Save operation might have failed or the entity doesn't get its ID generated upon saving.");
            }
        } catch (DataIntegrityViolationException e) {
            logger.error("Data integrity violation while saving the entity: " + e.getMessage());
        } catch (TransactionSystemException e) {
            logger.error("Transaction system exception while saving the entity: " + e.getMessage());
        } catch (Exception e) {
            logger.error("An unexpected error occurred while saving the entity: " + e.getMessage());
        }
    }

    private Map<String, Integer> initCorrectnessExperiments(String experiment) throws IOException {
        Map<String, Integer> correctnessExperiments = new HashMap<>();
        try(InputStream inputStream = new ClassPathResource(experiment).getInputStream()) {
            try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                        String line;
                        while((line = reader.readLine()) != null) {
                            correctnessExperiments.put(line, -1);
                        }
            }
        }
        return correctnessExperiments;
    }

}
