package main.java.com.firstrain.similarity;

public class Globals {

    public static String TARGET_SOLR_URL="http://pdsmysql1-rl.ca.firstrain.net:8080/solr/ExtractedPhrasesNew/"; // Temporary, with docs from Jan 1.
    public static String TARGET_DSDOCSS_URL="http://pdsmysql1-rl.ca.firstrain.net:8080/solr/DSDocs/";
    public static String CAT_ID_FIELD_NAME="catNarrow"; // for sdsmysql1-l
    public static int MIN_PHRASE_SIZE=1;
    public static int DAYS_IN_DEFAULT_BUFFER_PERIOD=10;
    public static int REQUIRED_BUFFER_DAY_COUNT=5;
    public static int MAX_DAYS_BEFORE_RECALC=2;
    public static int DEFAULT_DAYS_IN_BACKGROUND_INTERVAL=20;
    public static String BASIC_WORDS_LIST="the a of in on an for it has don't do not and with be to is was will there than i've didn't can't can did";
    
    //public static String TARGET_DSDOCSS_URL="http://sdsmysql1-l.ca.firstrain.net:8080/solr/DSDocs/"; // Staging, Being rebuilt
    //public static String TARGET_SOLR_URL="http://pdsmysql1-rl.ca.firstrain.net:8080/solr/ExtractedPhrases/"; // Used for development.
    //public static String TARGET_SOLR_URL="http://sdsmysql1-l.ca.firstrain.net:8080/solr/ExtractedPhrases/";  // Used for staging tests (Now limited to MArch+)
    //String CAT_ID_FIELD_NAME="catIdNarrow"; // for pdsmysql1-rl

    //public static String FEATURE_TYPES="wikiTitleRare,wikiTitleMultiToken";
	//public static double FREQ_CUTOFF_FOR_RARENESS=0.1; // Bigger causes bigger clusters
    //public static int OVERLAP_SIZE_DEMAND=3;  // Bigger causes smaller clusters 
    //public static int BACKGROUND_START_DAY_ID=5570;
    //public static int DAYS_IN_BACKGROUND_INTERVAL=20;

    //public static String ALL_TOPIC_CAT_IDS="1909549"; //1280598 
    //public static String ALL_TOPIC_CAT_IDS="3701093,5075088,5075094,3701132,3701135,3701108,3701150";

/*
    public static String ALL_TOPIC_CAT_IDS="3701093,5075088,5075094,3701132,3701135,3701108,3701150,3701210,3701114,3701213,3701120,3701126,3701141,3701264,3701219,3701216,3701270,3701159,3701222,3701267,3701111,5075369,3701144,5075372,"+
"3701207,5075375,5075378,3704588,3701258,3701153,3701240,3701129,5075381,3701123,3701318,3701225,5075384,5075387,5196650,5196653,3701228,3701171,3701231,3701282,3701156,3704178,4313458,3701147,3704591,"+
"3701174,3701303,3701321,3701186,3701234,3701288,3701315,3701331,3701291,3701334,3701255,3701195,3701237,3701261,3701249,3701192,3701183,3701243,3701198,3701165,3701300,3701340,3701279,3701201,3701309,3701246,"+
"4313455,3704594,3701162,3701337,3701177,3701294,3701306,3701337,3701168,3701285,3701294,3701312,4313452,4313449,3701297,3701180,3701376,3701204,3701252,3701189,5075390,5075393,5075396,5196665,5196662,5196659,"+
"5196656,5196635,5196641,5196605,5196617,5196626,5196632,5196638,5196644,5196647,5196599,5196611,5196620,5196629,5196641"; 
*/

/*  NEW LIST OF FEATURE TYPES
    chunkedPhraseFilteredInTitle, chunkedPhraseFilteredTypeInTitle
    chunkedPhraseFiltered, chunkedPhraseFilteredType, chunkedPhraseFilteredSentence

    wikiTitleMultiTokenInTitle
    wikiTitleRareInTitle
    wikiTitleSemiRareInTitle
    wikiTitleCommonInTitle
    wikiTitleMultiToken, wikiTitleMultiTokenSentence
    wikiTitleRare, wikiTitleRareSentence
    wikiTitleSemiRare, wikiTitleSemiRareSentence
    wikiTitleCommon, wikiTitleCommonSentence

    namedEntityOrgInTitle
    namedEntityLocalInTitle
    namedEntityPersonInTitle
    namedEntityOrg, namedEntityOrgSentence
    namedEntityLocal, namedEntityLocalSentence
    namedEntityPerson, namedEntityPersonSentence
*/

}