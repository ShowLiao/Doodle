package com.example.show.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

class DrawPath {
	Paint paint;
	Path path;
}

public class GraffitiView extends View {

	private Paint paint;
	private Path path;
	private float downX, downY;
	private float tmpX, tmpY;

	private List<DrawPath> drawPathList;

	public GraffitiView(Context context) {
		super(context, null);
		drawPathList = new ArrayList<>();
		initPaint();
	}

//	public GraffitiView(Context context, @Nullable AttributeSet attrs) {
//		super(context, attrs, 0);
//		initPaint();
//	}

//	public GraffitiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//		super(context, attrs, defStyleAttr);
//		initPaint();
//	}

	private void initPaint() {
		paint = new Paint();
		paint.setAntiAlias(true);  //鋸齒
		paint.setStrokeWidth(10);
		paint.setStyle(Paint.Style.STROKE);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (drawPathList != null && !drawPathList.isEmpty()) {
			for (DrawPath draw : drawPathList) {
				canvas.drawPath(draw.path, draw.paint);
			}
		}

		Log.e("onDraw", "lllll");
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				downX = event.getX();
				downY = event.getY();
				path = new Path();
				path.moveTo(downX, downY);

				DrawPath drawPath = new DrawPath();
				drawPath.path = path;
				drawPath.paint = paint;

				drawPathList.add(drawPath);
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

			case MotionEvent.ACTION_UP:
				invalidate();
				break;
		}
		return true;
	}

	public void undo() {

		if (drawPathList != null && drawPathList.size() > 0) {
			drawPathList.remove(drawPathList.size() - 1);
			invalidate();
		}

	}
}
