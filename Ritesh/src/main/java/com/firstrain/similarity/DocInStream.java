package main.java.com.firstrain.similarity;

import java.io.*;
import java.util.*;

public class DocInStream implements Serializable {
	public DocInStream(String streamId, String siteDocId, String title, String groupId) {
		this.streamId=streamId; this.siteDocId=siteDocId; this.title=title; this.groupId=groupId; this.rareFeaturesList=new HashSet<String>(); 
	}
	public DocInStream(String streamId, String siteDocId, String title, String url, String date, String groupId) {
		this.streamId=streamId; this.siteDocId=siteDocId; this.title=title; this.url=url; this.date=date; this.groupId=groupId; this.rareFeaturesList=new HashSet<String>(); 
	}
	public DocInStream(String streamId, String siteDocId, String title, String groupId, Set<String> rareFeaturesList) {
		this.streamId=streamId; this.siteDocId=siteDocId; this.title=title; this.groupId=groupId; this.rareFeaturesList=rareFeaturesList; 
	}
	String streamId;
	String title;
	String url;
	String date;
	String siteDocId, groupId; 
    Set<String> rareFeaturesList;
	List<DocInStream> links;
	String clusterId, algorithm, clusterEvidence;
	public String toString() {return "DOC: "+siteDocId+"|"+groupId+"|"+clusterId+"|"+title+"|"+clusterEvidence;} 
	public String getJsonWithClusterSize(int clusterSize) {
		String cleanTitle=title.replace("\"","\\\"");
		cleanTitle=cleanTitle.replace("\n"," ");
		String linksBackTo="["; String del=""; if (links!=null) {for (DocInStream d: links) {linksBackTo=linksBackTo+del+"\""+d.groupId+"\"";}} linksBackTo=linksBackTo+"]";
		return "{\"siteDocId\":\""+siteDocId+"\",\"groupId\":\""+groupId+"\""
		+",\"title\":\""+cleanTitle+"\""+",\"url\":\""+url+"\""+",\"date\":\""+date+"\""
		+",\"linksBackTo\":"+linksBackTo+",\"docsInCluster\":\""+clusterSize+"\",\"groupsInCluster\":\""+("UNKNOWN")+"\""
        +",\"rareFeaturesList\":\""+((rareFeaturesList!=null)?rareFeaturesList:null)+"\""
        +",\"clusterEvidence\":\""+clusterEvidence+"\"}";
	} 
	public String getJsonWithGroupSize(int groupSize) {
		String cleanTitle=title.replace("\"","\\\"");
		cleanTitle=cleanTitle.replace("\n"," ");
		String linksBackTo="["; String del=""; if (links!=null) {for (DocInStream d: links) {linksBackTo=linksBackTo+del+"\""+d.groupId+"\"";}} linksBackTo=linksBackTo+"]";
		return "{\"siteDocId\":\""+siteDocId+"\",\"groupId\":\""+groupId+"\""
		+",\"title\":\""+cleanTitle+"\""+",\"url\":\""+url+"\""+",\"date\":\""+date+"\""
		+",\"linksBackTo\":"+linksBackTo+",\"groupSize\":\""+groupSize+"\""
        +",\"rareFeaturesList\":\""+((rareFeaturesList!=null)?rareFeaturesList:null)+"\""
		+",\"clusterEvidence\":\""+clusterEvidence+"\"}";
	}
	public String getJson() {
		String cleanTitle=title.replace("\"","\\\"");
		cleanTitle=cleanTitle.replace("\n"," ");
		String linksBackTo="["; String del=""; if (links!=null) {for (DocInStream d: links) {linksBackTo=linksBackTo+del+"\""+d.groupId+"\"";}} linksBackTo=linksBackTo+"]";
		return "{\"siteDocId\":\""+siteDocId+"\",\"groupId\":\""+groupId+"\""
		+",\"title\":\""+cleanTitle+"\""+",\"url\":\""+url+"\""+",\"date\":\""+date+"\""
		+",\"linksBackTo\":"+linksBackTo
        +",\"rareFeaturesList\":\""+((rareFeaturesList!=null)?rareFeaturesList:null)+"\""
        +",\"clusterEvidence\":\""+clusterEvidence+"\"}";
	}
}