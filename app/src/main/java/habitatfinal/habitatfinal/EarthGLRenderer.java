package habitatfinal.habitatfinal;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by sandeep on 2015-04-02.
 */
public class EarthGLRenderer implements GLSurfaceView.Renderer {
    private static final int AXIAL_TILT_DEGRESS = 30;

    /** Clear colour, alpha component. */
    private static final float CLEAR_RED = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_GREEN = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_BLUE = 0.0f;

    /** Clear colour, alpha component. */
    private static final float CLEAR_ALPHA = 0.5f;

    /** Perspective setup, field of view component. */
    private static final float FOVY = 22.0f;

    /** Perspective setup, near component. */
    private static final float Z_NEAR = 0.1f;

    /** Perspective setup, far component. */
    private static final float Z_FAR = 100.0f;

    /** Object distance on the screen. move it back a bit so we can see it! */
    private static final float OBJECT_DISTANCE = -15.0f;

    /** The earth's sphere. */
    private final Sphere mEarth;

    /** The context. */
    private final Context mContext;

    /** The rotation angle, just to give the screen some action. */
    private float mRotationAngle;

    /**
     * Constructor to set the handed over context.
     * @param context The context.
     */
    public EarthGLRenderer(final Context context) {
        this.mContext = context;
        this.mEarth = new Sphere(3, 2);
        this.mRotationAngle = 0.0f;
    }

    @Override
    public void onDrawFrame(final GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, OBJECT_DISTANCE);
        gl.glRotatef(AXIAL_TILT_DEGRESS, 1, 0, 0);
        gl.glRotatef(this.mRotationAngle++, 0, 1, 0);
        this.mEarth.draw(gl);
    }

    @Override
    public void onSurfaceChanged(final GL10 gl, final int width, final int height) {
        final float aspectRatio = (float) width / (float) (height == 0 ? 1 : height);

        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, FOVY, aspectRatio, Z_NEAR, Z_FAR);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceCreated(final GL10 gl, final EGLConfig config) {
        this.mEarth.loadGLTexture(gl, this.mContext, R.mipmap.earthmap1k);
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearColor(CLEAR_RED, CLEAR_GREEN, CLEAR_BLUE, CLEAR_ALPHA);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
    }
}