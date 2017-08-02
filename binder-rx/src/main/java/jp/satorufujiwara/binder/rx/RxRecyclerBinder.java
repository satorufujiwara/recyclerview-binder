package jp.satorufujiwara.binder.rx;


import android.content.Context;
import android.support.v7.widget.RecyclerView;

import io.reactivex.subjects.PublishSubject;
import jp.satorufujiwara.binder.ViewType;
import jp.satorufujiwara.binder.recycler.RecyclerBinder;

public abstract class RxRecyclerBinder<V extends ViewType> extends RecyclerBinder<V> {

    private static final Object SIGNAL = new Object();
    private PublishSubject<Object> lifecycleSubject = PublishSubject.create();

    protected RxRecyclerBinder(final Context context, final V viewType) {
        super(context, viewType);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        lifecycleSubject.onNext(SIGNAL);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        lifecycleSubject.onNext(SIGNAL);
    }

    public final <T> LifecycleTransformer<T> bindToLifecycle() {
        return new LifecycleTransformer<>(lifecycleSubject);
    }
}