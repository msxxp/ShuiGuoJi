package h.h.shuiguoji;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Style;


public  class JCardBuilder
{
	public JCardBuilder(Context context)
	{
		ints[0] = Color.RED;
		ints[1] = Color.BLUE;
		ints[2] = Color.CYAN;
		ints[3] = Color.DKGRAY;
		ints[4] = Color.GRAY;
		ints[5] = Color.YELLOW;
		ints[6] = Color.LTGRAY;
		ints[7] = Color.MAGENTA;
		ints[8] = Color.RED;
		ints[9] = Color.GREEN; 
		ints[10] = Color.RED;
	}
	
	Random rand = new Random();
	 
	int[] ints = new int[11];
	public void OnDraw(byte cardData, Canvas cvs,Rect rc)
	{
		  Paint mPaint = new Paint();
		  mPaint.setColor(ints[cardData]);
		  mPaint.setStyle(Style.FILL); //…Ë÷√ÃÓ≥‰  
		  cvs.drawRect(rc, mPaint);
		  cvs.drawText(Integer.toString(cardData), rc.left, rc.top, mPaint);
		  
	}
}