package jp.fkmsoft.kiicloudadvent2014.page.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import jp.fkmsoft.kiicloudadvent2014.R;

/**
 * Fragment for main page
 */
public class MainFragment extends Fragment {
    private static final String ARGS_TOKEN = "token";

    public static MainFragment newInstance(String token) {
        MainFragment fragment = new MainFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TOKEN, token);
        fragment.setArguments(args);

        return fragment;
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
}
