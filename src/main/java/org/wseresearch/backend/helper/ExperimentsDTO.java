package org.wseresearch.backend.helper;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "explanations")
public class ExperimentsDTO {

    @Id
    private String id;
    private Map<String, Integer> correctnessExperiments; // Hash, errors
    private Map<String, Understandability_Metric> understandabilityExperiments; // Tuple, {best,worst}

    public ExperimentsDTO() {
    }

    public ExperimentsDTO(String id, Map<String, Integer> correctnessExperiments, Map<String, Understandability_Metric> understandabilityExperiments) {
        this.id = id;
        this.correctnessExperiments = correctnessExperiments;
        this.understandabilityExperiments = understandabilityExperiments;
    }

    public Map<String, Integer> getCorrectnessExperiments() {
        return correctnessExperiments;
    }

    public Map<String, Understandability_Metric> getUnderstandabilityExperiments() {
        return understandabilityExperiments;
    }

    public void setCorrectnessExperiments(Map<String, Integer> correctnessExperiments) {
        this.correctnessExperiments = correctnessExperiments;
    }

    public void setCorrectnessExperiment(String hash, Integer errors) {
        this.correctnessExperiments.replace(hash, errors);
    }

    public void setUnderstandabilityExperiment(String tuple, Understandability_Metric understandabilityMetric) {
        this.understandabilityExperiments.replace(tuple, understandabilityMetric);
    }

    public void setUnderstandabilityExperiments(Map<String, Understandability_Metric> understandabilityExperiments) {
        this.understandabilityExperiments = understandabilityExperiments;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}