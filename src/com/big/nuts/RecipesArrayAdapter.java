package com.big.nuts;

import java.util.ArrayList;
import java.util.List;
import com.big.nuts.model.Recipe;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class RecipesArrayAdapter extends ArrayAdapter<Recipe> {

	static class ViewHolder {
		TextView textViewTitle, textViewSocialRank;
		String recipeId;
		ImageView imageView;		
	}
	public static List<View> clickFalse = new ArrayList<View>();
	View listGrid = null;
	Recipe recipe = null;
	
	private OnListItemClickListener mOnListItemClickListener;
	private Activity context;
	private int resource;
	private List<Recipe> mRecipes;
	private boolean isGridView;
	
	public RecipesArrayAdapter(Activity context, int resource, List<Recipe> objects) {
		super(context, resource, objects);
		mRecipes = objects != null ? objects : new ArrayList<Recipe>();
		this.resource = resource;
		this.context = context;
	}
	
	public void setIsGridView(boolean isGridView) {
		this.isGridView = isGridView;
	}
	
	public void setOnLisetItemClickListener(OnListItemClickListener onListItemClickListener) {
		mOnListItemClickListener = onListItemClickListener;
	}
	
	public void setNewData(List<Recipe> objects) {
		mRecipes = objects != null ? objects : new ArrayList<Recipe>();
	}
	
	@Override
	public int getCount() {
		return mRecipes != null ? mRecipes.size() : 0;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {
	
		recipe = mRecipes.get(position);		
		
		listGrid = convertView;
		
		if(listGrid == null) {
			LayoutInflater inflater =  context.getLayoutInflater();
			listGrid = inflater.inflate(resource, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textViewTitle = (TextView) listGrid.findViewById(R.id.recipeTitle);
			viewHolder.textViewSocialRank = (TextView) listGrid.findViewById(R.id.recipeSocialRank);
			viewHolder.imageView = (ImageView) listGrid.findViewById(R.id.recipeImage);
			viewHolder.recipeId = recipe.getRecipeId();
			listGrid.setTag(viewHolder);
		}
		
		setRecipeData();

		OnClickListener testListener = new OnClickListener() {
		
			@Override
			public void onClick(View v) {
				
				v.setClickable(false);
				clickFalse.add(v);
				
				if (mOnListItemClickListener != null) {
					mOnListItemClickListener.onListItemClick(((ViewHolder)v.getTag()).recipeId);						
				}
			}
		};
		
		listGrid.setOnClickListener(testListener);
		
		return listGrid;
	}

	private void setRecipeData() {
		
		ViewHolder view = (ViewHolder) listGrid.getTag();
		
		if(!isGridView) {
			view.textViewTitle.setText(recipe.getTitle());
			view.textViewSocialRank.setText("Social rank: " + recipe.getSocialRank());			
		}
		
		UrlImageViewHelper.setUrlDrawable(view.imageView, recipe.getImageUrl());
		view.recipeId = recipe.getRecipeId();
		
	}

	public interface OnListItemClickListener {
		void onListItemClick(String recipeId);
	}
}
