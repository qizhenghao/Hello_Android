package com.bruce.android.knowledges.costomviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.bruce.android.knowledges.R;

import java.util.ArrayList;
import java.util.List;

public class NewsfeedShareMultiImageView extends View {
	private List<String> mUrls;
	private List<OnClickListener> mListeners;
	private ArrayList<Bitmap> mBitmaps = new ArrayList<Bitmap>();
	private ArrayList<RectF> mRects = new ArrayList<RectF>();
	private Rect mSrcRect = new Rect();
	private Rect mCountRect = new Rect();
	private Paint mImagePaint;
	private TextPaint mTextPaint;
	private int mWidth;
	private int mSpacing;
	private final int MAX_COUNT = 4;
	private long mTouchTime;
	private int x;
	private int y;
	private Bitmap mCountBitmap;
	private String mCount ;
	private float mCountOriginY;
	private float mCountOriginX;
    private static Bitmap sCountBitmap;
    private float scale = 1.0f;
	private Context mContext;

	public NewsfeedShareMultiImageView(Context context) {
		this(context, null, 0);
	}

	public NewsfeedShareMultiImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public NewsfeedShareMultiImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context, attrs);
	}

    private void initView(Context context, AttributeSet attrs) {
		mContext = context;
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MultiImageView);

        int resourceId = a.getResourceId(R.styleable.MultiImageView_src_, 0);

        if (resourceId != 0) {
            mCountBitmap = BitmapFactory.decodeResource(context.getResources(), resourceId);
        } else {
            if (sCountBitmap == null) {
                sCountBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.newsfeed_image_icon);
            }
            mCountBitmap = sCountBitmap;
        }
        this.scale = a.getFloat(R.styleable.MultiImageView_iconScale, 1.0f);
        mWidth = a.getDimensionPixelSize(R.styleable.MultiImageView_width, (int) context.getResources()
                .getDimension(R.dimen.vc_0_0_1_newsfeed_share_gray_image_size));
        mImagePaint = new Paint();
        mImagePaint.setAntiAlias(true);
        mImagePaint.setColor(Color.argb(255, 204, 204, 204));
        mSpacing = (int) (context.getResources().getDisplayMetrics().density * 1);
        mTextPaint = new TextPaint();
        mTextPaint.setAntiAlias(true);
        int textSize = a.getDimensionPixelSize(R.styleable.MultiImageView_countTextSize,
				(int) (context.getResources().getDisplayMetrics().density * 10));
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        a.recycle();
    }

    @Override
	public boolean onTouchEvent(MotionEvent event) {
        if (mListeners == null || mListeners.isEmpty()) {
            return super.onTouchEvent(event);
        }
        switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mTouchTime = System.currentTimeMillis();
			x = (int) event.getX();
			y = (int) event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if (System.currentTimeMillis() - mTouchTime < 128) {
				for (int i = 0; i < mRects.size(); i++) {
					RectF rectF = mRects.get(i);
                    if (i < mListeners.size()) {
                        OnClickListener listener = mListeners.get(i);
                        if (rectF != null && listener != null && rectF.contains(x, y)) {
                            listener.onClick(this);
                            break;
                        }
                    }
                }
			}
			break;
		}
		return true;
	}
	public void setImages(List<String> urls,List<OnClickListener> listeners,int count){
		if (urls == null) {
			return;
		}
		this.mUrls = urls;
		this.mListeners = listeners;
		if (count>999) {
			this.mCount = "999+张";
            mTextPaint.setTextScaleX(0.8f);
        }else if (count>1){
			this.mCount = count+"张";
            mTextPaint.setTextScaleX(1.0f);
		}else{
            this.mCount="";
        }
		initRects();
		loadImages(urls);
	}

	private void initRects() {
		mRects.clear();
		RectF rect = null;
		float half = (mWidth-mSpacing)/2.0f;
		if (mUrls.size() == 1) {
			rect = new RectF(0, 0, mWidth, mWidth);
			mRects.add(rect);
		}else if (mUrls.size() == 2) {
			rect = new RectF(0, 0, half, mWidth);
			mRects.add(rect);
			rect = new RectF(half+mSpacing, 0, mWidth, mWidth);
			mRects.add(rect);
		}else if (mUrls.size() == 3) {
			rect = new RectF(0, 0, half, half);
			mRects.add(rect);
			rect = new RectF(half+mSpacing, 0, mWidth, half);
			mRects.add(rect);
			
			rect = new RectF(0, half+mSpacing, mWidth, mWidth);
			mRects.add(rect);
			
		}else if (mUrls.size() >= 4) {
			rect = new RectF(0, 0, half, half);
			mRects.add(rect);
			rect = new RectF(half+mSpacing, 0, mWidth, half);
			mRects.add(rect);
			
			rect = new RectF(0, half+mSpacing, half, mWidth);
			mRects.add(rect);
			
			rect = new RectF(half+mSpacing, half+mSpacing, mWidth, mWidth);
			mRects.add(rect);
		}
        int countHeight = (int) (mCountBitmap.getHeight()*scale);
        int countWidth = (int) (mCountBitmap.getWidth()*scale);
//        int top = (int) ((half-countHeight)/2);
        int top = (int) ((mContext.getResources().getDisplayMetrics().density * 10)*scale);
        mCountRect.set(mWidth -countWidth, top, mWidth, top + countHeight);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float  fontHeight = fontMetrics.bottom - fontMetrics.top;
		mCountOriginY = mCountRect.top+mCountRect.height()/2.0f + fontHeight/2.0f - fontMetrics.bottom;
		mCountOriginX =mCountRect.centerX();
	}

	private void loadImages(final List<String> urls){
		mBitmaps.clear();
		int count = Math.min(MAX_COUNT, urls.size());

		mBitmaps.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.welcome_no_medicine_iv));
		mBitmaps.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.welcome_no_medicine_iv));
		mBitmaps.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.welcome_no_medicine_iv));
		mBitmaps.add(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.welcome_no_medicine_iv));
		invalidate();
//		for (int i = 0; i <count; i++) {
//            mBitmaps.add(null);
//			String url = urls.get(i);
//            if (!TextUtils.isEmpty(url)){
//                final int index = i;
//                RecyclingImageLoader.loadImage(null, url, null, new BaseImageLoadingListener(){
//                    @Override
//                    public void onLoadingComplete(String imageUri, RecyclingImageView imageView, LoadOptions options,
//                                                  Drawable loadedImage, boolean sync) {
//                        if (urls == mUrls && loadedImage instanceof BitmapDrawable) {
//                            if (index >= 0 && index < mBitmaps.size()) {
//                                mBitmaps.set(index,((BitmapDrawable) loadedImage).getBitmap());
//                                invalidate();
//                            }
//                        }
//                    }
//                });
//            }
//		}
	}
	@Override
	protected void onDraw(Canvas canvas) {
		int size = mBitmaps.size();
		for (int i = 0; i < size; i++) {
			Bitmap bitmap = mBitmaps.get(i);
			if (bitmap != null && !bitmap.isRecycled()) {
				if (size == 2) {
					mSrcRect.set((int)( bitmap.getWidth() / 4.0f), 0, (int) ((bitmap.getWidth()*3 / 4.0f)), bitmap.getHeight());
					canvas.drawBitmap(mBitmaps.get(i), mSrcRect, mRects.get(i), mImagePaint);
				} else if (size == 3 && i == 2) {
					mSrcRect.set(0, (int)(bitmap.getHeight()/4.0f), bitmap.getWidth(), (int)(bitmap.getHeight()*3 / 4.0f));
					canvas.drawBitmap(mBitmaps.get(i), mSrcRect, mRects.get(i), mImagePaint);
				} else {
					canvas.drawBitmap(mBitmaps.get(i), null, mRects.get(i), mImagePaint);
				}
			}else{
                canvas.drawRect(mRects.get(i),mImagePaint);
            }
		}
		if (!TextUtils.isEmpty(mCount)) {
			canvas.drawBitmap(mCountBitmap, null, mCountRect, mTextPaint);
			canvas.drawText(mCount, mCountOriginX, mCountOriginY, mTextPaint);
		}
	}
}
