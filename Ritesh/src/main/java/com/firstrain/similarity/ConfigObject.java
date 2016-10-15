package main.java.com.firstrain.similarity;

import java.io.Serializable;

public class ConfigObject implements Serializable {
    public ConfigObject() {}
    public ConfigObject(String choice) throws Exception {
        if ("default".equals(choice)) {}
        else {throw new Exception("Undefined config object type.");}
    }
    public ConfigObject(String streamCatIds, String featureTypes, int backgroundStartDay, int daysInBackgroundInterval, double freqCutoffForRareness, int overlapSizeDemand) {
        if (streamCatIds!=null) STREAM_CAT_IDS=streamCatIds;
        if (featureTypes!=null) FEATURE_TYPES=featureTypes;
        if (backgroundStartDay>=0) BACKGROUND_START_DAY_ID=backgroundStartDay;
        if (daysInBackgroundInterval>=0) DAYS_IN_BACKGROUND_INTERVAL=daysInBackgroundInterval;
        if (freqCutoffForRareness>=0) FREQ_CUTOFF_FOR_RARENESS=freqCutoffForRareness;
        if (overlapSizeDemand>=0) OVERLAP_SIZE_DEMAND=overlapSizeDemand;
    }
    
    public String FEATURE_TYPES="wikiTitleRare,wikiTitleMultiToken";
	public double FREQ_CUTOFF_FOR_RARENESS=0.1; // Bigger causes more features called "RARE",  causes bigger clusters
    public int OVERLAP_SIZE_DEMAND=3;  // Bigger demands more ovrlap, causes smaller clusters

    public int BACKGROUND_START_DAY_ID=5570;
    public int DAYS_IN_BACKGROUND_INTERVAL=20;
    public String STREAM_CAT_IDS="1909549"; 
}
