package alps.java.api.util;

public interface ISiSiTimeDistribution {
    double getMeanValue();

    void setMeanValue(double meanValue);

    double getStandardDeviation();

    void setStandardDeviation(double standardDeviation);

    double getMaxValue();

    void setMaxValue(double maxValue);

    double getMinValue();

    void setMinValue(double minValue);

    boolean isWellKnownDuration();

    void setWellKnownDuration(boolean wellKnownDuration);

    void addDistributionToMe(ISiSiTimeDistribution otherDistro);

    void subtractDurationFromMe(ISiSiTimeDistribution otherDistro);

    ISiSiTimeDistribution subtractDurationAndGiveResult(ISiSiTimeDistribution otherDistro);

    ISiSiTimeDistribution combineDistributionWeighted(ISiSiTimeDistribution otherDistro, double otherDistroWeight);

    void addDistributionToMeWeighted(ISiSiTimeDistribution otherDistro, double otherDistroWeight);

    void averageOutWith(ISiSiTimeDistribution otherDuration);

    void copyValuesOf(ISiSiTimeDistribution otherDistribution);

    ISiSiTimeDistribution makeCopyOfMe();
}

