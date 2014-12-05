package jp.fkmsoft.kiicloudadvent2014;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Utility class
 */
public class FragmentUtils {
    /**
     * Replaces with next fragment
     * @param manager Fragment manager
     * @param containerId Container id
     * @param next The fragment you want to replace with
     * @param addBackstack true if you want to add current fragment to backstack with tag ""
     */
    public static void toNextFragment(FragmentManager manager, int containerId, Fragment next, boolean addBackstack) {
        toNextFragment(manager, containerId, next, addBackstack, "");
    }

    /**
     * Replaces with next fragment
     * @param manager Fragment manager
     * @param containerId Container id
     * @param next The Fragment you want to replace with
     * @param addBackstack true if you want to add current fragment to backstack with tag ""
     * @param tag The tag for next fragment.
     *            You can find next with this tag by {@link android.support.v4.app.FragmentManager#findFragmentByTag(String)}
     */
    public static void toNextFragment(FragmentManager manager, int containerId, Fragment next, boolean addBackstack, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (addBackstack) {
            transaction.addToBackStack("");
        }
        transaction.replace(containerId, next, tag);
        transaction.commit();
    }
}
