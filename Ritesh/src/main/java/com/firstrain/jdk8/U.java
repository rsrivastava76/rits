package main.java.com.firstrain.jdk8;

import java.util.*;
import java.net.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
import java.time.temporal.*;
import com.google.gson.*;

public class U {
    public static void p(Object x) {System.out.println(x);}


    public static String loadUrl(String url) {
        Integer val;
        StringBuffer sb=new StringBuffer();
        BufferedReader br=null;
        try {
            URL theURL=new URL(url);
            br = new BufferedReader(new InputStreamReader(theURL.openStream()));
            for (String line = br.readLine();line != null;line = br.readLine()) {
                sb.append(line+"\n");
            }
        } catch (Exception e) {sb.append(e.toString());}
        finally { try {br.close();} catch (Exception e) {}}
        String response=sb.toString();
        return response;
    }


    public static String join(List list,String del) {
    	String result=""; String d="";
    	for (Object x: list) {result=result+d+x; d=del;}
    		return result;
    }
    public static int toInt(String x, int d) {
        try {return Integer.parseInt(x);} 
        catch (Exception e) {return d;}
    }
    public static long getDayId(String inputDate) {
        DateTimeFormatter formatter =DateTimeFormatter.ofPattern("MMM-d-yyyy HH:mm:ss");
        LocalDateTime frEpochTime = LocalDateTime.parse("Jan-1-2001 00:00:00", formatter);
        LocalDateTime targetTime = LocalDateTime.parse(inputDate+" 00:00:00", formatter);
        long dayCount=ChronoUnit.DAYS.between(frEpochTime, targetTime);
        return dayCount;
    }
    public static Map<String,String> getDocumentData(String siteDocId) {
        String dataUrl=Globals.TARGET_DSDOCSS_URL+"select?q=siteDocId%3A"+siteDocId+"&fl=title,dayId,insertTime,url&wt=json&indent=true";
        String data = U.loadUrl(dataUrl);
        JsonParser parser = new JsonParser();
        Map<String,String> docData=new HashMap<>();
        try {
            JsonArray jsonDocs = parser.parse(data).getAsJsonObject().get("response").getAsJsonObject().get("docs").getAsJsonArray();
            //solrDoc=jsonDocs.get(0);
            docData.put("title",jsonDocs.get(0).getAsJsonObject().get("title").getAsString());
            docData.put("dayId",jsonDocs.get(0).getAsJsonObject().get("dayId").getAsString());
            docData.put("url",jsonDocs.get(0).getAsJsonObject().get("url").getAsString());
            docData.put("date",jsonDocs.get(0).getAsJsonObject().get("insertTime").getAsString());
        } catch (Exception badDocs) {U.p("SOLR DSDOC PARSING ERROR: "+badDocs);}
        return docData;
    }
    public static String getDocumentTitle(String siteDocId) {
        String title="NA";
        String dayId="NA";
        String dataUrl=Globals.TARGET_DSDOCSS_URL+"select?q=siteDocId%3A"+siteDocId+"&fl=title,dayId&wt=json&indent=true";
        String data = U.loadUrl(dataUrl);
        JsonParser parser = new JsonParser();
        try {
            JsonArray jsonDocs = parser.parse(data).getAsJsonObject().get("response").getAsJsonObject().get("docs").getAsJsonArray();
            //solrDoc=jsonDocs.get(0);
            title=jsonDocs.get(0).getAsJsonObject().get("title").getAsString();
            dayId=jsonDocs.get(0).getAsJsonObject().get("dayId").getAsString();
        } catch (Exception badDocs) {U.p("SOLR DSDOC PARSING ERROR: "+badDocs);}
        return "("+dayId+") "+title;
    }
}