package jp.satorufujiwara.binder.recycler;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import jp.satorufujiwara.binder.ViewType;
import rx.Observable;
import rx.subjects.BehaviorSubject;

public abstract class RxRecyclerBinder<V extends ViewType> extends RecyclerBinder<V> {

    private BehaviorSubject<Void> lifecycleSubject = null;

    protected RxRecyclerBinder(final Activity activity, final V viewType) {
        super(activity, viewType);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        if (lifecycleSubject == null) {
            return;
        }
        lifecycleSubject.onNext(null);
        lifecycleSubject = null;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (lifecycleSubject == null) {
            return;
        }
        lifecycleSubject.onNext(null);
        lifecycleSubject = null;
    }

    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
        if (lifecycleSubject == null) {
            lifecycleSubject = BehaviorSubject.create();
        }
        return bind(lifecycleSubject);
    }

    private static <T, R> Observable.Transformer<T, T> bind(final Observable<R> lifecycle) {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> source) {
                return source.takeUntil(lifecycle);
            }
        };
    }
}
