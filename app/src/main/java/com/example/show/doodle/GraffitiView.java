package com.example.show.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
	private static final int iPaintWidth = 20;

	private int mPaintWidth = iPaintWidth;

	private List<DrawPath> drawPathList;
	private List<DrawPath> redoDrawPathList;

	private int mColor = Color.BLACK;

	private float mPressure = 0;

	public GraffitiView(Context context) {
		super(context, null);
		drawPathList = new ArrayList<>();
		redoDrawPathList = new ArrayList<>();
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
		paint.setStrokeWidth(mPaintWidth);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(mColor);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if (drawPathList != null && !drawPathList.isEmpty()) {
			for (DrawPath draw : drawPathList) {
				canvas.drawPath(draw.path, draw.paint);
			}
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				downX = event.getX();
				downY = event.getY();
				path = new Path();
				path.moveTo(downX, downY);

				DrawPath drawPath = new DrawPath();
				drawPath.path = path;
				drawPath.paint = paint;

				drawPathList.add(drawPath);
				invalidate();
				initPaint();
				tmpX = downX;
				tmpY = downY;
			}
			case MotionEvent.ACTION_MOVE: {
				float mvX = event.getX();
				float mvY = event.getY();

				path = new Path();
				path.moveTo(mvX, mvY);

				path.quadTo(tmpX, tmpY, mvX, mvY);

				DrawPath drawPath = new DrawPath();
				drawPath.path = path;
				drawPath.paint = paint;

				if (event.getPressure() > mPressure) {
					mPaintWidth+=2;
					paint.setStrokeWidth(mPaintWidth);
				} else if (event.getPressure() < mPressure) {
					mPaintWidth-=2;
					paint.setStrokeWidth(mPaintWidth);
				}

				drawPathList.add(drawPath);

				invalidate();
				initPaint();
				tmpX = mvX;
				tmpY = mvY;
				break;
			}
			case MotionEvent.ACTION_UP: {
				mPaintWidth = iPaintWidth;
				initPaint();
				break;
			}
		}
		return true;
	}

	public void undo() {

		if (drawPathList != null && drawPathList.size() > 0) {
			redoDrawPathList.add(drawPathList.remove(drawPathList.size() - 1));
			invalidate();
		}

	}

	public void redo() {
		if (redoDrawPathList != null && redoDrawPathList.size() > 0) {
			drawPathList.add(redoDrawPathList.remove(redoDrawPathList.size() - 1));
			invalidate();
		}
	}

	public void resetPaintColor(int color) {
		mColor = color;
		paint.setColor(color);
	}

	public void eraser() {
		paint.setColor(Color.WHITE);
		paint.setStrokeWidth(mPaintWidth + 6);
	}
}
