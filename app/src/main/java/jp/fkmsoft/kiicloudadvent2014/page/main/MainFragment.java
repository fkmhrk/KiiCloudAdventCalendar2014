package jp.fkmsoft.kiicloudadvent2014.page.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.fkmsoft.kiicloudadvent2014.FragmentUtils;
import jp.fkmsoft.kiicloudadvent2014.R;
import jp.fkmsoft.kiicloudadvent2014.page.userscope.UserScopeFragment;
import jp.fkmsoft.libs.kiilib.entities.KiiUser;

/**
 * Fragment for main page
 */
public class MainFragment extends Fragment {
    private static final String ARGS_TOKEN = "token";
    private static final String ARGS_USER = "user";

    public static MainFragment newInstance(String token, KiiUser user) {
        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TOKEN, token);
        args.putParcelable(ARGS_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    // arguments
    private String mToken;
    private KiiUser mUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mToken = args.getString(ARGS_TOKEN);
        mUser = args.getParcelable(ARGS_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        ButterKnife.inject(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @OnClick(R.id.button_user_scope)
    void userScopeClicked(View v) {
        FragmentUtils.toNextFragment(getFragmentManager(), R.id.container, UserScopeFragment.newInstance(mToken, mUser), true);
    }
}
