package main.java.com.firstrain.similarity;

import java.util.*;

class SimilarityCalc {

    static public void analyzeAndEnhanceDocAllComparisons(DocInStream targetDoc, Stream stream) {
    	analyzeAndEnhanceDoc(targetDoc, stream);
	}
    static public void analyzeAndEnhanceDoc(DocInStream targetDoc, Stream stream) {
		if (targetDoc==null) {U.p("NULL TARGET DOC!");}
		if (stream.latestDocsClustered==null) {U.p("NULL DOC LIST!");}
		if (stream.backgroundData==null) {U.p("NULL COMMON WORDS!");}
    	// Configuration
    	int minPhraseSize=Globals.MIN_PHRASE_SIZE; // Config.MIN_PHRASE_SIZE;
    	int overlapSizeDemand=stream.configObject.OVERLAP_SIZE_DEMAND;
    	// I assume common word lists are already lower cased.

    	// Key derived values.  Added to document at end.

    	List<DocInStream> discoveredLinks=new ArrayList<DocInStream>();
    	String algorithm="Pennwell1."+stream.configObject.FEATURE_TYPES+"."+minPhraseSize;
    	String clusterId="0";
    	String evidenceOfSimilarity="none";

	    Set<String> thisDocRareWordList=new HashSet<>();
	    for (String phrase: targetDoc.rareFeaturesList) {
	    	//String lowerCasePhrase=phrase.toLowerCase();
	    	if (phrase.split(" ").length>=minPhraseSize && !stream.backgroundData.contains(phrase)) {thisDocRareWordList.add(phrase);}
	    }

	    boolean clustered=false;
	    // MAKE DUPLICATE DOCS ALWAYS SIMILAR (CLUSTERED).
	    for (DocInStream previousDoc: stream.latestDocsClustered) {
	        if (targetDoc.groupId.equals(previousDoc.groupId)) {
	            clusterId=previousDoc.clusterId; 
	            evidenceOfSimilarity="GROUPID";
	            clustered=true;
	            discoveredLinks.add(previousDoc);
	            break;
	        }
	    }
	    //targetDoc.featureList!!!!!!!!!!!!!  USED WHERE?
	    //FOR EFFICIENCY, ADD THE COMPUTED RARE WORDS TO THE PREVIOUS DOC!
	    // IF STILL UNCLUSTERED, LOOK FOR OVERLAP WITH PREVIOUS DOC
	    if (!clustered) {
	        for (DocInStream previousDoc: stream.latestDocsClustered) {
	            if (previousDoc.rareFeaturesList==null) {continue;}

	            //List<String> previousDocWordsLowerCase=new ArrayList<String>(); 
	            //for (String phrase: previousDoc.featureList) {previousDocWordsLowerCase.add(phrase.toLowerCase());}
	            List<String> intersectionList=new ArrayList<>(); 
	            for (String f:thisDocRareWordList) {if (previousDoc.rareFeaturesList.contains(f)) {intersectionList.add(f);}}

	            if (intersectionList.size()>=overlapSizeDemand) {
					List<String> cleanedList=removeSimilarWords(intersectionList);
					//U.p("CHECKING OVERLAP SIZE: "+(intersectionList.size()-cleanedList.size()));
		            if (cleanedList.size()>=overlapSizeDemand) {
		            	clusterId=previousDoc.clusterId;  
		            	evidenceOfSimilarity="OVERLAP: "+cleanedList;
		                clustered=true;
	            		discoveredLinks.add(previousDoc);
		                break;
		            }
		        }
	        }
	    }
	    // IF STILL UNCLUSTERED, USE NEW CLUSTER ID
	    if (!clustered) {
	        clusterId=getNextNewSimId(); 
	        evidenceOfSimilarity="FIRST DOCUMENT IN CLUSTER";
	        clustered=true;
	    }
    	//targetDoc.simDocInfo=new SimDocInfo(algorithm,clusterId,evidenceOfSimilarity,thisDocRareWordList);
    	targetDoc.algorithm=algorithm;
    	targetDoc.clusterId=clusterId;
    	targetDoc.clusterEvidence=evidenceOfSimilarity;
    	targetDoc.rareFeaturesList=thisDocRareWordList; 
    	targetDoc.links=discoveredLinks;
    	stream.latestDocsClustered.add(targetDoc);
	    //processedDocList.add(targetDoc);
    }
	// =====================================================
	static List<String> removeSimilarWords(List<String> startList) {
	    List<String> endList=new ArrayList<>();
	    for (String a: startList) {
	        boolean foundBigger=false;
	        for (String b: startList) {
	        	if (b.equals(a) || b.indexOf(a)==-1) {continue;}
	        	foundBigger=true; break;
	        }
	        if (!foundBigger) {endList.add(a);}
	    }
	    return endList;
	}
	// =====================================================
    static String getNextNewSimId() {return ""+System.nanoTime();}
	// =====================================================
}