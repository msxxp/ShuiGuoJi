package h.h.shuiguoji;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas; 
import android.graphics.Matrix;
import android.graphics.Rect; 


public class Sprite{
	  private Rect area;     // 区域
	  private Resources res; // 资源容器  ,暂时先这样吧 // TODO:  
	  private int imageID;   // 图片ID
	  private Bitmap bitmap;
	
	
	  public Sprite(Resources res,int imageID,Rect area)
	  {
		  this.imageID = imageID;
		  this.area = area;
		  this.res = res;
		  InitBMP();
	  }
	  private void InitBMP()
	  {
		  Bitmap bmp = BitmapFactory.decodeResource(res, this.imageID);
		  if ( bmp == null ) return;
			
		  Matrix matrix = new Matrix(); 
		  float sX = 1.0f * area.width() / bmp.getWidth();
		  float sY = 1.0f * area.height() / bmp.getHeight();
		  matrix.postScale(sX, sY);
		  
		  bitmap = Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
	  }
	  public void Draw(Canvas canvas)
	  {
		  if ( bitmap == null ) return;
		  
		  canvas.drawBitmap(bitmap,area.left, area.top, null);
	  } 
  }