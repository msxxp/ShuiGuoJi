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
	
	public static interface OnCardClickEvent // ��̬�ӿڣ����Ը��ⲿ��������.������ CardGroupView.OnCardClickEvent �༴�ɡ�
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
    public boolean onTouchEvent(MotionEvent event) { // ����ת�����Ϊ.
        int action = event.getAction();
        int mPosX = (int) event.getX();
        int mPosY = (int) event.getY();
        Point pt = new Point();
        pt.x = mPosX;
        pt.y = mPosY;
        
        switch (action) {
        // �������µ��¼�
        case MotionEvent.ACTION_UP:
            Log.v("test", "ACTION_UP");
            OnClick(pt);

			this.invalidate();
            break;
        }
        return true;
	}
	
	// ����ص�������,
	public class OnClickListener implements OnCardClickEvent
	{
		@Override
		public void DoClicked(View sender, byte cardData,boolean isSelect) // �����ɸ�����Ҫ�޸�.
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
	
	// ��ʼ����������Ҳ������Ӻ���ȥ����.
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

	//���ü��
	
	//���캯��
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

	// �ڹ��캯���е�����Ч����Ϊ����ʱ����С��û��ȷ����һ������ͼ����� 
	private Rect GetCurrentSelfArea()
	{
		Rect newRect = new Rect(0,0,this.getWidth(),this.getHeight());
		return newRect;
	}
	
	// ���ȷ����С�������ٹ��캯���г�ʼһ�Ρ���ô�������� OnDraw ��ÿ�θ���.
	private void InitCardsArea(Rect totalArea)
	{
		Rect rc = totalArea;
		 
		 int cardHeight = (int) (rc.height() * ( 1.0 / (1 + kTanChu)));
		 int cardWidth = (int) (kKuan * cardHeight);
		 int cardGap = (int) (kGap * cardWidth);
		 int cardTanChu = (int) (rc.height() * ( kTanChu * 1.0 / (1 + kTanChu)));
		 
		 int leftOffset = 0;
		 switch( alignment ) // ���ݶ��뷽ʽ������������Ƶ�left ƫ��ֵ.
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
	
	// �����յ������Ϊ��ʱ���б����������������档
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
	
	// ��ͼ����װ����.�������Ϊ�ӿڣ����Ը����.
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