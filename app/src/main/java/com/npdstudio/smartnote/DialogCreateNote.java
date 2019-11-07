package com.npdstudio.smartnote;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


@SuppressLint("ValidFragment")
public class DialogCreateNote extends DialogFragment {
    Dialog dialog;
    TextView txtTitle;
    @SuppressLint("ValidFragment")
    public DialogCreateNote(TextView txtTitle) {
        this.txtTitle  = txtTitle;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //return super.onCreateDialog(savedInstanceState);
        dialog = new Dialog(getActivity());
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_create_note);


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        final Button btnCancel = dialog.findViewById(R.id.btnCancel);
        final Button btnCreate = dialog.findViewById(R.id.btnCreate);
        final EditText edtTitle = dialog.findViewById(R.id.edtTitle);
        edtTitle.setText(txtTitle.getText().toString());
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                txtTitle.setText(edtTitle.getText().toString());
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        //dialog.show();
        return dialog;
    }

}
