package com.bruce.android.knowledges.costomviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.bruce.android.knowledges.R;


/**
 * Created by VincentChen on 2016/7/19.
 *
 * ImageView （顶部圆角，底部直角）
 */
public class TopRoundedImageView extends ImageView {

    private final int DEFAULT_CORNER_RADIUL = 8;

    private int mWidth;
    private int mHeight;
    private int mRoundConer;
    private Drawable mDrawable;
    private Bitmap mBitmap;

    private Paint mPaint = new Paint();
    private BitmapShader mBitmapShader;
    private Matrix matrix = new Matrix();

    int onDrawTimes = 0;

    public TopRoundedImageView(Context context) {
        this(context, null, 0);
    }

    public TopRoundedImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopRoundedImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mWidth = getWidth();
        mHeight = getHeight();
        mRoundConer = DEFAULT_CORNER_RADIUL;

        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopRoundedImageView);
        if (array != null) {
            mRoundConer = (int) array.getDimension(R.styleable.TopRoundedImageView_top_corner_radius, 0);
            array.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        try {
            Log.d("Bruce", "onDrawTimes = " + ++onDrawTimes);
            mDrawable = getDrawable();
            mBitmap = drawableToBitmap(getDrawable());

            mBitmapShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            float scale = calculateScale(mDrawable);
            matrix.setScale(scale, scale); // set scale
            mBitmapShader.setLocalMatrix(matrix);

            mPaint.setAntiAlias(true);
            mPaint.setShader(mBitmapShader);
            // draw top round
            RectF rect = new RectF(0, 0, getWidth(), getHeight());
            RectF rect1 = new RectF(0, mRoundConer, getWidth(), getHeight());
            canvas.drawRoundRect(rect, mRoundConer, mRoundConer, mPaint);
            canvas.drawRect(rect1, mPaint);
            // set null
            if (mBitmap != null && !mBitmap.isRecycled()) {
                mBitmap = null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            super.onDraw(canvas);
        }
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    private float calculateScale(Drawable drawable) {
        if (drawable == null) {
            return 1;
        }
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        float scaleWidth = (float) (getWidth() * 1.0 / width);
        float scaleHeight = (float) (getHeight() * 1.0 / height);
        return Math.max(scaleWidth, scaleHeight);
    }


    private Bitmap createTopRoundBitmap(Bitmap bitmap) {
        try {
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            Bitmap target=null;
            try {
                target = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
            } catch (OutOfMemoryError ex){
                ex.printStackTrace();
            }
            Canvas canvas = new Canvas(target);

            RectF rectF = new RectF(0, 0, mWidth, mHeight);
            canvas.drawRoundRect(rectF, mRoundConer, mRoundConer, paint);

            RectF rectFTemp = new RectF(0, mRoundConer, mWidth, mHeight);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
            canvas.drawRect(rectFTemp, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, 0, 0, paint);
            return target;
        } catch (Exception ex) {
            ex.printStackTrace();
            return bitmap;
        }
    }
}
