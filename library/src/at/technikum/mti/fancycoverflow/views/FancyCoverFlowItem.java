package at.technikum.mti.fancycoverflow.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FancyCoverFlowItem extends ViewGroup {

    // =============================================================================
    // Private members
    // =============================================================================

    private ColorMatrix colorMatrix;
    private Paint paint;

    private Bitmap childBitmap;

    private Canvas saturationCanvas;
    private float saturation;

    // =============================================================================
    // Constructor
    // =============================================================================

    public FancyCoverFlowItem(Context context) {
        super(context);
        this.init();
    }

    public FancyCoverFlowItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public FancyCoverFlowItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.paint = new Paint();
        this.colorMatrix = new ColorMatrix();

        this.setSaturation(1);
    }

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View child = getChildAt(0);

        if (child != null) {
            int measuredWidth = this.getMeasuredWidth();
            int measuredHeight = this.getMeasuredHeight();

            child.layout(0, 0, measuredWidth, measuredHeight);
            this.childBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            this.saturationCanvas = new Canvas(this.childBitmap);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void dispatchDraw(Canvas canvas) {
        View child = getChildAt(0);

        if (child != null && this.saturationCanvas != null) {

            // If on honeycomb or newer, cache the view.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (child.isDirty()) {
                    child.draw(this.saturationCanvas);
                }
            }else {
                child.draw(this.saturationCanvas);
            }


            canvas.drawBitmap(this.childBitmap, 0, 0, paint);
        }
    }

    public void setSaturation(float saturation) {
        this.colorMatrix.setSaturation(saturation);
        this.paint.setColorFilter(new ColorMatrixColorFilter(this.colorMatrix));
    }
}
