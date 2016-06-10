package main.java.com.firstrain.similarity;

import java.util.*;


import java.util.*;
import java.net.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import javax.sql.DataSource;
//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class SimCalcManager {
    private static String targetSolrUrl=Globals.TARGET_SOLR_URL;
	// I assume common word lists are already lower cased.

    private static Map<String,Stream> streamsWithBackgroundData;
	public static void main(String[] args) {
        String listOfManyCatIds="2387492,2903840,8923479";
        List<ConfigObject> streamConfigs=new ArrayList<>(); 
        for (String catId: listOfManyCatIds.split(",")) {
            ConfigObject configObject=null;
            //try {configObject=new ConfigObject("default");} catch (Exception e) {U.p(e); e.printStackTrace(); return;}
            configObject=new ConfigObject(catId, "wikiTitleRare,wikiTitleMultiToken", 5543, 20, 0.1, 3);
            streamConfigs.add(configObject);
        }
        streamsWithBackgroundData=createStreamsWithBackgroundData(streamConfigs, true);
		
        long start=System.currentTimeMillis();
        int targetDay=5573;
		for (String catId: listOfManyCatIds.split(",")) {
			try {runDocsAgainstStream(streamsWithBackgroundData.get(catId),targetDay);}
            catch (Exception e) {U.p("ERROR: "+e);}
		}
		U.p("TIME: "+(System.currentTimeMillis()-start)+" milliseconds");
	}

    public static void runListOfDocsAgainstStream(Stream stream, String docIdListForSolr) {
        // docIdList should have format: "(123456 4983764 883403)"
        String dataUrl=targetSolrUrl+"select?q="+"siteDocId%3A"+URLEncoder.encode(docIdListForSolr)+"&rows=1000&wt=json&indent=true&fl=siteDocId,groupId,"+stream.configObject.FEATURE_TYPES;
        U.p(dataUrl);
        String data = U.loadUrl(dataUrl); 
        U.p(data); 
        JsonParser parser = new JsonParser();
        JsonArray latestDocs = parser.parse(data).getAsJsonObject().get("response").getAsJsonObject().get("docs").getAsJsonArray();
        U.p("\n\n\nDOCS TODAY: "+latestDocs.size());
        int count=0;
        Map<String,List<String>> groups=new HashMap<>();
        Map<String,List<String>> clusters=new HashMap<>();
        for (JsonElement doc: latestDocs) {
            try {
                JsonObject docJsonO=doc.getAsJsonObject();
                DocInStream oneDoc=runOneSolrDocAgainstStream(stream,docJsonO); //docJsonO);
                if (groups.get(oneDoc.groupId)==null) {groups.put(oneDoc.groupId,new ArrayList<String>());}
                groups.get(oneDoc.groupId).add(oneDoc.siteDocId);
                if (clusters.get(oneDoc.clusterId)==null) {clusters.put(oneDoc.clusterId,new ArrayList<String>());}
                clusters.get(oneDoc.clusterId).add(oneDoc.siteDocId);
                //U.p("DOC ANALYZED: "+oneDoc.siteDocId+", "+oneDoc.groupId+", "+oneDoc.clusterId+", SIZE: "+clusters.get(oneDoc.clusterId).size()+", "+oneDoc.clusterEvidence);
            } catch (Exception e) {U.p("ERROR: "+e);e.printStackTrace();}
            count++; 
        }
        U.p("STREAM DOC GROUPINGS: "+stream.configObject.STREAM_CAT_IDS+"\t"+latestDocs.size()+"\t"+groups.keySet().size()+"\t"+clusters.keySet().size());
    }
    public static void runDocsAgainstStream(Stream stream, int targetDay) { 
        //stream.resetLatestDocsClustered(); // REMOVED BECAUSE I MIGHT RUN THIS MULTIPLE TIMES FOR MULTIPLE DAYS
        
            String urlEncodedCatIdFilterForSolr=null;
            String catIdFilterForSolr="("+stream.configObject.STREAM_CAT_IDS.replace(","," ")+")";
            try {urlEncodedCatIdFilterForSolr=URLEncoder.encode(catIdFilterForSolr,"UTF-8");} catch (Exception e) {U.p("CHARACTER ENCODING ISSUE"); e.printStackTrace();}

        String dataUrl=targetSolrUrl+"select?q="+Globals.CAT_ID_FIELD_NAME+"%3A"+urlEncodedCatIdFilterForSolr+"+AND+dayId%3A"+(targetDay)+"&rows=1000&wt=json&indent=true&fl=siteDocId,groupId,"+stream.configObject.FEATURE_TYPES;
        U.p(dataUrl);
		String data = U.loadUrl(dataUrl);  
        JsonParser parser = new JsonParser();
        JsonArray latestDocs = parser.parse(data).getAsJsonObject().get("response").getAsJsonObject().get("docs").getAsJsonArray();
        U.p("\n\n\nDOCS TODAY: "+latestDocs.size());
        int count=0;
        Map<String,List<String>> groups=new HashMap<>();
        Map<String,List<String>> clusters=new HashMap<>();
		for (JsonElement doc: latestDocs) {
            try {
                JsonObject docJsonO=doc.getAsJsonObject();
                DocInStream oneDoc=runOneSolrDocAgainstStream(stream,docJsonO); //docJsonO);
				if (groups.get(oneDoc.groupId)==null) {groups.put(oneDoc.groupId,new ArrayList<String>());}
				groups.get(oneDoc.groupId).add(oneDoc.siteDocId);
				if (clusters.get(oneDoc.clusterId)==null) {clusters.put(oneDoc.clusterId,new ArrayList<String>());}
				clusters.get(oneDoc.clusterId).add(oneDoc.siteDocId);
				//U.p("DOC ANALYZED: "+oneDoc.siteDocId+", "+oneDoc.groupId+", "+oneDoc.clusterId+", SIZE: "+clusters.get(oneDoc.clusterId).size()+", "+oneDoc.clusterEvidence);
            } catch (Exception e) {U.p("ERROR: "+e);e.printStackTrace();}
            count++; 
        }
        U.p("STREAM DOC GROUPINGS: "+stream.configObject.STREAM_CAT_IDS+"\t"+latestDocs.size()+"\t"+groups.keySet().size()+"\t"+clusters.keySet().size());
    }
    public static DocInStream runOneSolrDocAgainstStream(Stream stream, JsonObject docJsonO) {
        String siteDocId=docJsonO.get("siteDocId").getAsString();
        String groupId=docJsonO.get("groupId").getAsString();
        //String title=U.getDocumentTitle(siteDocId); //docJsonO.get("title").getAsString();
        Map<String,String> docData=U.getDocumentData(siteDocId);
        DocInStream oneDoc=new DocInStream(stream.id, siteDocId, docData.get("title"), docData.get("url"), docData.get("date"), groupId);
        //DocInStream oneDoc=new DocInStream(stream.id, siteDocId, docData.get("title"), "http://test.com", "1971-23-87", groupId);
        addNormalizedGoodQualityFeaturesToCollection(oneDoc.rareFeaturesList, docJsonO, stream.configObject.FEATURE_TYPES);
        //SimilarityCalc.analyzeAndEnhanceDoc(oneDoc, stream);
        SimilarityCalc.analyzeAndEnhanceDocAllComparisons(oneDoc, stream);
        return oneDoc;
    }
    public static void addNormalizedGoodQualityFeaturesToCollection(Set<String> holder, JsonObject solrDoc, String featureTypes) {
        // WORRY ABOUT LOWER CASE, COMBINING MULTIPLE FEATURE TYPES, LISTS vs SETS (new solr phrase lists allow dupes)
        // WORRY ABOUT getAsString() vs toString()
        // WORRY ABOUT SELECTING GOOD FEATURES BASED ON "good quality" algorithm
        String siteDocId=solrDoc.get("siteDocId").getAsString();
        for (String featureType: featureTypes.split(",")) {
            try {
                JsonArray phrases = solrDoc.get(featureType).getAsJsonArray();
                for (JsonElement p: phrases) {
                    String feature=p.getAsString().toLowerCase();  // getAsString() needed.  toString() returns string with quotes around result.
                    feature=feature.replace("\"","");
                    if (isGoodQuality(feature)) {holder.add(feature);}
                }  
            } catch (Exception noPhrasesFail) {U.p("DOC:"+siteDocId+" - FEATURE ("+featureType+") RETRIEVAL ERROR:"+noPhrasesFail);}
        }
    }
    public static boolean isGoodQuality(String feature) {
        //String[] basicWords="the a of in on an for it has don't do not and with be to is was will there than".split(" ");
        String[] basicWords=Globals.BASIC_WORDS_LIST.split(" ");
        String[] words=feature.toLowerCase().split(" ");
        int length=words.length;
        int c=0; for (String t:words) {if (!Arrays.asList(basicWords).contains(t)) {c=c+1;}}
         //int c=0; for (String t:words) {if (!basicWordsByFreq.contains(t.toLowerCase())) {c=c+1;}}
        if (length==0) {return false;}
        if (length==1 && c<1) {return false;}
        if (length>=2 && c<2) {return false;}
        return true;
    }



    static Map<String, Stream> streamsWithBackgroundByFilterString;
    static Map<String, Stream> getLatestStreamsWithBackground() {return streamsWithBackgroundByFilterString;}

    private static Map<String, Stream> createStreamsWithBackgroundData(List<ConfigObject> streamConfigs, boolean overwrite) {
        Map<String, Stream> streamsBeingComputed=new HashMap<String, Stream>();
        for (ConfigObject configObject: streamConfigs) {
            Stream stream=new Stream("3984792384");  stream.update(configObject); 
            stream.generateBackground();
            streamsBeingComputed.put(stream.configObject.STREAM_CAT_IDS, stream);
            U.p(stream.configObject.STREAM_CAT_IDS+": "+stream.backgroundData);
            U.p("    TOTAL WORD COUNT: "+stream.backgroundData.size());
        }
        //persistAsFile(listsBeingComputed);
        if (overwrite) {streamsWithBackgroundByFilterString=streamsBeingComputed;}
        return streamsBeingComputed;
    }
}