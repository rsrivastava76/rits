package main.java.com.firstrain.similarity;

import java.io.*;
import java.util.*;
import java.time.*;
import java.net.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Stream implements Serializable {

    private static String targetSolrUrl=Globals.TARGET_SOLR_URL; 

    public String id=null;
    public ConfigObject configObject=null;
    /*
    public String filterString=null;
    public int day0=0;
    public int sampleDays=0;
    public String featureTypes="";
    private double freqCutoffForRareness=0.1;
    */

    public int backgroundDocCount=0;
    public List<String> backgroundData=null;
    public List<DocInStream> latestDocsClustered=null;

    public Stream(String streamId) {this.id=streamId;}
    public void update(ConfigObject configObject) {this.configObject=configObject;}
    public void resetLatestDocsClustered() {latestDocsClustered=new ArrayList<DocInStream>();}
    public void generateBackground() {
        List<String> intervalWordList=new ArrayList<String>();

        int docCount=0;
        // FROM EACH DAY'S DOCS, COLLECT FEATURE WORDS (IN SET) THEN COMBINE TO MAKE A BIG LIST WITH REPEATS BY DAY
        for (int targetDay=configObject.BACKGROUND_START_DAY_ID; targetDay<configObject.BACKGROUND_START_DAY_ID+configObject.DAYS_IN_BACKGROUND_INTERVAL; targetDay++) {
            Set<String> thisDaysWords =  new HashSet<String>();
            LocalDate today = LocalDate.now();
            String urlEncodedCatIdFilterForSolr=null;
            String catIdFilterForSolr="("+configObject.STREAM_CAT_IDS.replace(","," ")+")";
            try {urlEncodedCatIdFilterForSolr=URLEncoder.encode(catIdFilterForSolr,"UTF-8");} catch (Exception e) {U.p("CHARACTER ENCODING ISSUE"); e.printStackTrace();}
            String dataUrl=targetSolrUrl+"select?q="+Globals.CAT_ID_FIELD_NAME+"%3A"+urlEncodedCatIdFilterForSolr+"+AND+dayId%3A"+(targetDay)+"&rows=1000&wt=json&indent=true&fl=siteDocId,groupId,"+configObject.FEATURE_TYPES;
            U.p("SOLR BACKGROUND DOCS REQUEST: "+dataUrl);
            String data = U.loadUrl(dataUrl);
            JsonParser parser = new JsonParser();
            try {
                JsonArray jsonDocs = parser.parse(data).getAsJsonObject().get("response").getAsJsonObject().get("docs").getAsJsonArray();
                docCount=docCount+jsonDocs.size();
                jsonDocs.forEach(solrDoc-> {
                    SimCalcManager.addNormalizedGoodQualityFeaturesToCollection(thisDaysWords, solrDoc.getAsJsonObject(), configObject.FEATURE_TYPES);
                });
            } catch (Exception badDocs) {U.p("SOLR DOC PARSING ERROR: "+badDocs);}

            for (String w:thisDaysWords) {intervalWordList.add(w.toLowerCase().replace("\"",""));}
            U.p(dataUrl);
            U.p("DAY "+targetDay+": TOTAL DOCS: "+docCount);
        }
        this.backgroundDocCount=docCount;
        // THEN COUNT FREQUENCY OF EACH WORD AND POPULATES commonWords LIST.
        Map<String,Integer> allWordsCount=new HashMap<>();
        for (String w:intervalWordList) {int oldCount=0; try {oldCount=allWordsCount.get(w).intValue();} catch(Exception e) {} allWordsCount.put(w,oldCount+1);}
        List<String> commonWords = new ArrayList<>();
        for (String w: allWordsCount.keySet()) {if (allWordsCount.get(w)>=(configObject.DAYS_IN_BACKGROUND_INTERVAL*configObject.FREQ_CUTOFF_FOR_RARENESS)) {commonWords.add(w);}}
        backgroundData=commonWords; //U.join(commonWords,"|");k
        U.p("ALL WORDS "+allWordsCount.keySet().size()+" COMMON WORDS: "+commonWords.size());
    }
    public boolean hasSufficientBackgroundData(int targetDay) {
        if (backgroundData==null) {return false;}
        if (targetDay<configObject.BACKGROUND_START_DAY_ID+configObject.DAYS_IN_BACKGROUND_INTERVAL+Globals.REQUIRED_BUFFER_DAY_COUNT) {return false;}
        else {return true;}
    }
    public void printClusterData() {
        U.p("TOTAL DOCS ANALYZED: "+latestDocsClustered.size());
        for (DocInStream d: latestDocsClustered) {
            if (d.clusterEvidence.startsWith("GROUP")) {continue;}
            else {U.p(d.toString());}
        }
    }
    public String getJson() {return getJson(-1,-1);}
    public String getJson(int foregroundDay0, int foregroundDays) {
        //MultiSet<String> groupIds=new MultiSet<>();
        Map<String,Integer> groupSizesById=new HashMap<>();
        Map<String,List<DocInStream>> latestClustersMap=new HashMap<>();
        for (DocInStream d: latestDocsClustered) {
            int gs=0; Integer a=groupSizesById.get(d.groupId); if (a!=null) {gs=a.intValue();}  groupSizesById.put(d.groupId,gs+1);
            List<DocInStream> x=latestClustersMap.get(d.clusterId); if (x==null) {x=new ArrayList<DocInStream>(); latestClustersMap.put(d.clusterId,x);} 
            x.add(d);
        }

        String result="{\"id\":\""+id+"\""
        +",\"filterString\":\""+configObject.STREAM_CAT_IDS+"\""
        +",\"backgroundDay0\":\""+configObject.BACKGROUND_START_DAY_ID+"\""
        +",\"backgroundDays\":\""+configObject.DAYS_IN_BACKGROUND_INTERVAL+"\""
        +",\"backgroundDocCount\":\""+backgroundDocCount+"\""
        +",\"backgroundDataPhraseCount\":\""+((backgroundData!=null)?backgroundData.size():null)+"\""
        +",\"backgroundData\":\""+((backgroundData!=null)?backgroundData:null)+"\""
        +",\"foregroundDay0\":\""+foregroundDay0+"\""
        +",\"foregroundDays\":\""+foregroundDays+"\""
        +",\"foregroundDocCount\":\""+((latestDocsClustered!=null)?latestDocsClustered.size():null)+"\""
        +",\"foregroundGroupCount\":\""+((latestDocsClustered!=null)?groupSizesById.keySet().size():null)+"\""
        +",\"foregroundClusterCount\":\""+((latestDocsClustered!=null)?latestClustersMap.keySet().size():null)+"\"";


        result=result+",\"oneDocPerCluster\":[";
        String delC="";
        for (String clusterId: latestClustersMap.keySet()) {
            List<DocInStream> docs=latestClustersMap.get(clusterId);
            result=result+delC+latestClustersMap.get(clusterId).get(0).getJsonWithClusterSize(docs.size()); delC=",";
        }
        result=result+"]";
        result=result+",\"clusters\":{";
        delC="";
        for (String clusterId: latestClustersMap.keySet()) {
            Set<String> groupsShown=new HashSet<>();
            List<DocInStream> docs=latestClustersMap.get(clusterId);
            result=result+delC+"\""+clusterId+"\":[";
            String delD="";
            for (DocInStream d: docs) {if (!groupsShown.contains(d.groupId)) {result=result+delD+d.getJsonWithGroupSize(groupSizesById.get(d.groupId)); delD=","; groupsShown.add(d.groupId);}}
            result=result+"]";
            delC=",";
        }
        result=result+"}}";
        return result;
    }

}
