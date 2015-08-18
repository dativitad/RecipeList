package com.big.nuts;

import com.big.nuts.service.SearchRequest;

import butterknife.Bind;
import butterknife.ButterKnife;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class Splash extends Activity {
	
	@Bind(R.id.bear) ImageView bearImg;
	@Bind(R.id.hat) ImageView hatImg;
	@Bind(R.id.logo) ImageView logoImg;
	
	Thread timer;
	public static Thread loadContent;
	DisplayMetrics dispMetrics = new DisplayMetrics();
	int width = 0, height = 0, hatImgHeight = 0, logoImgHeight = 0, bearImgHeight = 0, bearImgWidth = 0;
	int bearTopEdge = 0, bearBottomEdge = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if(!isInternetConnection()) {
			setContentView(R.layout.no_internet);	
		}else {
			setContentView(R.layout.splash);			
			ButterKnife.bind(this);
			
			loadContent = new Thread(new SearchRequest());
			loadContent.start();
			
			getMeasures();
			animateSplash();
			delayIntentStart();			
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(timer != null){
			timer.interrupt();						
		}
	}

	private boolean isInternetConnection() {
		ConnectivityManager connManag = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connManag.getActiveNetworkInfo();
		if(networkInfo != null) {
			return networkInfo.isConnectedOrConnecting();			
		}
		return false;
	}
	
	private void delayIntentStart() {
		
		timer = new Thread() {
			public void run() {
				Intent openStartPoint;
				try {
					sleep(3000);
					openStartPoint = new Intent(Splash.this, Class.forName("com.big.nuts.RecipesActivity"));
					startActivity(openStartPoint);
					finish();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException ex) {
					ex.printStackTrace();
				}
			}
		};
		timer.start();
	}

	private void getMeasures() {
		
		getWindowManager().getDefaultDisplay().getMetrics(dispMetrics);
		width = dispMetrics.widthPixels;
	    height = dispMetrics.heightPixels;
	    
	    hatImg.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
	    hatImgHeight = hatImg.getMeasuredHeight();
	    logoImg.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
	    logoImgHeight = logoImg.getMeasuredHeight();
	    bearImg.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
	    bearImgHeight = bearImg.getMeasuredHeight();
	    bearImgWidth = bearImg.getMeasuredWidth();
	    
	    bearTopEdge = height/2-bearImgHeight/2;
	    bearBottomEdge = bearImgHeight/2+height/2;
		
	    bearImg.setAlpha((float) 0);
	    hatImg.setY(-hatImgHeight);
	    logoImg.setY(height);
	}

	private void animateSplash() {
		
		PropertyValuesHolder ichTwo = PropertyValuesHolder.ofFloat("alpha", 1);
		PropertyValuesHolder ichThree = PropertyValuesHolder.ofFloat("y", -hatImgHeight, bearTopEdge-hatImgHeight+bearImgHeight/5);
		PropertyValuesHolder ichFour = PropertyValuesHolder.ofFloat("y", height, bearBottomEdge-bearImgHeight/8);
		
		ObjectAnimator apperanceBearImg = ObjectAnimator.ofPropertyValuesHolder(bearImg, ichTwo);
		ObjectAnimator apperanceHatImg = ObjectAnimator.ofPropertyValuesHolder(hatImg, ichThree);
		ObjectAnimator apperanceLogoImg = ObjectAnimator.ofPropertyValuesHolder(logoImg, ichFour);

		apperanceBearImg.setDuration(1000);
		apperanceHatImg.setDuration(1000);
		apperanceLogoImg.setDuration(1500);
		apperanceBearImg.setStartDelay(500);
		apperanceHatImg.setStartDelay(500);
		apperanceLogoImg.setStartDelay(500);
		apperanceBearImg.start();
		apperanceHatImg.start();
		apperanceLogoImg.start();
		
	}

	
}
