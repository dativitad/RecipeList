package com.big.nuts.service;

import java.lang.reflect.Type;
import com.big.nuts.model.Recipe;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class GetRequest implements Runnable {

	String baseURL = "http://food2fork.com/api/get?key=07f91970df1dabc45a700f37794bb316&rId=";
	public static String recipeId = null;
	String url = baseURL.concat(recipeId);
	private static Recipe recipe = new Recipe();
	
	
	public static Recipe getRecipe() {
		return recipe;
	}
	
	public void getRecipeFromUrl() {
		
		String jsonRootObjectStr = new JsonStringRetriever().getJSONStrFromUrl(url);
		JsonParser parser = new JsonParser();
		JsonElement element = parser.parse(jsonRootObjectStr);
		JsonObject rootObj = element.getAsJsonObject();
		JsonObject recipeJs = rootObj.getAsJsonObject("recipe");
		
		Type dataType = new TypeToken<Recipe>() {}.getType();
		
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		recipe = gson.fromJson(recipeJs, dataType);

			
	}

	@Override
	public void run() {
		getRecipeFromUrl();
		
	}
}
