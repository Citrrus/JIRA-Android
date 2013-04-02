package com.citrrus.jira.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import com.citrrus.jira.R;
import com.citrrus.jira.core.AvatarLoader;
import com.citrrus.jira.core.Constants;
import com.citrrus.jira.core.User;

import javax.inject.Inject;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;
    @Inject protected AvatarLoader avatarLoader;

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        if(getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(Constants.Extra.USER);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(avatar, user);
        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

    }


}
