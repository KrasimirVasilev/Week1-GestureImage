package tutorials.hackbulgaria.com.week1_gestureimage;

import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity implements View.OnTouchListener {

    public static final String TAG = "com.hackbulgaria.tutorials";
    public static int POINTER_1 = 0;
    public static int POINTER_2 = 1;

    private PointF point1 = new PointF();
    private PointF point2 = new PointF();
    private PointF middlePoint = new PointF();

    private ImageView targetImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        targetImageView = (ImageView) findViewById(R.id.targetImageView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        targetImageView.setScaleType(ImageView.ScaleType.MATRIX);
        targetImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int actionIndex = motionEvent.getActionIndex();
        int pointerId = motionEvent.getPointerId(actionIndex);

        switch (motionEvent.getActionMasked()) {

            case MotionEvent.ACTION_DOWN:

                //Get coordinates of first pointer when touch the screen
                point1.x = motionEvent.getX(POINTER_1);
                point1.y = motionEvent.getY(POINTER_1);

                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                //Get coordinates of second pointer when touch the screen
                point2.x = motionEvent.getX(POINTER_2);
                point2.y = motionEvent.getY(POINTER_2);

                //Calculate the middle point coordinate between two pointers
                middlePoint = getMiddlePoint(point1.x, point1.y, point2.x, point2.y);

                break;

            case MotionEvent.ACTION_MOVE:

                if (motionEvent.getPointerCount() == 2) {

                    //When two pointers star to move get their coordinates
                    point1.x = motionEvent.getX(POINTER_1);
                    point1.y = motionEvent.getY(POINTER_1);
                    point2.x = motionEvent.getX(POINTER_2);
                    point2.y = motionEvent.getY(POINTER_2);

                    //Calculate the new middle point coordinates
                    PointF newMiddle = getMiddlePoint(point1.x, point1.y, point2.x, point2.y);

                    //Move the image (set new coordinates)
                    targetImageView.setTranslationX(targetImageView.getTranslationX() + (newMiddle.x - middlePoint.x));
                    targetImageView.setTranslationY(targetImageView.getTranslationY() + (newMiddle.y - middlePoint.y));

                    Log.d(TAG, "Translation X : " + targetImageView.getTranslationX());
                    Log.d(TAG, "Translation Y : " + targetImageView.getTranslationY());
                }

                break;

            default:
                break;
        }

        return true;
    }

    /**
     * Calculate coordinates of center between two points
     *
     * @param x1 is x coordinate of first point
     * @param y1 is y coordinate of first point
     * @param x2 is x coordinate of second point
     * @param y2 is y coordinate of second point
     * @return
     */
    private PointF getMiddlePoint(float x1, float y1, float x2, float y2) {

        PointF middlePoint = new PointF();

        middlePoint.x = (x1 + x2) / 2.0f;
        middlePoint.y = (y1 + y2) / 2.0f;

        return middlePoint;
    }
}
