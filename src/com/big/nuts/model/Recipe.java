package com.big.nuts.model;

import java.util.List;


public class Recipe {
	
	private String publisher;
	private String f2fUrl;
	private List<String> ingredients;
	private String sourceUrl;
	private String recipeId;
	private String imageUrl;
	private double socialRank;
	private String publisherUrl;
	private String title;

	public Recipe() {

	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getF2fUrl() {
		return f2fUrl;
	}

	public void setF2fUrl(String f2fUrl) {
		this.f2fUrl = f2fUrl;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public double getSocialRank() {
		return socialRank;
	}

	public void setSocialRank(double socialRank) {
		this.socialRank = socialRank;
	}

	public String getPublisherUrl() {
		return publisherUrl;
	}

	public void setPublisherUrl(String publisherUrl) {
		this.publisherUrl = publisherUrl;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	
}
