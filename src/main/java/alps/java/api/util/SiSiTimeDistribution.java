package alps.java.api.util;

import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.logging.Logger;

public class SiSiTimeDistribution implements ISiSiTimeDistribution {

    private double meanValue;
    private double standardDeviation;
    private double maxValue;
    private double minValue;
    private boolean wellKnownDuration;
    private static Logger Log = Logger.getLogger(SiSiTimeDistribution.class.getName());

    // Getter-Methode für meanValue
    public double getMeanValue() {
        return meanValue;
    }

    // Setter-Methode für meanValue
    public void setMeanValue(double meanValue) {
        this.meanValue = meanValue;
    }

    // Getter-Methode für standardDeviation
    public double getStandardDeviation() {
        return standardDeviation;
    }

    // Setter-Methode für standardDeviation
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    // Getter-Methode für maxValue
    public double getMaxValue() {
        return maxValue;
    }

    // Setter-Methode für maxValue
    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    // Getter-Methode für minValue
    public double getMinValue() {
        return minValue;
    }

    // Setter-Methode für minValue
    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    // Getter-Methode für wellKnownDuration
    public boolean isWellKnownDuration() {
        return wellKnownDuration;
    }

    // Setter-Methode für wellKnownDuration
    public void setWellKnownDuration(boolean wellKnownDuration) {
        this.wellKnownDuration = wellKnownDuration;
    }

    public void addDistributionToMe(ISiSiTimeDistribution otherDistro) {
        setMeanValue(getMeanValue() + otherDistro.getMeanValue());
        setStandardDeviation(Math.sqrt((getStandardDeviation() * getStandardDeviation()) + (otherDistro.getStandardDeviation() * otherDistro.getStandardDeviation())));
        setMaxValue(getMaxValue() + otherDistro.getMaxValue());
        setMinValue(getMinValue() + otherDistro.getMinValue());

        if (isWellKnownDuration()) {
            setWellKnownDuration(otherDistro.isWellKnownDuration());
        }
    }

    public void subtractDurationFromMe(ISiSiTimeDistribution otherDistro) {
        setMeanValue(getMeanValue() - otherDistro.getMeanValue());
        if (getMeanValue() < 0) {
            setMeanValue(0);
        }

        setMinValue(getMinValue() - otherDistro.getMinValue());
        if (getMinValue() < 0) {
            setMinValue(0);
        }

        setMaxValue(getMaxValue() - otherDistro.getMaxValue());
        if (getMaxValue() < 0) {
            setMaxValue(0);
        }
    }

    public ISiSiTimeDistribution subtractDurationAndGiveResult(ISiSiTimeDistribution otherDistro) {
        SiSiTimeDistribution result = new SiSiTimeDistribution();
        result.setMeanValue(getMeanValue() - otherDistro.getMeanValue());
        if (result.getMeanValue() < 0) {
            result.setMeanValue(0);
        }

        result.setMinValue(getMinValue() - otherDistro.getMinValue());
        if (result.getMinValue() < 0) {
            result.setMinValue(0);
        }

        result.setMaxValue(getMaxValue() - otherDistro.getMaxValue());
        if (result.getMaxValue() < 0) {
            result.setMaxValue(0);
        }
        return result;
    }

    public ISiSiTimeDistribution combineDistributionWeighted(ISiSiTimeDistribution otherDistro, double otherDistroWeight) {
        SiSiTimeDistribution result = new SiSiTimeDistribution();
        result.setMeanValue(getMeanValue() + (otherDistro.getMeanValue() * otherDistroWeight));
        result.setStandardDeviation(Math.sqrt((getStandardDeviation() * getStandardDeviation()) + (otherDistro.getStandardDeviation() * otherDistroWeight * otherDistro.getStandardDeviation() * otherDistroWeight)));
        result.setMaxValue(getMaxValue() + (otherDistro.getMaxValue() * otherDistroWeight));
        result.setMinValue(getMinValue() + (otherDistro.getMinValue() * otherDistroWeight));
        result.setWellKnownDuration(otherDistro.isWellKnownDuration() && isWellKnownDuration());
        return result;
    }

    public void addDistributionToMeWeighted(ISiSiTimeDistribution otherDistro, double otherDistroWeight) {
        setMeanValue(getMeanValue() + (otherDistro.getMeanValue() * otherDistroWeight));
        setStandardDeviation(Math.sqrt((getStandardDeviation() * getStandardDeviation()) + (otherDistro.getStandardDeviation() * otherDistroWeight * otherDistro.getStandardDeviation() * otherDistroWeight)));
        setMaxValue(getMaxValue() + (otherDistro.getMaxValue() * otherDistroWeight));
        setMinValue(getMinValue() + (otherDistro.getMinValue() * otherDistroWeight));

        if (isWellKnownDuration()) {
            setWellKnownDuration(otherDistro.isWellKnownDuration());
        }
    }

    @Override
    public String toString() {
        String result = "SiSi Time Distro with - Mean Value: " + convertFractionsOfDayToHourFormat(getMeanValue());

        if (!(standardDeviation == 0)) {
            result = result + " - Standard Deviation: " + convertFractionsOfDayToHourFormat(getStandardDeviation());
        }

        if (!(minValue == 0)) {
            result = result + " - Minimum time : " + convertFractionsOfDayToHourFormat(getMinValue());
        }

        if (!(maxValue == 0)) {
            result = result + " - Maximum time : " + convertFractionsOfDayToHourFormat(getMaxValue());
        }

        return result;
    }


    public void averageOutWith(ISiSiTimeDistribution otherDuration) {
        setMeanValue((getMeanValue() + otherDuration.getMeanValue()) / 2);
        setStandardDeviation((getStandardDeviation() + otherDuration.getStandardDeviation()) / 2);

        if (!(getMaxValue() > otherDuration.getMaxValue())) {
            setMaxValue(otherDuration.getMaxValue());
        }

        if (!(getMinValue() < otherDuration.getMinValue())) {
            setMinValue(otherDuration.getMinValue());
        }
    }

    public void copyValuesOf(ISiSiTimeDistribution otherDistribution) {
        //distributionType = otherDistribution.distributionType;
        setMeanValue(otherDistribution.getMeanValue());
        setStandardDeviation(otherDistribution.getStandardDeviation());
        setMinValue(otherDistribution.getMinValue());
        setMaxValue(otherDistribution.getMaxValue());
    }

    public ISiSiTimeDistribution makeCopyOfMe() {
        SiSiTimeDistribution result = new SiSiTimeDistribution();
        result.setMeanValue(getMeanValue());
        result.setStandardDeviation(getStandardDeviation());
        result.setMinValue(getMinValue());
        result.setMaxValue(getMaxValue());

        return result;
    }


    private static String convertFractionsOfDayToHourFormat(double fractionsOfDay) {
        String result = "";

        long days = (long) Math.floor(fractionsOfDay);
        if (days > 0) {
            result = result + days + " days, ";
        }

        int hours;
        fractionsOfDay = (fractionsOfDay - days) * 24;
        hours = (int) Math.floor(fractionsOfDay);
        if (hours > 0) {
            result = result + hours + " hours, ";
        }

        int minutes;
        fractionsOfDay = (fractionsOfDay - hours) * 60;
        minutes = (int) Math.floor(fractionsOfDay);
        if (minutes > 0) {
            result = result + minutes + " minutes, ";
        }

        int seconds;
        fractionsOfDay = (fractionsOfDay - minutes) * 60;
        seconds = (int) Math.floor(fractionsOfDay);
        if (seconds > 0) {
            result = result + seconds + " seconds, ";
        }

        int millis;
        fractionsOfDay = (fractionsOfDay - seconds) * 1000;
        millis = (int) Math.floor(fractionsOfDay);
        if (millis > 0) {
            result = result + millis + " millis, ";
        }

        if ((days + hours + minutes + seconds + millis) <= 0) {
            result = "0";
        }

        return result;
    }

    public static String convertFractionsOfDayToXSDDurationString(double fractionsOfDay) {
        String result = "P";
        int days = (int) fractionsOfDay;

        if (days > 0) {
            result = result + days + "D";
        }

        result = result + "T";

        int hours = (int) ((fractionsOfDay - days) * 24);
        if (hours > 0) {
            result = result + hours + "H";
        }

        int minutes = (int) ((fractionsOfDay - days - (hours / 24.0)) * 1440);
        if (minutes > 0) {
            result = result + minutes + "M";
        }

        double seconds = (fractionsOfDay - days - (hours / 24.0) - (minutes / 1440.0)) * 86400;
        if (seconds > 0) {
            result = result + seconds + "S";
        }

        if (days + hours + minutes + seconds <= 0) {
            result = "P0DT0H0M0S";
        }

        return result;
    }

    public static double convertXSDDurationStringToFractionsOfDay(String xsdDurationString) {
        //TODO: alps.net.api wurde weggelassen
        Locale customLocale = new Locale("en", "US");
        DecimalFormatSymbols customSymbols = DecimalFormatSymbols.getInstance(customLocale);
        customSymbols.setDecimalSeparator('.');
        String xsdDurationStringModified = StringExtractor.extractString(xsdDurationString);

        // Check if the string starts with 'P' and ends with 'S'
        if (!xsdDurationStringModified.startsWith("P")) {
            Log.warning("could not Parse the value " + xsdDurationStringModified + " as valid XSD Duration");
            return 0.0;
        }

        // Remove the 'P' and 'S' from the string to extract the duration components
        String duration = xsdDurationStringModified.substring(1, xsdDurationStringModified.length());

        double fractionsOfDay = 0;

        int daysIndex = duration.indexOf("D");
        if (daysIndex >= 0) {
            int days = Integer.parseInt(duration.substring(0, daysIndex));
            fractionsOfDay = fractionsOfDay + days;
            duration = duration.substring(daysIndex + 1);
        }
        //remove the T for Time that
        int indexOfT = duration.indexOf("T");
        if (indexOfT >= 0) {
            duration = duration.substring(indexOfT + 1);
        }

        int hoursIndex = duration.indexOf("H");
        if (hoursIndex >= 0) {
            int hours = Integer.parseInt(duration.substring(0, hoursIndex));
            fractionsOfDay = fractionsOfDay + (hours / 24.0);
            duration = duration.substring(hoursIndex + 1);
        }

        int minutesIndex = duration.indexOf("M");
        if (minutesIndex >= 0) {
            int minutes = Integer.parseInt(duration.substring(0, minutesIndex));
            fractionsOfDay = fractionsOfDay + (minutes / 1440.0);
            duration = duration.substring(minutesIndex + 1);
        }

        int secondsIndex = duration.indexOf("S");
        if (secondsIndex > 0) {
            //TODO: in alps.net.api wird das mit PASSProcessModelElement.customCulture geregelt
            double seconds = Double.parseDouble(duration.substring(0, secondsIndex).replace(customSymbols.getDecimalSeparator(), '.'));
            fractionsOfDay = fractionsOfDay + (seconds / 86400.0);
        }

        return fractionsOfDay;
    }


}