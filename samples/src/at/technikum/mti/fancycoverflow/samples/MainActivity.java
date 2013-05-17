package at.technikum.mti.fancycoverflow.samples;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import at.technikum.mti.fancycoverflow.FancyCoverFlow;

import java.io.IOException;
import java.io.InputStream;

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
        this.fancyCoverFlow.setAdapter(new CoverFlowAdapter());
        this.fancyCoverFlow.setUnselectedAlpha(0.5f);
        this.fancyCoverFlow.setSpacing(100);
    }

    // =============================================================================
    // Private classes
    // =============================================================================

    private static class CoverFlowAdapter extends BaseAdapter {

        // =============================================================================
        // Private members
        // =============================================================================

        private int[] images = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5, R.drawable.image6,};

        // =============================================================================
        // Constructor
        // =============================================================================

        private CoverFlowAdapter() {

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
        public View getView(int i, View reuseableView, ViewGroup viewGroup) {
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
