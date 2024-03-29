package habitatfinal.habitatfinal;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

/**
 * Created by sandeep on 2015-04-02.
 */
public class EarthGLSurfaceView extends GLSurfaceView {

    private final EarthGLRenderer mRenderer;

    public EarthGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(1);

        // Set the Renderer for drawing on the GLSurfaceView
        mRenderer = new EarthGLRenderer(context);
        setRenderer(mRenderer);

        // Render the view only when there is a change in the drawing data
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        // MotionEvent reports input details from the touch screen
        // and other input controls. In this case, you are only
        // interested in events where the touch position changed.

        float x = e.getX();
        float y = e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_MOVE:

                float dx = x - mPreviousX;
                float dy = y - mPreviousY;

                // reverse direction of rotation above the mid-line
                if (y > getHeight() / 2) {
                    dx = dx * -1 ;
                }

                // reverse direction of rotation to left of the mid-line
                if (x < getWidth() / 2) {
                    dy = dy * -1 ;
                }
                requestRender();
        }

        mPreviousX = x;
        mPreviousY = y;
        return true;
    }
}
