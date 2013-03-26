package com.jondwillis.vapordex.ui.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.jondwillis.vapordex.R;
import com.jondwillis.vapordex.core.AvatarLoader;
import com.jondwillis.vapordex.core.User;
import javax.inject.Inject;

import butterknife.InjectView;

import static com.jondwillis.vapordex.core.Constants.Extra.USER;

public class UserActivity extends BootstrapActivity {

    @InjectView(R.id.iv_avatar)
    protected ImageView avatar;
    @InjectView(R.id.tv_name)
    protected TextView name;

    @Inject protected AvatarLoader avatarLoader;

    protected User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_view);

        if(getIntent() != null && getIntent().getExtras() != null) {
            user = (User) getIntent().getExtras().getSerializable(USER);
        }

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        avatarLoader.bind(avatar, user);
        name.setText(String.format("%s %s", user.getFirstName(), user.getLastName()));

    }


}
