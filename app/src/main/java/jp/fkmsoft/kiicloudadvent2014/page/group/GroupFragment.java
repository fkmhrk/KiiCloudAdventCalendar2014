package jp.fkmsoft.kiicloudadvent2014.page.group;

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
import jp.fkmsoft.libs.kiilib.apis.GroupAPI;
import jp.fkmsoft.libs.kiilib.entities.KiiGroup;
import jp.fkmsoft.libs.kiilib.entities.KiiUser;
import jp.fkmsoft.libs.kiilib.volley.KiiVolleyAPI;

/**
 * Fragment for Group page
 */
public class GroupFragment extends Fragment {
    private static final String ARGS_TOKEN = "token";
    private static final String ARGS_USER = "user";

    public static GroupFragment newInstance(String token, KiiUser user) {
        GroupFragment fragment = new GroupFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TOKEN, token);
        args.putParcelable(ARGS_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    // arguments
    private String mToken;
    private KiiUser mUser;

    @InjectView(R.id.edit_group_name)
    EditText mGroupNameEdit;

    private KiiVolleyAPI mAPI;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        mToken = args.getString(ARGS_TOKEN);
        mUser = args.getParcelable(ARGS_USER);

        MyApplication application = (MyApplication) getActivity().getApplication();
        RequestQueue queue = application.getQueue();
        mAPI = new KiiVolleyAPI(queue, Constants.APP_ID, Constants.APP_KEY, Constants.BASE_URL);
        mAPI.setAccessToken(mToken);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_group, container, false);

        ButterKnife.inject(this, root);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        ButterKnife.reset(this);
    }

    // UI event
    @OnClick(R.id.button_create_group)
    void createGroupClicked() {
        String groupName = mGroupNameEdit.getText().toString();

        mAPI.groupAPI().create(groupName, mUser, null, new GroupAPI.GroupCallback<KiiGroup>() {
            @Override
            public void onSuccess(KiiGroup group) {
                mGroupNameEdit.setText("");
                Toast.makeText(getActivity(), getString(R.string.group_created, group.getId()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), R.string.group_create_failed, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
