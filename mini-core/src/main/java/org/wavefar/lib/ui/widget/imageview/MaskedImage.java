package org.wavefar.lib.ui.widget.imageview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import org.wavefar.lib.utils.LogUtil;

/**
 * 遮罩ImageView基类
 * @author summer
 */
public abstract class MaskedImage extends AppCompatImageView {
	private static final Xfermode MASK_XFERMODE;
	private Bitmap mask;
	private Paint paint;
	private static String TAG="MaskedImage";
	static {
		PorterDuff.Mode localMode = PorterDuff.Mode.DST_IN;
		MASK_XFERMODE = new PorterDuffXfermode(localMode);
	}

	public MaskedImage(Context paramContext) {
		super(paramContext);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
	}

	public MaskedImage(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
	}

	/**
	 * 创建遮罩
	 * @return
	 */
	public abstract Bitmap createMask();

	@Override
	protected void onDraw(Canvas paramCanvas) {
		Drawable localDrawable = getDrawable();
		if (localDrawable == null) {
			return;
		}
		try {
			if (this.paint == null) {
				this.paint = new Paint();
				this.paint.setFilterBitmap(false);
				Paint localPaint2 = this.paint;
				localPaint2.setXfermode(MASK_XFERMODE);
			}
			float f1 = getWidth();
			float f2 = getHeight();
			@SuppressLint("WrongConstant") int i = paramCanvas.saveLayer(0.0F, 0.0F, f1, f2, null, 31);
			int j = getWidth();
			int k = getHeight();
			localDrawable.setBounds(0, 0, j, k);
			localDrawable.draw(paramCanvas);
			if ((this.mask == null) || (this.mask.isRecycled())) {
				this.mask = createMask();
			}
			Bitmap localBitmap2 = this.mask;
			Paint localPaint3 = this.paint;
			paramCanvas.drawBitmap(localBitmap2, 0.0F, 0.0F, localPaint3);
			paramCanvas.restoreToCount(i);
		} catch (Exception e) {
				LogUtil.e(TAG,e.toString());
		}
	}
}
