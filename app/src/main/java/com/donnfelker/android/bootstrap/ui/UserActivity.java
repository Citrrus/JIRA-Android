package com.donnfelker.android.bootstrap.ui;

import static com.donnfelker.android.bootstrap.core.Constants.Extra.USER;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.donnfelker.android.bootstrap.R;
import com.donnfelker.android.bootstrap.core.AvatarLoader;
import com.donnfelker.android.bootstrap.core.User;
import com.google.inject.Inject;

import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar) protected ImageView avatar;
    @InjectView(R.id.tv_name) protected TextView name;

    @InjectExtra(USER) protected User user;

    @Inject protected AvatarLoader avatarLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(avatar, user);
        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

    }


}
