package h.h.shuiguoji;

import h.h.shuiguoji.util.SystemUiHider;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class ShuiGuoJi extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setFullScreen();
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.testcards );//setContentView( new CardGroupView(this));
		//this.findViewById(R.layout.testcards).setBackgroundColor(Color.TRANSPARENT);
		return;
		
		/*
		setContentView(R.layout.activity_shui_guo_ji );//new ShuiGuoJiView(this));
		
		
		button = (ImageButton)this.findViewById(R.id.imagebutton1);
		activity = this;
		button.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v) {
	    		shuiGuojiView = ( ShuiGuoJiView )activity.findViewById(R.id.shuiguojiview);
	    		
	    		shuiGuojiView.InitTimer();
	    	}
	    });
	    */
	}
	
	ImageButton button;
	ShuiGuoJiView shuiGuojiView;
	ShuiGuoJi     activity;
	
	public void setFullScreen()

	{

	requestWindowFeature(Window.FEATURE_NO_TITLE);

	getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

	WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}
}
