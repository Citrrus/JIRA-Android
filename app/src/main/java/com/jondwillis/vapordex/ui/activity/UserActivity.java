package com.jondwillis.vapordex.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.inject.Inject;
import com.jondwillis.vapordex.R;
import com.jondwillis.vapordex.core.AvatarLoader;
import com.jondwillis.vapordex.core.User;
import roboguice.inject.InjectExtra;
import roboguice.inject.InjectView;

import static com.jondwillis.vapordex.core.Constants.Extra.USER;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar)
    protected ImageView avatar;
    @InjectView(R.id.tv_name)
    protected TextView name;

    @InjectExtra(USER)
    protected User user;

    @Inject
    protected AvatarLoader avatarLoader;

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
