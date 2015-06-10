package jp.satorufujiwara.binder.sample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

public class FragmentActivityExt {

    private FragmentActivityExt() {
        //extension
    }

    public static Fragment setContentFragment(final FragmentActivity ext, final int containerViewId,
            final Fragment fragment) {
        final Fragment f = ext.getSupportFragmentManager().findFragmentById(containerViewId);
        if (f != null) {
            return f;
        }
        ext.getSupportFragmentManager().beginTransaction().add(containerViewId, fragment).commit();
        return fragment;
    }

}
