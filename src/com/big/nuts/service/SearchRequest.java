package com.big.nuts.service;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.big.nuts.Splash;
import com.big.nuts.model.Recipe;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class SearchRequest implements Runnable {

	private static List<Recipe> searchResult = new ArrayList<Recipe>();

	String baseUrl = "http://food2fork.com/api/search?key=07f91970df1dabc45a700f37794bb316&q=";
	public static String query = null;
	public static String rate = "&sort=r";
	String url = baseUrl.concat(rate);
	
	
	public static List<Recipe> getSearchResult() {
		return  searchResult;
	}
	
	public void searchRecipe() {
	
		if (query != null) {
			url = baseUrl.concat(query).concat(rate);		
		} else {
			url = baseUrl.concat(rate);
		}
		
		String jsonRootObjectStr = new JsonStringRetriever().getJSONStrFromUrl(url);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonRootObjectStr);
		JsonObject rootObj = element.getAsJsonObject();
		JsonArray recipes = rootObj.getAsJsonArray("recipes");
		Type dataListType = new TypeToken<Collection<Recipe>>() {}.getType();
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		searchResult = gson.fromJson(recipes, dataListType);
		
	}

	@Override
	public void run() {
		searchRecipe();
		Splash.loadContent.interrupt();
	}
	
	
}
