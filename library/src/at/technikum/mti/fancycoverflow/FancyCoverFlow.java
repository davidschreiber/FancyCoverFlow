package at.technikum.mti.fancycoverflow;

import android.content.Context;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import at.technikum.mti.fancycoverflow.views.FancyCoverFlowItem;

public class FancyCoverFlow extends Gallery {

    // =============================================================================
    // Private members
    // =============================================================================

    private float unselectedAlpha;

    /**
     * Camera used for view transformation.
     */
    private Camera transformationCamera;

    private int maxRotation = 75;

    private float maxScaleDown;

    /**
     * Distance in pixels between the transformation effects (alpha, rotation, zoom) are applied.
     */
    private int actionDistance;

    /**
     * Saturation factor (0-1) of items that reach the outer effects distance.
     */
    private float unselectedSaturation;

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


    public int getMaxRotation() {
        return maxRotation;
    }

    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }

    public float getUnselectedAlpha() {
        return this.unselectedAlpha;
    }

    public float getMaxScaleDown() {
        return maxScaleDown;
    }

    public void setMaxScaleDown(float maxScaledown) {
        this.maxScaleDown = maxScaledown;
    }

    public int getActionDistance() {
        return actionDistance;
    }

    public void setActionDistance(int actionDistance) {
        this.actionDistance = actionDistance;
    }

    @Override
    public void setUnselectedAlpha(float unselectedAlpha) {
        super.setUnselectedAlpha(unselectedAlpha);
        this.unselectedAlpha = unselectedAlpha;
    }

    public float getUnselectedSaturation() {
        return unselectedSaturation;
    }

    public void setUnselectedSaturation(float unselectedSaturation) {
        this.unselectedSaturation = unselectedSaturation;
    }

// =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        FancyCoverFlowItem item = (FancyCoverFlowItem) child;

        // Since Jelly Bean childs won't get invalidated automatically, needs to be added for the smooth coverflow animation
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            item.invalidate();
        }

        // TODO: Check int division.
        final int coverFlowCenter = this.getWidth() / 2;
        final int childCenter = item.getLeft() + item.getWidth() / 2;

        final int childWidth = item.getWidth();
        final int childHeight = item.getWidth();

        // Calculate the abstract amount for all effects.
        final float effectsAmount = Math.min(1.0f, Math.max(-1.0f, (1.0f / this.actionDistance) * (childCenter - coverFlowCenter)));

        // Calculate the value for each effect.
        final int rotationAngle = (int) (-effectsAmount * this.maxRotation);
        final float zoomAmount = 1 - this.maxScaleDown * Math.abs(effectsAmount);
        final float alphaAmount = (this.unselectedAlpha - 1) * Math.abs(effectsAmount) + 1;
        final float saturationAmount = (this.unselectedSaturation - 1) * Math.abs(effectsAmount) + 1;

        // Clear previous transformations and set transformation type (matrix + alpha).
        t.clear();
        t.setTransformationType(Transformation.TYPE_BOTH);

        // Apply alpha.
        t.setAlpha(alphaAmount);

        item.setSaturation(saturationAmount);

        // Apply rotation.
        final Matrix imageMatrix = t.getMatrix();
        this.transformationCamera.save();
        this.transformationCamera.rotateY(rotationAngle);
        this.transformationCamera.getMatrix(imageMatrix);
        this.transformationCamera.restore();

        // Zoom.
        imageMatrix.preTranslate(-childWidth / 2.0f, -childHeight / 2.0f);
        imageMatrix.postScale(zoomAmount, zoomAmount);
        imageMatrix.postTranslate(childWidth / 2.0f, childHeight / 2.0f);

        return true;
    }
}
