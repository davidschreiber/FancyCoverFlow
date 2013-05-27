package at.technikum.mti.fancycoverflow;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


public abstract class FancyCoverFlowAdapter extends BaseAdapter {

    // =============================================================================
    // Supertype overrides
    // =============================================================================

    @Override
    public final View getView(int i, View reusableView, ViewGroup viewGroup) {
        View wrappedView = null;
        FancyCoverFlowItemWrapper coverFlowItem;

        if (reusableView != null) {
            coverFlowItem = (FancyCoverFlowItemWrapper) reusableView;
            wrappedView = coverFlowItem.getWrappedView();
        } else {
            coverFlowItem = new FancyCoverFlowItemWrapper(viewGroup.getContext());
        }

        wrappedView = this.getCoverFlowItem(i, wrappedView, viewGroup);

        if (wrappedView == null) {
            throw new NullPointerException("getCoverFlowItem() was expected to return a view, but null was returned.");
        }

        coverFlowItem.setWrappedView(wrappedView);
        coverFlowItem.setLayoutParams(wrappedView.getLayoutParams());

        return coverFlowItem;
    }

    // =============================================================================
    // Abstract methods
    // =============================================================================

    public abstract View getCoverFlowItem(int position, View reusableView, ViewGroup parent);
}
