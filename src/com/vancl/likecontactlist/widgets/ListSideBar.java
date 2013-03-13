package com.vancl.likecontactlist.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ListSideBar extends View {
	//�Ƿ���
	private boolean showBkg = false;
	// 26����ĸ
    public static String[] sections = { "#", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" };
	
    //��������Ƿ����ӿ�
    OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    //ѡ���ֵ
    int choose = -1;
    //����
    private Paint paint = new Paint();
    private Paint prePaint;
	public ListSideBar(Context context) {
		super(context);
	}

	public ListSideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ListSideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	/**
     * ��д�������
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //�����崦�ڵ��״̬�ͽ����ı���ɫ����Ϊ��ɫ
        if (showBkg) {
            canvas.drawColor(Color.parseColor("#40000000"));
        }
        //��ã֣����ĸ�
        int height = getHeight();
          //��ã֣����Ŀ�
        int width = getWidth();
        //����ó�ÿһ�������ŵĸ߶�
        int singleHeight = height / sections.length;
        for (int i = 0; i < sections.length; i++) {
            //���þ��
            paint.setAntiAlias(true);
            //���������С
            paint.setTextSize(15);
              //����������26����ĸ�е�����һ����Ⱦ�
            if (i == choose) {
                //���Ƶ�����������ɫΪ��ɫ
                paint.setColor(Color.parseColor("#3399ff"));
                paint.setFakeBoldText(true);
            }
            //�õ������X����
            float xPos = width / 2 - paint.measureText(sections[i]) / 2;
            //�õ������Y����
            float yPos = singleHeight * i + singleHeight+3;
            //��������Ƶ������
            canvas.drawText(sections[i], xPos, yPos, paint);
             //��ԭ����
            paint.reset();
        }
 
    }
 
    /**
     * ����¼�
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //�õ������״̬
        final int action = event.getAction();
        //�����Y����
        final float y = event.getY();
         
        final int oldChoose = choose;
        //����
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        //�õ���ǰ��ֵ
        final int c = (int) (y / getHeight() * sections.length);
        //���ݵ����״̬��ͬ������ͬ�Ĵ���
        switch (action) {
        //�����Ѿ���ʼ
        case MotionEvent.ACTION_DOWN:
            //����������Ϊtrue
            showBkg = true;
            if (oldChoose != c) {
                if (c > 0 && c < sections.length) {
                	if(listener != null){
                		 //����ǰ�����ֵ�󶨼���  ��������ڱ�ҳ���������ǽӿڡ�ʵ�ʵ�������MainActiv�С�Ҳ����˵���ǵ�������ӿڻ�ִ��MainActivtiy�ķ���
                        listener.onTouchingLetterChanged(sections[c]);
                	}
                    choose = c;
                }
            }
            //ˢ�½���
            invalidate();
            break;
            //�ɿ�Ϊ��ɵ��
        case MotionEvent.ACTION_MOVE:
            if (oldChoose != c) {
                if (c > 0 && c < sections.length) {
                	if(listener != null){
                		listener.onTouchingLetterChanged(sections[c]);
                	}
                    choose = c;
                    invalidate();
                }
            }
            break;
        //����ɿ�  ��ԭ���� ��ˢ�½���
        case MotionEvent.ACTION_UP:
        	if(listener != null){
        		listener.onTouchOver();
            }
            showBkg = false;
            choose = -1;
            invalidate();
            break;
        }
        return true;
    }
 
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
 
    /**
     * ���⹫���ķ���
     * 
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }
 
    /**
     * �ӿ�
     * 
     * @author coder
     * 
     */
    public interface OnTouchingLetterChangedListener {
        public void onTouchingLetterChanged(String s);
        public void onTouchOver();
    }
}
