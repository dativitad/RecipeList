package com.big.nuts;

import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.big.nuts.model.Recipe;
import com.big.nuts.service.GetRequest;
import com.big.nuts.service.SearchRequest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Spinner;

public class RecipesActivity extends Activity implements OnQueryTextListener, RecipesArrayAdapter.OnListItemClickListener {
	
	@Bind(R.id.serach) SearchView searchView;
	@Bind(R.id.spinerChView) Spinner spinerChView;
	@Bind(R.id.spinerChRate) Spinner spinerChRate;
	@Bind(R.id.listViewID) ListView listview;
	@Bind(R.id.gridViewID) GridView gridView;
	
	List<Recipe> recipesR = SearchRequest.getSearchResult();
	SearchRequest sReques = new SearchRequest();
	RecipesArrayAdapter adapter;
	
	Thread justSearch, testGet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.list_view_layout);
		
		ButterKnife.bind(this);
		
		searchView.setOnQueryTextListener(this);
		spinerChView.setOnItemSelectedListener(changeView);
		spinerChRate.setOnItemSelectedListener(changeRate);
		
		adapter = new RecipesArrayAdapter(RecipesActivity.this, R.layout.image_title, recipesR);
		adapter.setOnLisetItemClickListener(this);
		
		listview.setAdapter(adapter);
	}

	OnItemSelectedListener changeRate = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			
			if(parent.getItemAtPosition(position).toString().equals(getString(R.string.tranding))) {
				SearchRequest.rate = "&sort=t";
			}else {
				SearchRequest.rate = "&sort=r";
			}
			justSearch = new Thread(new SearchRequest());
			justSearch.start();
			
			try {
				justSearch.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			adapter.setNewData(SearchRequest.getSearchResult());
			adapter.notifyDataSetChanged();	
						
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		
		}
	};
	
	OnItemSelectedListener changeView = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			
			if(parent.getItemAtPosition(position).toString().equals(getString(R.string.grid))) {

				listview.setAdapter(null);
				adapter.setIsGridView(true);
				gridView.setAdapter(adapter);					
				
			}else {
			
				gridView.setAdapter(null);
				adapter.setIsGridView(false);
				listview.setAdapter(adapter);	
			}			
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		
		}
	};
	

	@Override
	public boolean onQueryTextSubmit(String query) {

		try {
			justSearch.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		adapter.setNewData(SearchRequest.getSearchResult());
		adapter.notifyDataSetChanged();				
			
		return false;
	}

	@Override
	public boolean onQueryTextChange(String newText) {
		
		if(newText.indexOf(" ")>=0) {
			newText = newText.replace(" ", "%20");
		}
		
		SearchRequest.query = newText;
		
		justSearch = new Thread(new SearchRequest());
		if(justSearch.isAlive()) {
			justSearch.interrupt();
		}
		justSearch.start();			
			
		return false;
	}

	public void startIntent() {
		
		Intent intent = null;
		try {
			intent = new Intent(RecipesActivity.this, Class.forName("com.big.nuts.DetailRecipeActivity"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		startActivity(intent);
	}

	@Override
	public void onListItemClick(String id) {
		
		GetRequest.recipeId = id;
		
		Thread getRecipe = new Thread(new GetRequest());
		getRecipe.start();
		
		try {
			getRecipe.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		startIntent();
		
	}

	@Override
	protected void onResume() {
		super.onResume();
		for(View nextView : RecipesArrayAdapter.clickFalse) {
			nextView.setClickable(true);
		}
		RecipesArrayAdapter.clickFalse.clear();
	}

}
