package jp.satorufujiwara.binder.sample;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import jp.satorufujiwara.binder.Section;
import jp.satorufujiwara.binder.recycler.RecyclerBinderAdapter;

public class BinderSampleFragment extends Fragment {

    public static BinderSampleFragment newInstance() {
        return new BinderSampleFragment();
    }

    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;

    private final RecyclerBinderAdapter<BinderSampleSection, BinderSampleViewType> adapter
            = new RecyclerBinderAdapter<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_binder_sample, container, false);
        ButterKnife.inject(this, v);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.add(BinderSampleSection.SECTION_1, new DataBinder1(getActivity(), "in Section1"));
        adapter.add(BinderSampleSection.SECTION_1, new DataBinder2(getActivity(), "in Section1"));
        adapter.add(BinderSampleSection.SECTION_1, new DataBinder3(getActivity(), "in Section1"));

        adapter.add(BinderSampleSection.SECTION_3, new DataBinder2(getActivity(), "in Section3"));
        adapter.add(BinderSampleSection.SECTION_3, new DataBinder1(getActivity(), "in Section3"));

        adapter.add(BinderSampleSection.SECTION_2, new DataBinder3(getActivity(), "in Section2"));
        adapter.add(BinderSampleSection.SECTION_2, new DataBinder2(getActivity(), "in Section2"));
        adapter.add(BinderSampleSection.SECTION_2, new DataBinder1(getActivity(), "in Section2"));
    }

    enum BinderSampleSection implements Section {

        SECTION_1,
        SECTION_2,
        SECTION_3;


        @Override
        public int position() {
            return ordinal();
        }
    }
}
