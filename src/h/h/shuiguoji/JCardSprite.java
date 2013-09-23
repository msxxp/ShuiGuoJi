package h.h.shuiguoji;

import android.graphics.Canvas;
import android.graphics.Rect;


public class JCardSprite
{
	private byte cardValue;
	
	public JCardSprite(byte cardData)
	{
		cardValue = cardData;
	}
	
	public void OnDraw(Canvas cvs,Rect rc)
	{
		//JCardBuilder.OnDraw(cardValue, cvs, rc);
	}
}