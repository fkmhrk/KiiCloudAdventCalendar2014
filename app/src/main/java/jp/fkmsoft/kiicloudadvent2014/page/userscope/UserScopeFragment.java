package jp.fkmsoft.kiicloudadvent2014.page.userscope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jp.fkmsoft.kiicloudadvent2014.Constants;
import jp.fkmsoft.kiicloudadvent2014.MyApplication;
import jp.fkmsoft.kiicloudadvent2014.R;
import jp.fkmsoft.kiicloudadvent2014.dialog.InputDialogFragment;
import jp.fkmsoft.libs.kiilib.apis.BucketAPI;
import jp.fkmsoft.libs.kiilib.apis.ObjectAPI;
import jp.fkmsoft.libs.kiilib.apis.QueryResult;
import jp.fkmsoft.libs.kiilib.entities.KiiBucket;
import jp.fkmsoft.libs.kiilib.entities.KiiClause;
import jp.fkmsoft.libs.kiilib.entities.KiiObject;
import jp.fkmsoft.libs.kiilib.entities.KiiUser;
import jp.fkmsoft.libs.kiilib.entities.QueryParams;
import jp.fkmsoft.libs.kiilib.volley.KiiVolleyAPI;

/**
 * Fragment for User Scope Page
 */
public class UserScopeFragment extends ListFragment {
    private static final String ARGS_TOKEN = "token";
    private static final String ARGS_USER = "user";

    private static final String FIELD_TITLE = "title";
    private static final int REQUEST_ADD = 1000;

    public static UserScopeFragment newInstance(String token, KiiUser user) {
        UserScopeFragment fragment = new UserScopeFragment();

        Bundle args = new Bundle();
        args.putString(ARGS_TOKEN, token);
        args.putParcelable(ARGS_USER, user);
        fragment.setArguments(args);

        return fragment;
    }

    // arguments
    private String mToken;
    private KiiUser mUser;

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

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_user_scope, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // It would be good to use loader instead

        final KiiBucket bucket = new KiiBucket(mUser, Constants.BUCKET_NAME_MEMO);
        final QueryParams params = new QueryParams(KiiClause.all());
        params.sortByDesc("_modified");

        mAPI.bucketAPI().query(bucket, params, new BucketAPI.QueryCallback<KiiBucket, KiiObject>() {
            List<KiiObject> list = new ArrayList<>();
            @Override
            public void onSuccess(QueryResult<KiiBucket, KiiObject> kiiObjects) {
                list.addAll(kiiObjects);
                if (kiiObjects.hasNext()) {
                    mAPI.bucketAPI().query(bucket, params, this);
                    return;
                }
                setItems(list);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), R.string.user_scope_err_load, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.action_add:
            showAddDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
        case REQUEST_ADD:
            if (resultCode != Activity.RESULT_OK) { return; }

            String text = data.getStringExtra(Intent.EXTRA_TEXT);
            createMemo(text);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // private

    private void showAddDialog() {
        InputDialogFragment dialog = InputDialogFragment.newInstance(this, REQUEST_ADD);
        dialog.show(getFragmentManager(), "");
    }

    private void createMemo(String titie) {
        KiiBucket bucket = new KiiBucket(mUser, Constants.BUCKET_NAME_MEMO);
        JSONObject data = new JSONObject();
        try {
            data.put(FIELD_TITLE, titie);
        } catch (JSONException e) {
            // nop
        }

        mAPI.objectAPI().create(bucket, data, new ObjectAPI.ObjectCallback<KiiBucket, KiiObject>() {
            @Override
            public void onSuccess(KiiObject kiiObject) {
                addItem(kiiObject);
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), R.string.user_scope_err_create, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setItems(List<KiiObject> list) {
        ArrayAdapter<Item> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        for (KiiObject obj : list) {
            adapter.add(new Item(obj));
        }
        setListAdapter(adapter);
    }

    private void addItem(KiiObject kiiObject) {
        ArrayAdapter<Item> adapter = (ArrayAdapter<Item>) getListAdapter();
        adapter.insert(new Item(kiiObject), 0);
        adapter.notifyDataSetChanged();
    }

    private static class Item {
        private KiiObject mObj;
        Item(KiiObject obj) {
            mObj = obj;
        }

        @Override
        public String toString() {
            return mObj.optString(FIELD_TITLE, "");
        }
    }
}
