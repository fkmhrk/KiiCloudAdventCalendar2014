package jp.fkmsoft.kiicloudadvent2014.page.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.fkmsoft.kiicloudadvent2014.Constants;
import jp.fkmsoft.kiicloudadvent2014.MyApplication;
import jp.fkmsoft.kiicloudadvent2014.R;
import jp.fkmsoft.libs.kiilib.apis.AppAPI;
import jp.fkmsoft.libs.kiilib.entities.KiiUser;
import jp.fkmsoft.libs.kiilib.volley.KiiVolleyAPI;

/**
 * Fragment for Login page
 */
public class SignupFragment extends Fragment {
    public static SignupFragment newInstance() {
        SignupFragment fragment = new SignupFragment();

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
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        ButterKnife.inject(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @OnClick(R.id.button_signup)
    void signupClicked(View v) {
        String username = mUsernameEdit.getText().toString();
        String password = mPasswordEdit.getText().toString();

        JSONObject info = new JSONObject();
        try {
            info.put("loginName", username);
        } catch (JSONException e) {
            // nop
        }

        mKiiAPI.signup(info, password, new AppAPI.SignupCallback<KiiUser>() {
            @Override
            public void onSuccess(KiiUser kiiUser) {
                Toast.makeText(getActivity(), R.string.signup_done, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), R.string.signup_failed, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
