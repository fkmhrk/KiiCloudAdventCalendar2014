package jp.fkmsoft.kiicloudadvent2014.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import jp.fkmsoft.kiicloudadvent2014.R;

/**
 * Dialog for providing input form
 */
public class InputDialogFragment extends DialogFragment {
    public static InputDialogFragment newInstance(Fragment target, int requestCode) {
        InputDialogFragment fragment = new InputDialogFragment();
        fragment.setTargetFragment(target, requestCode);

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @InjectView(R.id.edit)
    EditText mEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.dialog_input, container, false);

        ButterKnife.inject(this, root);

        return root;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setTitle(null);
        return dialog;
    }

    @OnClick(R.id.button_submit)
    void submitClicked(View v) {
        Fragment target = getTargetFragment();
        if (target == null) {
            dismiss();
            return;
        }

        String text = mEdit.getText().toString();
        Intent it = new Intent();
        it.putExtra(Intent.EXTRA_TEXT, text);
        target.onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, it);
        dismiss();
    }

}
