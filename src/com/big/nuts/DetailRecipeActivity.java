package com.big.nuts;


import com.big.nuts.model.Recipe;
import com.big.nuts.service.GetRequest;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailRecipeActivity extends Activity {

	@Bind(R.id.recipeTitleDetail) TextView recipeTitleDetail;
	@Bind(R.id.recipeImageDetail) ImageView recipeImage;
	@Bind(R.id.recipePublisher) TextView recipePublisher;
	@Bind(R.id.recipeSocialRank) TextView recipeSocialRank;
	@Bind(R.id.recipeIngradients) TextView recipeIngredients;
	@Bind(R.id.recipePublisherUrl) TextView recipePublisherUrl;
	@Bind(R.id.recipeSourceUrl) TextView recipeSourceUrl;
	@Bind(R.id.recipeF2fUrl) TextView recipeF2fUrl;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.details);
		
		ButterKnife.bind(this);
		
		Recipe recipe =  GetRequest.getRecipe();

		UrlImageViewHelper.setUrlDrawable(recipeImage, recipe.getImageUrl());
		
		recipeTitleDetail.setText(recipe.getTitle());
		recipePublisher.setText(String.format(getString(R.string.publisher), recipe.getPublisher()));
		
		recipePublisherUrl.setMovementMethod(LinkMovementMethod.getInstance());
		recipeSourceUrl.setMovementMethod(LinkMovementMethod.getInstance());
		recipeF2fUrl.setMovementMethod(LinkMovementMethod.getInstance());
		
		recipePublisherUrl.setText(Html.fromHtml("<a href='"+recipe.getPublisherUrl()+"'>"+getString(R.string.publisher_url)+"</a>"));
		recipeSourceUrl.setText(Html.fromHtml("<a href='"+recipe.getSourceUrl()+"'>"+getString(R.string.source_url)+"</a>"));
		recipeF2fUrl.setText(Html.fromHtml("<a href='"+recipe.getF2fUrl()+"'>"+getString(R.string.f2f_url)+"</a>"));
		
		recipeSocialRank.setText(String.format(getString(R.string.social_rank), recipe.getSocialRank()));

		String ingredients = "";
		
		for(String nextIngredient : recipe.getIngredients()) {
			ingredients += "– ".concat(nextIngredient.concat("\n"));
		}
		recipeIngredients.setText(ingredients);

	}

	
	
	
}
