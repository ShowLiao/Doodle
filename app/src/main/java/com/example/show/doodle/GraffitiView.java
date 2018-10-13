package com.example.show.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GraffitiView extends View {

	private Paint paint;
	private Path path;
	private float downX, downY;
	private float tmpX, tmpY;

	public GraffitiView(Context context) {
		super(context, null);
		init();
	}

	public GraffitiView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs, 0);
		init();
	}

	public GraffitiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		paint = new Paint();
		paint.setAntiAlias(true);  //鋸齒
		paint.setStrokeWidth(10);
		paint.setStyle(Paint.Style.STROKE);
//		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		path = new Path();
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (path != null) {
			canvas.drawPath(path, paint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				path.moveTo(downX, downY);

				invalidate();

				tmpX = downX;
				tmpY = downY;
				break;

			case MotionEvent.ACTION_MOVE:
				float mvX = event.getX();
				float mvY = event.getY();
				path.quadTo(tmpX, tmpY, mvX, mvY);

				invalidate();

				tmpX = mvX;
				tmpY = mvY;
				break;
		}
		return true;
	}
}
