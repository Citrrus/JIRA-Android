package com.citrrus.jira.core;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import com.jondwillis.android.backports.BlockingDeque;
import com.jondwillis.android.backports.LinkedBlockingDeque;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 1/12/13
 * Time: 1:54 PM
 */
public class DialogUtils {


    public static void showFragmentDialog(FragmentManager mgr, DialogFragment df) {
        showFragmentDialog(mgr, df, true);
    }

    public static void showFragmentDialog(FragmentManager mgr, DialogFragment df, boolean hidePrevious) {
        if (hidePrevious) {
            hideFragmentDialog(mgr);
        }

        String tag = df.toString();
        dialogDeque.addFirst(tag);
        df.show(mgr, tag);
    }

    private static BlockingDeque<String> dialogDeque = new LinkedBlockingDeque<String>();

    public static void hideFragmentDialog(FragmentManager mgr) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = mgr.beginTransaction();

        // sync up our artificial stack
        List<String> tagsToRemove = new ArrayList<String>();
        for (String tag : dialogDeque) {
            if (mgr.findFragmentByTag(tag) == null) {
                tagsToRemove.add(tag);
            }
        }
        dialogDeque.removeAll(tagsToRemove);

        try {
            if (!dialogDeque.isEmpty()) {
                DialogFragment prev = (DialogFragment) mgr.findFragmentByTag(dialogDeque.takeFirst());
                if (prev != null) {
                    ft.remove(prev);
                    prev.dismiss();
                }
            }
        } catch (InterruptedException e) {

        }

        ft.commit();
    }
}
