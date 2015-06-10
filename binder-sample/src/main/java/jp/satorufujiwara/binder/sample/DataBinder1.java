package jp.satorufujiwara.binder.sample;


import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.satorufujiwara.binder.recycler.RecyclerBinder;

public class DataBinder1 extends RecyclerBinder<BinderSampleViewType> {

    private final String text;

    public DataBinder1(Activity activity, String text) {
        super(activity, BinderSampleViewType.VIEW_TYPE_1);
        this.text = text;
    }

    @Override
    public int layoutResId() {
        return R.layout.binder_data_1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder holder = (ViewHolder) viewHolder;
        holder.textSection.setText(text);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.text_section)
        TextView textSection;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
