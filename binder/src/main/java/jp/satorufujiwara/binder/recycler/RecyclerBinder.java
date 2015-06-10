package jp.satorufujiwara.binder.recycler;


import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

import jp.satorufujiwara.binder.Binder;
import jp.satorufujiwara.binder.ViewType;

public abstract class RecyclerBinder<V extends ViewType>
        implements Binder<V, RecyclerView.ViewHolder> {

    private final WeakReference<Activity> mActivity;
    private final V mViewType;

    protected RecyclerBinder(final Activity activity, final V viewType) {
        mActivity = new WeakReference<>(activity);
        mViewType = viewType;
    }

    @LayoutRes
    public abstract int layoutResId();

    public abstract RecyclerView.ViewHolder onCreateViewHolder(final View v);

    @Override
    public final RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent) {
        final Activity activity = getActivity();
        if (activity == null) {
            return null;
        }
        return onCreateViewHolder(activity.getLayoutInflater()
                .inflate(layoutResId(), parent, false));
    }

    @Override
    public V getViewType() {
        return mViewType;
    }

    protected final Activity getActivity() {
        return mActivity.get();
    }

}
