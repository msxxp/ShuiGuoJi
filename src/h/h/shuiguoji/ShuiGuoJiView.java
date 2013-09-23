package h.h.shuiguoji; 


import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context; 
import android.graphics.Canvas; 
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet; 
import android.util.Log;
import android.view.MotionEvent;
import android.view.View; 
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;



public class ShuiGuoJiView extends View {
	


  public Sprite []sprites = new Sprite[24];// = new ArrayList<Sprite>();
  public int []bmpID = {R.drawable.houge,R.drawable.dapingguo,
		  R.drawable.xiaojuzi,R.drawable.dajuzi,
		  R.drawable.dalingdang,R.drawable.xiao77,
		  R.drawable.da77,R.drawable.dapingguo,
		  R.drawable.xiaomangguo,R.drawable.damangguo,
		  R.drawable.dashuangxing,R.drawable.xiaoshuangxing,
		  R.drawable.houge,R.drawable.dapingguo,
		  R.drawable.xiaolingdang,R.drawable.dajuzi,
		  R.drawable.dalingdang,R.drawable.xiaowang,
		  R.drawable.dawang,R.drawable.dapingguo,
		  R.drawable.xiaopingguo,R.drawable.damangguo,
		  R.drawable.daxigua,R.drawable.xiaoxigua
		  };
  public static final int bmpCount = 24;
  public Rect []rects = new Rect[bmpCount];
  public Rect Crect;
  public int randNumber = 0;
  public ImageButton start;
  public Timer timer;
  public TimerTask task;
  public  Handler handler;
  public Random random = new Random();
  public int targetIt;
  
  //public List<String>  ids = new ArrayList<String>();
  public String []ids = new String[bmpCount];
  
  public ShuiGuoJiView(Context context) { 
    super(context); 
    targetIt = 0;
    InitSprites( context );
  } 
  public ShuiGuoJiView(Context context,AttributeSet attr) 
  { 
    super(context,attr); 
    targetIt = 0;
    InitSprites( context );
 } 
  
  public void InitTimer()
  {
	  if ( randNumber > 0 ) return;
	  
	  timer = new Timer(true);
	  randNumber = Math.abs(random.nextInt()) % 50 ;
	  randNumber += 12;
	  Log.d("xxxxxxxxxxxxx"+Integer.toString(randNumber),"");
	  
	  InitHandler();
	  InitTimerTask();
	timer.schedule(task,0, 100);
  }
  public void InitTimerTask()
  {
	  task = new TimerTask(){
	    	public void run() {
	    		Message message = new Message();
	    		message.what = 1;
	    		handler.sendMessage(message);
	    		}
	    	};
  }
  
  View thisView;
  public void InitHandler()
  {
	  thisView = this;
	  handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what)
			{
			case 1:
			{
				//Log.d("sds"+Integer.toString(randNumber), "sdsf:" + Integer.toString( random.nextInt() ));
				if(randNumber>0)
	    		{
	    			randNumber -= 1;
	    		}
	    		if(randNumber<1)
	    		{
	    			timer.cancel();
	    		}
	    		targetIt++;
	    		targetIt %= bmpCount;
	    		Log.d(Integer.toString(targetIt),"'''''''''");
				thisView.invalidate(HeBing(rects[targetIt],rects[(targetIt+bmpCount-1)%bmpCount]));
				//Log.d(Integer.toString((targetIt+bmpCount-1)%bmpCount),
				//"1111111");
			}
			break;
			}
			
			super.handleMessage(msg);
			}
		};
	}
  
  public Rect HeBing(Rect a,Rect b)
  {
	  Rect rc = new Rect();
	  rc.left = Math.min(a.left,b.left);
	  rc.top = Math.min(a.top,b.top);
	  rc.right = Math.max(a.right,b.right);
	  rc.bottom = Math.max(a.bottom,b.bottom);
	  return rc;
  }

  private void InitSprites(Context context)
  {
	  Rect outRect = new Rect();
	  Crect = outRect;
	  this.getWindowVisibleDisplayFrame(outRect);
	  
	  int w = outRect.width() / 7;
	  int h = outRect.height() / 11;
	  
	  rects[0] = new Rect(6*w,4*h,7*w,5*h);
	  rects[1] = new Rect(6*w,5*h,7*w,6*h);
	  rects[2] = new Rect(6*w,6*h,7*w,7*h);
	  rects[3] = new Rect(6*w,7*h,7*w,8*h);
	  
	  rects[4] = new Rect(5*w,7*h,6*w,8*h);
	  rects[5] = new Rect(4*w,7*h,5*w,8*h);
	  rects[6] = new Rect(3*w,7*h,4*w,8*h);
	  rects[7] = new Rect(2*w,7*h,3*w,8*h);
	  rects[8] = new Rect(w,7*h,2*w,8*h);
	  rects[9] = new Rect(0,7*h,w,8*h);
	  

	  rects[10] = new Rect(0,6*h,w,7*h);
	  rects[11] = new Rect(0,5*h,w,6*h);
	  rects[12] = new Rect(0,4*h,w,5*h);
	  rects[13] = new Rect(0,3*h,w,4*h);
	  rects[14] = new Rect(0,2*h,w,3*h);
	  rects[15] = new Rect(0,h,w,2*h);
	  

	  rects[16] = new Rect(w,h,2*w,2*h);
	  rects[17] = new Rect(2*w,h,3*w,2*h);
	  rects[18] = new Rect(3*w,h,4*w,2*h);
	  rects[19] = new Rect(4*w,h,5*w,2*h);
	  rects[20] = new Rect(5*w,h,6*w,2*h);
	  rects[21] = new Rect(6*w,h,7*w,2*h);
	  

	  rects[22] = new Rect(6*w,2*h,7*w,3*h);
	  rects[23] = new Rect(6*w,3*h,7*w,4*h);
	
	  for(int i = 0; i < bmpCount; i++)
	  {
		  ids[i] = Integer.toString(bmpID[i]);
	  }
	  
	  for(int i = 0;i < bmpCount; i++)
	  {
		  sprites[i] = new Sprite( context.getResources(), Integer.parseInt(ids[i]),
		             rects[i]
		  );
	  }
  }
  
  @Override 
  protected void onDraw(Canvas canvas)
  {
	  super.onDraw(canvas); 
	  Paint mPaint = new Paint();
	  mPaint.setColor(Color.BLACK);
	  mPaint.setStyle(Style.FILL); //ÉèÖÃÌî³ä   
      canvas.drawRect(Crect, mPaint);
      
      mPaint.setColor(Color.WHITE);
      canvas.drawRect(rects[targetIt], mPaint);
      
	  //for(Sprite sprite : sprites )
	  for ( int i = 0;i < bmpCount;i++)
	  {
		  sprites[i].Draw(canvas);
	  } 
  } 
} 