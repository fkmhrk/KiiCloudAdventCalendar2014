package jp.fkmsoft.kiicloudadvent2014.page.signup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import jp.fkmsoft.kiicloudadvent2014.R;

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

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_signup, container, false);

        ButterKnife.inject(this, root);

        return root;
    }


}
