package com.citrrus.jira.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.citrrus.jira.R;

/**
 * Created with IntelliJ IDEA.
 * User: jonwillis
 * Date: 2/26/13
 * Time: 12:42 AM
 */
public class SignupFragment extends AuthenticationFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_signup, container, false);
    }
}
