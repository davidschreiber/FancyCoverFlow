/*
 * Copyright 2013 David Schreiber
 *           2013 John Paul Nalog
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package at.technikum.mti.fancycoverflow;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.SpinnerAdapter;

public class FancyCoverFlow extends Gallery {

    // =============================================================================
    // Constants
    // =============================================================================

    public static final int ACTION_DISTANCE_AUTO = Integer.MAX_VALUE;

    public static final float SCALEDOWN_GRAVITY_TOP = 0.0f;

    public static final float SCALEDOWN_GRAVITY_CENTER = 0.5f;

    public static final float SCALEDOWN_GRAVITY_BOTTOM = 1.0f;

    // =============================================================================
    // Private members
    // =============================================================================

    /**
     * TODO: Doc
     */
    private float unselectedAlpha;

    /**
     * Camera used for view transformation.
     */
    private Camera transformationCamera;

    /**
     * TODO: Doc
     */
    private int maxRotation = 75;

    /**
     * Factor (0-1) that defines how much the unselected children should be scaled down. 1 means no scaledown.
     */
    private float unselectedScale;

    /**
     * TODO: Doc
     */
    private float scaleDownGravity = SCALEDOWN_GRAVITY_CENTER;

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
        this.applyXmlAttributes(attrs);
    }

    public FancyCoverFlow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.initialize();
        this.applyXmlAttributes(attrs);
    }

    private void initialize() {
        this.transformationCamera = new Camera();
        this.setSpacing(0);
    }

    private void applyXmlAttributes(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FancyCoverFlow);

        this.actionDistance = a.getInteger(R.styleable.FancyCoverFlow_actionDistance, ACTION_DISTANCE_AUTO);
        this.scaleDownGravity = a.getFloat(R.styleable.FancyCoverFlow_scaleDownGravity, 1.0f);
        this.maxRotation = a.getInteger(R.styleable.FancyCoverFlow_maxRotation, 45);
        this.unselectedAlpha = a.getFloat(R.styleable.FancyCoverFlow_unselectedAlpha, 0.3f);
        this.unselectedSaturation = a.getFloat(R.styleable.FancyCoverFlow_unselectedSaturation, 0.0f);
        this.unselectedScale = a.getFloat(R.styleable.FancyCoverFlow_unselectedScale, 0.75f);
    }

    // =============================================================================
    // Getter / Setter
    // =============================================================================

    /**
     * Use this to provide a {@link FancyCoverFlowAdapter} to the coverflow. This
     * method will throw an {@link ClassCastException} if the passed adapter does not
     * sublass {@link FancyCoverFlowAdapter}.
     *
     * @param adapter
     */
    @Override
    public void setAdapter(SpinnerAdapter adapter) {
        if (!(adapter instanceof FancyCoverFlowAdapter)) {
            throw new ClassCastException(FancyCoverFlow.class.getName() + " only works in conjunction with a " + FancyCoverFlowAdapter.class.getName());
        }

        super.setAdapter(adapter);
    }

    /**
     * Returns the maximum rotation that is applied to items left and right of the center of the coverflow.
     *
     * @return
     */
    public int getMaxRotation() {
        return maxRotation;
    }

    /**
     * Sets the maximum rotation that is applied to items left and right of the center of the coverflow.
     *
     * @param maxRotation
     */
    public void setMaxRotation(int maxRotation) {
        this.maxRotation = maxRotation;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public float getUnselectedAlpha() {
        return this.unselectedAlpha;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public float getUnselectedScale() {
        return unselectedScale;
    }

    /**
     * TODO: Write doc
     *
     * @param unselectedScale
     */
    public void setUnselectedScale(float unselectedScale) {
        this.unselectedScale = unselectedScale;
    }

    /**
     * TODO: Doc
     *
     * @return
     */
    public float getScaleDownGravity() {
        return scaleDownGravity;
    }

    /**
     * TODO: Doc
     *
     * @param scaleDownGravity
     */
    public void setScaleDownGravity(float scaleDownGravity) {
        this.scaleDownGravity = scaleDownGravity;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public int getActionDistance() {
        return actionDistance;
    }

    /**
     * TODO: Write doc
     *
     * @param actionDistance
     */
    public void setActionDistance(int actionDistance) {
        this.actionDistance = actionDistance;
    }

    /**
     * TODO: Write doc
     *
     * @param unselectedAlpha
     */
    @Override
    public void setUnselectedAlpha(float unselectedAlpha) {
        super.setUnselectedAlpha(unselectedAlpha);
        this.unselectedAlpha = unselectedAlpha;
    }

    /**
     * TODO: Write doc
     *
     * @return
     */
    public float getUnselectedSaturation() {
        return unselectedSaturation;
    }

    /**
     * TODO: Write doc
     *
     * @param unselectedSaturation
     */
    public void setUnselectedSaturation(float unselectedSaturation) {
        this.unselectedSaturation = unselectedSaturation;
    }

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    protected boolean getChildStaticTransformation(View child, Transformation t) {
        // We can cast here because FancyCoverFlowAdapter only creates wrappers.
        FancyCoverFlowItemWrapper item = (FancyCoverFlowItemWrapper) child;

        // Since Jelly Bean childs won't get invalidated automatically, needs to be added for the smooth coverflow animation
        if (android.os.Build.VERSION.SDK_INT >= 16) {
            item.invalidate();
        }

        final int coverFlowWidth = this.getWidth();
        final int coverFlowCenter = coverFlowWidth / 2;
        final int childWidth = item.getWidth();
        final int childHeight = item.getHeight();
        final int childCenter = item.getLeft() + childWidth / 2;

        // Use coverflow width when its defined as automatic.
        final int actionDistance = (this.actionDistance == ACTION_DISTANCE_AUTO) ? (int) ((coverFlowWidth + childWidth) / 2.0f) : this.actionDistance;

        // Calculate the abstract amount for all effects.
        final float effectsAmount = Math.min(1.0f, Math.max(-1.0f, (1.0f / actionDistance) * (childCenter - coverFlowCenter)));

        // Calculate the value for each effect.
        final int rotationAngle = (int) (-effectsAmount * this.maxRotation);
        final float zoomAmount = (this.unselectedScale - 1) * Math.abs(effectsAmount) + 1;
        final float alphaAmount = (this.unselectedAlpha - 1) * Math.abs(effectsAmount) + 1;
        final float saturationAmount = (this.unselectedSaturation - 1) * Math.abs(effectsAmount) + 1;

        // Clear previous transformations and set transformation type (matrix + alpha).
        t.clear();
        t.setTransformationType(Transformation.TYPE_BOTH);

        // Apply alpha.
        t.setAlpha(alphaAmount);

        // Pass over saturation to the wrapper.
        item.setSaturation(saturationAmount);

        // Apply rotation.
        final Matrix imageMatrix = t.getMatrix();
        this.transformationCamera.save();
        this.transformationCamera.rotateY(rotationAngle);
        this.transformationCamera.getMatrix(imageMatrix);
        this.transformationCamera.restore();

        // Calculate the scale anchor (y anchor can be altered)
        final float translateX = childWidth / 2.0f;
        final float translateY = childHeight * this.scaleDownGravity;

        // Zoom.
        imageMatrix.preTranslate(-translateX, -translateY);
        imageMatrix.postScale(zoomAmount, zoomAmount);
        imageMatrix.postTranslate(translateX, translateY);

        return true;
    }

    // =============================================================================
    // Public classes
    // =============================================================================

    public static class LayoutParams extends Gallery.LayoutParams {
        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }
    }
}
