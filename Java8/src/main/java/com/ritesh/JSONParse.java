package com.ritesh;

import net.sf.json.JSONObject;

public class JSONParse {

	public static void main(String[] args) {
		try {
			String jsonData =
					"{\"responseHeader\":{\"status\":0,\"QTime\":43524},\"response\":{\"numFound\":985,\"start\":0,\"docs\":[]},\"facet_counts\":{\"facet_queries\":{},\"facet_fields\":{\"catIdPassing\":[\"5066940\",985,\"512056\",985,\"696215\",985,\"696253\",985,\"300059\",958,\"320913\",957,\"299706\",953,\"1757174\",937,\"500840\",919,\"5074507\",902,\"296748\",882,\"3352801\",652,\"1750056\",592,\"288559\",591,\"500899\",562,\"695885\",490,\"2428979\",488,\"695904\",446,\"3701039\",439,\"730047\",419,\"707108\",340,\"695803\",330,\"941527\",319,\"2675172\",311,\"1673220\",309,\"254895\",292,\"276205\",290,\"513099\",288,\"1017846\",271,\"3351098\",271,\"3352777\",271,\"3352795\",271,\"1263235\",255,\"1475885\",226,\"1263229\",223,\"695836\",223,\"544926\",222,\"288557\",202,\"5615204\",195,\"695822\",193,\"4969053\",190,\"317864\",184,\"1270823\",170,\"511888\",168,\"3350828\",163,\"561435\",161,\"388534\",159,\"381018\",154,\"1483385\",147,\"947303\",147,\"688658\",146,\"3351802\",128,\"384223\",128,\"495430\",126,\"511921\",125,\"5531087\",125,\"5070539\",122,\"634627\",117,\"4968515\",115,\"511915\",109,\"768580\",109,\"2639435\",108,\"5467076\",103,\"696227\",102,\"254888\",95,\"6390335\",95,\"1527459\",94,\"291773\",93,\"5609685\",93,\"696210\",93,\"5065552\",87,\"561731\",87,\"1074608\",86,\"561969\",86,\"285398\",85,\"5070054\",85,\"4004058\",84,\"561981\",83,\"709432\",83,\"696218\",82,\"350259\",80,\"511918\",78,\"307305\",77,\"511804\",77,\"696242\",77,\"6390780\",75,\"511927\",74,\"254890\",73,\"696236\",73,\"5091155\",72,\"1172329\",71,\"563873\",70,\"291775\",69,\"514930\",68,\"4967060\",67,\"5070295\",67,\"1757060\",66,\"4968614\",64,\"6748320\",64,\"4244839\",63],\"catIdPassing\":[]},\"facet_dates\":{}}}";

			JSONObject json = JSONObject.fromObject(jsonData);
			JSONObject facetRes = json.getJSONObject("facet_counts").getJSONObject("facet_fields");

			System.out.println("blogURL: " + facetRes.getString("catIdPassing"));	
		} catch (Exception e) {
			System.out.println("Error " + e.getMessage());	
		}


	}

}