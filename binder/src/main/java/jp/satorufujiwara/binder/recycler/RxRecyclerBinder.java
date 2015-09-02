package jp.satorufujiwara.binder.recycler;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import jp.satorufujiwara.binder.ViewType;
import rx.Observable;
import rx.subjects.BehaviorSubject;

public abstract class RxRecyclerBinder<V extends ViewType> extends RecyclerBinder<V> {

    private final BehaviorSubject<Void> lifecycleSubject = BehaviorSubject.create();

    protected RxRecyclerBinder(final Activity activity, final V viewType) {
        super(activity, viewType);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        lifecycleSubject.onNext(null);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        lifecycleSubject.onNext(null);
    }

    public final <T> Observable.Transformer<T, T> bindToLifecycle() {
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
