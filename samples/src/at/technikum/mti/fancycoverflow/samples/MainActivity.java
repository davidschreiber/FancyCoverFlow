package at.technikum.mti.fancycoverflow.samples;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;
import at.technikum.mti.fancycoverflow.FancyCoverFlowAdapter;

public class MainActivity extends Activity {

    // =============================================================================
    // Child views
    // =============================================================================

    private FancyCoverFlow fancyCoverFlow;

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        this.fancyCoverFlow = (FancyCoverFlow) this.findViewById(R.id.fancyCoverFlow);

        this.fancyCoverFlow.setAdapter(new SampleCoverFlowAdapter());
        this.fancyCoverFlow.setUnselectedAlpha(1.0f);
        this.fancyCoverFlow.setUnselectedSaturation(0.0f);
        this.fancyCoverFlow.setSpacing(-50);
        this.fancyCoverFlow.setMaxRotation(25);
        this.fancyCoverFlow.setMaxScaleDown(0.5f);

        // TODO: Make option to enable automatic action distance (action distance == cover flow width).
        this.fancyCoverFlow.setActionDistance(100);
    }

    // =============================================================================
    // Private classes
    // =============================================================================

    private static class SampleCoverFlowAdapter extends FancyCoverFlowAdapter {

        // =============================================================================
        // Private members
        // =============================================================================

        private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,};

        // =============================================================================
        // Constructor
        // =============================================================================

        private SampleCoverFlowAdapter() {

        }

        // =============================================================================
        // Supertype overrides
        // =============================================================================

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Integer getItem(int i) {
            return images[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getCoverFlowItem(int i, View reuseableView, ViewGroup viewGroup) {
            ImageView imageView = null;

            if (reuseableView != null) {
                imageView = (ImageView) reuseableView;
            } else {
                imageView = new ImageView(viewGroup.getContext());
                imageView.setLayoutParams(new Gallery.LayoutParams(200, 400));

            }

            imageView.setImageResource(this.getItem(i));
            return imageView;
        }
    }

}
