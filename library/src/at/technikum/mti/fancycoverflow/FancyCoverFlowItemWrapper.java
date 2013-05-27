package at.technikum.mti.fancycoverflow;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.*;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * This class has only internal use (package scope).
 * <p/>
 * It is responsible for applying additional effects to each coverflow item, that can only be applied at view level (e.g. color saturation).
 */
class FancyCoverFlowItemWrapper extends View {

    // =============================================================================
    // Private members
    // =============================================================================

    /**
     * This is the real view that will be shown in the coverflow.
     */
    private View wrappedView;

    /**
     * This is a matrix to apply color filters (like saturation) to the wrapped view.
     */
    private ColorMatrix colorMatrix;

    /**
     * This paint is used to draw the wrapped view including any filters.
     */
    private Paint paint;

    /**
     * This is a cache holding the wrapped view's visual representation.
     */
    private Bitmap wrappedViewBitmap;

    /**
     * This canvas is used to let the wrapped view draw it's content.
     */
    private Canvas wrappedViewDrawingCanvas;

    // =============================================================================
    // Constructor
    // =============================================================================

    public FancyCoverFlowItemWrapper(Context context) {
        super(context);
        this.init();
    }

    public FancyCoverFlowItemWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    public FancyCoverFlowItemWrapper(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.init();
    }

    private void init() {
        this.wrappedView = null;
        this.paint = new Paint();
        this.colorMatrix = new ColorMatrix();
        // TODO: Define a default value for saturation inside an XML.
        this.setSaturation(1);
    }

    // =============================================================================
    // Getters / Setters
    // =============================================================================

    public void setWrappedView(View wrappedView) {
        this.wrappedView = wrappedView;
    }

    public View getWrappedView() {
        return this.wrappedView;
    }

    public void setSaturation(float saturation) {
        this.colorMatrix.setSaturation(saturation);
        this.paint.setColorFilter(new ColorMatrixColorFilter(this.colorMatrix));
    }

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.wrappedView != null) {
            int measuredWidth = this.getMeasuredWidth();
            int measuredHeight = this.getMeasuredHeight();

            this.wrappedView.layout(0, 0, measuredWidth, measuredHeight);
            this.wrappedViewBitmap = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888);
            this.wrappedViewDrawingCanvas = new Canvas(this.wrappedViewBitmap);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (this.wrappedView != null && this.wrappedViewDrawingCanvas != null) {

            // If on honeycomb or newer, cache the view.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                if (this.wrappedView.isDirty()) {
                    this.wrappedView.draw(this.wrappedViewDrawingCanvas);
                }
            } else {
                this.wrappedView.draw(this.wrappedViewDrawingCanvas);
            }


            canvas.drawBitmap(this.wrappedViewBitmap, 0, 0, paint);
        }
    }
}
