package jp.satorufujiwara.binder.recycler;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import jp.satorufujiwara.binder.Binder;
import jp.satorufujiwara.binder.ViewType;

public abstract class RecyclerBinder<V extends ViewType>
        implements Binder<V, RecyclerView.ViewHolder> {

    private final V mViewType;
    private Context mContext;

    protected RecyclerBinder(final Context context, final V viewType) {
        mContext = context;
        mViewType = viewType;
    }

    @LayoutRes
    public abstract int layoutResId();

    public abstract RecyclerView.ViewHolder onCreateViewHolder(final View v);

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent) {
        return onCreateViewHolder(LayoutInflater.from(mContext).inflate(layoutResId(), parent, false));
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        // no op
    }

    @Override
    public void onRemoved() {
        mContext = null;
    }

    @Override
    public V getViewType() {
        return mViewType;
    }

    public final Context getContext() {
        return mContext;
    }

}
