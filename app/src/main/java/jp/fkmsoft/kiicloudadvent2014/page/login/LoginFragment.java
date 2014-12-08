package jp.fkmsoft.kiicloudadvent2014.page.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.fkmsoft.kiicloudadvent2014.Constants;
import jp.fkmsoft.kiicloudadvent2014.FragmentUtils;
import jp.fkmsoft.kiicloudadvent2014.MyApplication;
import jp.fkmsoft.kiicloudadvent2014.R;
import jp.fkmsoft.kiicloudadvent2014.page.main.MainFragment;
import jp.fkmsoft.libs.kiilib.apis.AppAPI;
import jp.fkmsoft.libs.kiilib.entities.KiiUser;
import jp.fkmsoft.libs.kiilib.volley.KiiVolleyAPI;

/**
 * Fragment for Login page
 */
public class LoginFragment extends Fragment {
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @InjectView(R.id.edit_username)
    EditText mUsernameEdit;

    @InjectView(R.id.edit_password)
    EditText mPasswordEdit;

    private KiiVolleyAPI mKiiAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApplication context = (MyApplication) getActivity().getApplicationContext();
        mKiiAPI = new KiiVolleyAPI(context.getQueue(), Constants.APP_ID, Constants.APP_KEY, Constants.BASE_URL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.inject(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @OnClick(R.id.button_login)
    void loginClicked(View v) {
        String username = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();

        mKiiAPI.loginAsUser(username, password, new AppAPI.LoginCallback<KiiUser>() {
            @Override
            public void onSuccess(String token, KiiUser kiiUser) {
                Toast.makeText(getActivity(), R.string.login_done, Toast.LENGTH_SHORT).show();

                // Save access token
                SharedPreferences pref = getActivity().getSharedPreferences(Constants.PREF_NAME, Context.MODE_PRIVATE);
                pref.edit().putString(Constants.PREF_KEY_TOKEN, token).apply();

                FragmentUtils.toNextFragment(getFragmentManager(), R.id.container, MainFragment.newInstance(token), false);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), R.string.login_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
