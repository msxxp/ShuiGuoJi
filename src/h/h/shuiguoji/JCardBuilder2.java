package h.h.shuiguoji;
 

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas; 
import android.graphics.Matrix;
import android.graphics.Rect; 
import android.util.Log;


public  class JCardBuilder2
{
	public JCardBuilder2(Context context)
	{
		//cards = BitmapFactory.decodeFile("/ShuiGuoJi/assets/card.jpg");
		
		AssetManager asm = context.getAssets();

		InputStream is = null;
		try {
			 is = asm.open("card.png");
			  } catch (IOException e) {
			   Log.e("tag", e.getMessage());
			  }
		
		if ( is == null ) return;
		
		cards = BitmapFactory.decodeStream(is);
	}
	
	Bitmap cards;
	public void OnDraw(byte cardData, Canvas cvs,Rect rc)
	{
		if ( cards == null ) return;
		  cvs.drawBitmap(cards, GetACardArea(cardData), rc, null);
	}
	
	private Rect GetACardArea(byte cardData)
	{
	   byte type = (byte) (cardData>>4);
	   byte value = (byte) (cardData & 0x0f);
	   
	   int cw = 1002 / 9;
	   int ch = 1002 / 6;
	   Rect ret = new Rect();
	   ret.left = ( value - 1 ) * cw;
	   ret.right = ret.left + cw;
	   ret.top  = ( type) * ch;
	   ret.bottom = ret.top + ch;
	   return ret;
	}
}