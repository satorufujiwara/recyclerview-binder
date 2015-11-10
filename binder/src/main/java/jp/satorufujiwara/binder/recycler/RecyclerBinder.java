package jp.satorufujiwara.binder.recycler;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import jp.satorufujiwara.binder.Binder;
import jp.satorufujiwara.binder.ViewType;

public abstract class RecyclerBinder<V extends ViewType>
        implements Binder<V, RecyclerView.ViewHolder> {

    private final V mViewType;
    private Activity mActivity;

    protected RecyclerBinder(final Activity activity, final V viewType) {
        mActivity = activity;
        mViewType = viewType;
    }

    @LayoutRes
    public abstract int layoutResId();

    public abstract RecyclerView.ViewHolder onCreateViewHolder(final View v);

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent) {
        return onCreateViewHolder(mActivity.getLayoutInflater()
                .inflate(layoutResId(), parent, false));
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        // no op
    }

    @Override
    public void onRemoved() {
        mActivity = null;
    }

    @Override
    public V getViewType() {
        return mViewType;
    }

    public final Activity getActivity() {
        return mActivity;
    }

}
