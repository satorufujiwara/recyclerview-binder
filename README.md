recyclerview-binder
===

[![License](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-recyclerview--binder-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2368)
[![Download](https://api.bintray.com/packages/satorufujiwara/maven/recyclerview-binder/images/download.svg)](https://bintray.com/satorufujiwara/maven/recyclerview-binder/_latestVersion)

Android Library for RecyclerView to manage order of items and multiple view types.

# Features
* Insert any items to wherever you want without calculating their position.
* Insert any items in desired order regardless of the order of calling 'add/insert'.
* Separate implementations for each view type into their own classes.
* Support RxJava.

# Sample

 ![Sample](/art/sample.gif)

# Gradle

```groovy
repositories {
    jcenter()
}
dependencies {
    compile 'jp.satorufujiwara:recyclerview-binder:1.3.2'
}
```

# Quick Start

ViewType decides RecyclerView item's layout.

```java
public enum BinderSampleViewType implements ViewType {

    VIEW_TYPE_1,
    VIEW_TYPE_2,
    VIEW_TYPE_3;

    @Override
    public int viewType() {
        return ordinal();
    }
}
```

Binder binds data to Views in RecyclerView item.

```java
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
        TextView textSection;
        public ViewHolder(View view) {
            super(view);
            textSection = (TextView) view.findViewById(R.id.text_section);
        }
    }
}
```

Binder added to Adapter is layouted in order of Section.

```java
    RecyclerBinderAdapter<BinderSampleSection, BinderSampleViewType> adapter
            = new RecyclerBinderAdapter<>();

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
```

# Usage

### 1. Create class implemented [ViewType](https://github.com/satorufujiwara/recyclerview-binder/blob/master/binder/src/main/java/jp/satorufujiwara/binder/ViewType.java)

Enum is useful for this class.
 
### 2. Create classes extends [RecyclerBinder](https://github.com/satorufujiwara/recyclerview-binder/blob/master/binder/src/main/java/jp/satorufujiwara/binder/recycler/RecyclerBinder.java)

The number of these classes is the same to number of ViewType in Step 1.

### 3. Create class implemented [Section](https://github.com/satorufujiwara/recyclerview-binder/blob/master/binder/src/main/java/jp/satorufujiwara/binder/Section.java)

Enum is useful for this class.

### 4. Create [RecyclerBinderAdapter](https://github.com/satorufujiwara/recyclerview-binder/blob/master/binder/src/main/java/jp/satorufujiwara/binder/recycler/RecyclerBinderAdapter.java)

```java
RecyclerBinderAdapter<Section, ViewType> adapter = new RecyclerBinderAdapter<>();
```

### 5. Control RecyclerView

```java
adapter.add(Section.SECTION_1, binder);
adapter.addIfEmpty(Section.SECTION_1, binder);
adapter.addAll(Section.SECTION_1, List<RecyclerBinder> binders);
adapter.insert(Section.SECTION_1, binder, index);
adapter.remove(Section.SECTION_1, binder);
adapter.removeAll(Section.SECTION_1);
adapter.replaceAll(Section.SECTION_1, List<RecyclerBinder> binders);
adapter.clear();
```

## Use with RxJava

If subscibe observable in `RecyclerBinder` classes, use `RxRecyclerBinder` and `bindToLifecycle()`.

```java
myObservable
    .compose(bindToLifecycle())
    .subscribe();
```
When item removed from adapter, observable unsubscribe.
Note : Due to the unsubscription works, please call `RecyclerBinderAdapter.clear()` in `Activity.onDestroy()` or `Fragment.onDestroyView()`.


License
-------
    Copyright 2015 Satoru Fujiwara

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
