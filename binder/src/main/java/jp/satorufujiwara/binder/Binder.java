package jp.satorufujiwara.binder;

import android.view.ViewGroup;

public interface Binder<V extends ViewType, VH> {

    VH onCreateViewHolder(ViewGroup parent);

    void onBindViewHolder(VH viewHolder, int position);

    void onViewRecycled(VH holder);

    void onRemoved();

    V getViewType();

}
