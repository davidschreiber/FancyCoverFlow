package at.technikum.mti.fancycoverflow;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;

public class FancyCoverFlow extends Gallery {

    // =============================================================================
    // Private members
    // =============================================================================

    private float unselectedAlpha;

    /**
     * Camera used for view transformation.
     */
    private Camera transformationCamera;

    private int mMaxRotationAngle = 75;

    // =============================================================================
    // Constructors
    // =============================================================================

    public FancyCoverFlow(Context context) {
        super(context);
        this.initialize();
    }


    public FancyCoverFlow(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
    }

    public FancyCoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initialize();
    }

    private void initialize() {
        this.transformationCamera = new Camera();
        this.setSpacing(0);
    }

    // =============================================================================
    // Getter
    // =============================================================================

    public float getUnselectedAlpha() {
        return this.unselectedAlpha;
    }

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public void setUnselectedAlpha(float unselectedAlpha) {
        super.setUnselectedAlpha(unselectedAlpha);
        this.unselectedAlpha = unselectedAlpha;
    }

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        // Since Jelly Bean childs won't get invalidated automatically, needs to be added for the smooth coverflow animation
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            child.invalidate();
        }

        // TODO: Check int division.
        final int coverFlowCenter = this.getWidth() / 2;
        final int childCenter = child.getLeft() + child.getWidth() / 2;
        final int childWidth = child.getWidth();

        int rotationAngle;

        t.clear();

        t.setTransformationType(Transformation.TYPE_MATRIX);

        if (childCenter == coverFlowCenter) {
            rotationAngle = 0;
        } else {
            rotationAngle = (int) ((float) (coverFlowCenter - childCenter) / childWidth * mMaxRotationAngle);
            if (Math.abs(rotationAngle) > mMaxRotationAngle) {
                rotationAngle = rotationAngle < 0 ? -mMaxRotationAngle : mMaxRotationAngle;
            }
        }

        final int width = child.getWidth();
        final int height = child.getHeight();

        final Matrix imageMatrix = t.getMatrix();

        this.transformationCamera.save();
        this.transformationCamera.rotateY(rotationAngle);
        this.transformationCamera.getMatrix(imageMatrix);
        this.transformationCamera.restore();

        final float maxScaleDown = 0.7f;
        final float zoomAmount = Math.max(maxScaleDown, ((Math.abs(rotationAngle) * (maxScaleDown - 1)) / mMaxRotationAngle) + 1);

        // Always do a fresh transformation.
        imageMatrix.postScale(zoomAmount, zoomAmount);
        imageMatrix.preTranslate(-width / 2.0f, -height / 2.0f);
        imageMatrix.postTranslate(width / 2.0f, height / 2.0f);

        return true;
    }
}
