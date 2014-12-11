package jp.fkmsoft.kiicloudadvent2014.page.finduser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.fkmsoft.kiicloudadvent2014.Constants;
import jp.fkmsoft.kiicloudadvent2014.MyApplication;
import jp.fkmsoft.kiicloudadvent2014.R;
import jp.fkmsoft.libs.kiilib.apis.UserAPI;
import jp.fkmsoft.libs.kiilib.entities.KiiUser;
import jp.fkmsoft.libs.kiilib.volley.KiiVolleyAPI;

/**
 * Fragment for find user page
 */
public class FindUserFragment extends Fragment {
    private static final String ARGS_TOKEN = "token";

    public static FindUserFragment newInstance(String token) {
        FindUserFragment fragment = new FindUserFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TOKEN, token);
        fragment.setArguments(args);

        return fragment;
    }

    // arguments
    private String mToken;

    @InjectView(R.id.edit_username)
    EditText mUsernameEdit;

    private KiiVolleyAPI mAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mToken = args.getString(ARGS_TOKEN);

        MyApplication application = (MyApplication) getActivity().getApplication();
        RequestQueue queue = application.getQueue();
        mAPI = new KiiVolleyAPI(queue, Constants.APP_ID, Constants.APP_KEY, Constants.BASE_URL);
        mAPI.setAccessToken(mToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_find_user, container, false);

        ButterKnife.inject(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    @OnClick(R.id.button_find_user)
    void findUserClicked() {
        String username = mUsernameEdit.getText().toString();

        mAPI.userAPI().findUserByUsername(username, new UserAPI.UserCallback<KiiUser>() {
            @Override
            public void onSuccess(KiiUser kiiUser) {
                Toast.makeText(getActivity(), getString(R.string.find_user_found, kiiUser.getId()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), R.string.find_user_not_found, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
