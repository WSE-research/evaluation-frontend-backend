package org.wseresearch.backend.helper;

public class Understandability_Metric {


    private String best;
    private String worst;

    public Understandability_Metric() {
        best = null;
        worst = null;
    }

    public String getBest() {
        return best;
    }

    public String getWorst() {
        return worst;
    }

    public void setWorst(String worst) {
        this.worst = worst;
    }

    public void setBest(String best) {
        this.best = best;
    }
}
