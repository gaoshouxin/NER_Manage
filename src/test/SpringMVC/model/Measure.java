package test.SpringMVC.model;

/**
 * Created by gaoshouxin on 2017/5/30 0030.
 */
public class Measure {
    private String testData;
    private String accuracyRate;

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    public String getAccuracyRate() {
        return accuracyRate;
    }

    public void setAccuracyRate(String accuracyRate) {
        this.accuracyRate = accuracyRate;
    }

    public String getRecallRate() {
        return recallRate;
    }

    public void setRecallRate(String recallRate) {
        this.recallRate = recallRate;
    }

    public String getfValue() {
        return fValue;
    }

    public void setfValue(String fValue) {
        this.fValue = fValue;
    }

    private String recallRate;
    private String fValue;
}
