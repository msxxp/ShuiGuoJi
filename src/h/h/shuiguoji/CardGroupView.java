package h.h.shuiguoji;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class CardGroupView extends View // SurfaceView.thread lock draw
{

	public class CardData
	{
		public byte cardData;
		public boolean isSelect;
		public Rect rc;
		public CardData()
		{
			rc = new Rect();
		}
		
		public CardData(byte cardValue,boolean isSelected)
		{
			cardData = cardValue;
			isSelect = isSelected;
			rc = new Rect();
		}
	}
	public enum Alignment{Left, Center, Right}
	
	public static interface OnCardClickEvent // 静态接口，可以给外部派生对象.派生自 CardGroupView.OnCardClickEvent 类即可。
	{
        public void DoClicked(View sender, byte cardData,boolean isSelect);
	}
	
	private List<CardData> cardDatas = new ArrayList<CardData>();

	private List<OnCardClickEvent> onClickedEvents = new ArrayList<OnCardClickEvent>();
	
	public Alignment alignment;
	public float kTanChu;
	public float kKuan;
	public float kGap; 
	
	@Override
    public boolean onTouchEvent(MotionEvent event) { // 触摸转点击行为.
        int action = event.getAction();
        int mPosX = (int) event.getX();
        int mPosY = (int) event.getY();
        Point pt = new Point();
        pt.x = mPosX;
        pt.y = mPosY;
        
        switch (action) {
        // 触摸按下的事件
        case MotionEvent.ACTION_UP:
            Log.v("test", "ACTION_UP");
            OnClick(pt);

			this.invalidate();
            break;
        }
        return true;
	}
	
	// 点击回调处理器,
	public class OnClickListener implements OnCardClickEvent
	{
		@Override
		public void DoClicked(View sender, byte cardData,boolean isSelect) // 参数可根据需要修改.
		{
			Log.v("test", "f11111"); 
			for(int i = 0 ;i < cardDatas.size();i++)
			{
				if ( cardDatas.get(i).cardData == cardData )
				{
					cardDatas.get(i).isSelect = !cardDatas.get(i).isSelect;
					return;
				}
			}
		} 
	}
	
	// 初始化各参数，也可以另加函数去设置.
	private void InitParams(Context context)
	{
		kTanChu = 0.3f;
		kKuan = 0.6f;
		kGap = 0.3f;
		
		cardDatas.add( new CardData((byte)0x01,false));
		cardDatas.add( new CardData((byte)0x02,false));
		cardDatas.add( new CardData((byte)0x03,false));
		cardDatas.add( new CardData((byte)0x04,false));
		cardDatas.add( new CardData((byte)0x05,false));
		
		cardDatas.add( new CardData((byte)0x06,false));
		cardDatas.add( new CardData((byte)0x07,false));
		cardDatas.add( new CardData((byte)0x08,false));
		cardDatas.add( new CardData((byte)0x09,false));
		
		onClickedEvents.add( new OnClickListener() );
		//onClickedEvents.add( new OnClickListener() );
		
		//Activity act = (Activity)context;
		builder = new JCardBuilder2( context );
	}

	//设置间隔
	
	//构造函数
	public CardGroupView(Context context)
	{ 
	    super(context);
	    alignment = Alignment.Center;
	    InitParams(context);
	} 
	public CardGroupView(Context context,AttributeSet attr)
	{
		super(context,attr);
		
		alignment = Alignment.Center;
		TypedArray ta = context.obtainStyledAttributes(attr, R.styleable.CardGroupView);
		if ( ta != null )
		{
			// 0 for left,1 for centerl, 2 for right
			int align = ta.getInt(R.styleable.CardGroupView_alignment,1);
			if ( align == 0 ) alignment = Alignment.Left;
			if ( align == 2 ) alignment = Alignment.Right;
			ta.recycle();
		}
		InitParams(context); 
	}

	// 在构造函数中调用无效，因为构造时，大小还没有确定。一般在作图后调用 
	private Rect GetCurrentSelfArea()
	{
		Rect newRect = new Rect(0,0,this.getWidth(),this.getHeight());
		return newRect;
	}
	
	// 如果确定大小，可以再构造函数中初始一次。那么就无需在 OnDraw 里每次更新.
	private void InitCardsArea(Rect totalArea)
	{
		Rect rc = totalArea;
		 
		 int cardHeight = (int) (rc.height() * ( 1.0 / (1 + kTanChu)));
		 int cardWidth = (int) (kKuan * cardHeight);
		 int cardGap = (int) (kGap * cardWidth);
		 int cardTanChu = (int) (rc.height() * ( kTanChu * 1.0 / (1 + kTanChu)));
		 
		 int leftOffset = 0;
		 switch( alignment ) // 根据对齐方式，计算最左边牌的left 偏移值.
		 { 
		 case Center:
			 leftOffset = ( rc.left + rc.right ) / 2 - cardWidth / 2;
			 leftOffset = leftOffset - ( cardDatas.size() / 2 ) * cardGap;
			 if ( cardDatas.size() % 2 == 0 )
			 {
			     leftOffset = leftOffset - cardGap / 2;
			 }
			 break;
		 case Right:
			 leftOffset = rc.right - cardWidth;
			 if ( cardDatas.size() > 1)
			 {
			    leftOffset = leftOffset - ( cardDatas.size() - 1 ) * cardGap; 
			 }
			 break;
		default: // Left
			break;
		 }
		 
		 // calc the rects 
		 for(int i = 0 ;i < cardDatas.size();i++)
		 {
			 int cardTanChuTmp = cardTanChu;
			 if ( cardDatas.get(i).isSelect ) 
			 {
				 cardTanChuTmp = 0;
			 }
			 
			 Rect rcCard   = cardDatas.get(i).rc;
			 rcCard.left   = leftOffset + i * cardGap;
			 rcCard.top    = rc.top + cardTanChuTmp;
			 rcCard.right  = rcCard.left + cardWidth; 
			 rcCard.bottom = rcCard.top + cardHeight;
			 
		 }
		 return ;
	}
	
	private static boolean PtInRect( Point pt, Rect rc)
	{
		return  ( pt.x >= rc.left && pt.x <= rc.right ) &&
				( pt.y >= rc.top  && pt.y <= rc.bottom );
	}
	
	// 当接收到点击行为的时候，判别点击点在哪张牌里面。
	public void OnClick(Point pt)
	{
		for(int i = cardDatas.size() - 1 ;i >= 0 ;i--)
		{
		   if ( PtInRect( pt, cardDatas.get(i).rc ))
		   {
			   for( OnCardClickEvent clickEvent : onClickedEvents )
			   {
				   if ( clickEvent == null ) continue;
				   clickEvent.DoClicked(this, cardDatas.get(i).cardData,cardDatas.get(i).isSelect);
			   }
			   return;
		   }
		}
	}
	
	// 作图，组装工具.将其抽象为接口，可以更灵活.
	JCardBuilder2 builder;
	
	@Override 
	 protected void onDraw(Canvas canvas)
	 {
		 super.onDraw(canvas); 
		 
		 canvas.drawColor(Color.TRANSPARENT);
		 InitCardsArea( GetCurrentSelfArea());
		 
		 // draw rects
		 for(int i = 0 ;i < cardDatas.size();i++)
		 {
			 CardData data = cardDatas.get(i);
			 builder.OnDraw(data.cardData, canvas,data.rc);
		 } 
	 } 
}