package com.example.show.doodle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;

class DrawPath {
	Paint paint;
	Path path;
}

public class GraffitiView extends View {

	private Paint paint;
	private Path path;
	private float downX, downY;
	private float tmpX, tmpY;
	private int mPaintWidth = 10;

	private Stack<DrawPath> drawPathStack;
	private Stack<DrawPath> redoDrawPathStack;

	private int mColor = Color.BLACK;

	public GraffitiView(Context context) {
		super(context, null);
		drawPathStack = new Stack<>();
		redoDrawPathStack = new Stack<>();
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

		if (drawPathStack != null && !drawPathStack.isEmpty()) {
			for (DrawPath draw : drawPathStack) {
				canvas.drawPath(draw.path, draw.paint);
			}
		}
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

				drawPathStack.push(drawPath);
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
				mPaintWidth = 10;
				initPaint();
				break;
		}
		return true;
	}

	public void undo() {

		if (drawPathStack != null && drawPathStack.size() > 0) {
			redoDrawPathStack.push(drawPathStack.pop());
			invalidate();
		}

	}

	public void redo() {
		if (redoDrawPathStack != null && !redoDrawPathStack.isEmpty()) {
			drawPathStack.push(redoDrawPathStack.pop());
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
