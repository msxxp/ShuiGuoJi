package h.h.shuiguoji;
 
import android.graphics.Canvas; 
import android.graphics.Rect; 


public  interface ICardBuilder
{
	public void OnDraw(byte cardData, Canvas cvs,Rect rc);
	
}