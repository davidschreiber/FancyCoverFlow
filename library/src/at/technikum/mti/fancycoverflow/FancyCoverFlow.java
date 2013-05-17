package at.technikum.mti.fancycoverflow;

import android.content.Context;
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

        t.clear();

        final int coverFlowCenter = (int) (this.getWidth() / 2.0f);
        final int childViewCenter = child.getLeft() + (int) (child.getWidth() / 2.0f);
        final int distance = coverFlowCenter-childViewCenter;

        t.setAlpha((float) Math.sin(distance/100));

        Matrix m = t.getMatrix();

//        m.postRotate(distance);

        return true;
    }
}
