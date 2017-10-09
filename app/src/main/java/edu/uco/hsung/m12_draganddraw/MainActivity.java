package edu.uco.hsung.m12_draganddraw;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RectangleDraw rectangleDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout frame = findViewById(R.id.view_frame);

        rectangleDraw = new RectangleDraw(getApplicationContext());
        frame.addView(rectangleDraw);

        RadioButton redButton = findViewById(R.id.radio_red);
        RadioButton blueButton = findViewById(R.id.radio_blue);
        RadioButton greenButton = findViewById(R.id.radio_green);

        RadioButtonListener listener = new RadioButtonListener();
        redButton.setOnClickListener(listener);
        greenButton.setOnClickListener(listener);
        blueButton.setOnClickListener(listener);

        final Button clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rectangleDraw.rectangles.clear();
                rectangleDraw.invalidate();
            }
        });
    }

    private class RadioButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String buttonName;
            // find which button was clicked
            switch (view.getId()) {
                case R.id.radio_red:
                    rectangleDraw.rectPaint.setColor(0x80ff0000); // Alpha, R, G, B
                    break;
                case R.id.radio_blue:
                    rectangleDraw.rectPaint.setColor(0x800000ff);
                    break;
                case R.id.radio_green:
                    rectangleDraw.rectPaint.setColor(0x8000ff00);
                    break;
            }
        }
    }

    private class RectangleDraw extends View {

        private MyRectangle currentRect;
        private List<MyRectangle> rectangles = new ArrayList<>();
        private Paint rectPaint = new Paint();
        private Paint rectBGPaint = new Paint(); // canvas background

        public RectangleDraw(Context context) {
            super(context, null);
            rectBGPaint.setColor(Color.LTGRAY);
            rectPaint.setColor(0x80aaaaaa);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            
            canvas.drawPaint(rectBGPaint);

            for (MyRectangle box : rectangles) {
                float left = Math.min(box.getOrigin().x, box.getCurrent().x);
                float right = Math.max(box.getOrigin().x, box.getCurrent().x);
                float top = Math.min(box.getOrigin().y, box.getCurrent().y);
                float bottom = Math.max(box.getOrigin().y, box.getCurrent().y);

                canvas.drawRect(left, top, right, bottom, box.getPaint());
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            PointF current = new PointF(event.getX(), event.getY());

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    // Reset drawing state
                    currentRect = new MyRectangle(current);
                    currentRect.setPaint(new Paint(rectPaint));
                    rectangles.add(currentRect);
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (currentRect != null) {
                        currentRect.setCurrent(current);
                        invalidate(); // re-draw the View
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    currentRect = null;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    currentRect = null;
                    break;
            }
            return true;
        }
    }
}
