package com.clover.shakeac.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.clover.shakeac.R;

public class WelcomeActivity extends Activity implements AnimationListener {
	private Animation animation = null;
	private ImageView welcome = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		welcome = (ImageView) findViewById(R.id.welcome_image_view);
		animation = AnimationUtils.loadAnimation(this, R.anim.welcome_alpha);
		animation.setFillEnabled(true);
		animation.setFillAfter(true);
		welcome.setAnimation(animation);
		animation.setAnimationListener(this);
	}

	@Override
	public void onAnimationEnd(Animation arg0) {
		startActivity(new Intent(this, LoginActivity.class));
		overridePendingTransition(R.anim.zoom_enter, R.anim.zoom_exit);
		finish();
	}

	@Override
	public void onAnimationRepeat(Animation arg0) {
	}

	@Override
	public void onAnimationStart(Animation arg0) {
	}

}
