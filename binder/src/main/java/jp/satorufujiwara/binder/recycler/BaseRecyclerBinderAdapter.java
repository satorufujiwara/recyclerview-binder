package jp.satorufujiwara.binder.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import jp.satorufujiwara.binder.Binder;
import jp.satorufujiwara.binder.ViewType;

class BaseRecyclerBinderAdapter<V extends ViewType, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private final Object mLock = new Object();
    private final List<Binder<V, VH>> mObjects = new ArrayList<>();

    @Override
    public VH onCreateViewHolder(final ViewGroup parent, final int viewType) {
        return getItemByViewType(viewType).onCreateViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        getItem(position).onBindViewHolder(holder, position);
    }

    @Override
    public void onViewRecycled(VH holder) {
        final int position = holder.getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            getItem(position).onViewRecycled(holder);
        }
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getViewType().viewType();
    }

    public Binder<V, VH> getItem(int position) {
        return mObjects.get(position);
    }

    private Binder<V, VH> getItemByViewType(final int viewType) {
        for (final Binder<V, VH> item : mObjects) {
            if (item.getViewType().viewType() == viewType) {
                return item;
            }
        }
        throw new IllegalStateException("binder doesn't exist in list.");
    }

    void insert(final Binder<V, VH> object, final int index) {
        synchronized (mLock) {
            mObjects.add(index, object);
        }
    }

    void remove(final Binder<V, VH> object) {
        synchronized (mLock) {
            object.onRemoved();
            mObjects.remove(object);
        }
    }

    void clear() {
        synchronized (mLock) {
            for (final Binder<V, VH> item : mObjects) {
                item.onRemoved();
            }
            mObjects.clear();
        }
    }

}
