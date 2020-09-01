package com.alodiga.app.wallet.utils;

import android.app.ProgressDialog;
import android.content.Context;

import com.alodiga.app.R;

/**
 * Created by anyeli on 14/09/17.
 */

public class ProgressDialogAlodiga extends ProgressDialog {

    private int CONSTANT_MAXIMUM_VALUE_PROGRESS_DIALOG = 100;
    private int CONSTANT_START_PROGRESS = 0;


    public ProgressDialogAlodiga(Context context, String message) {
        super(context, R.style.DialogCustom);
        this.getWindow().setBackgroundDrawableResource(R.color.Gray);
        this.setMessage(message);
        this.setProgressStyle(STYLE_SPINNER);
        this.setCancelable(false);
        this.setMax(CONSTANT_MAXIMUM_VALUE_PROGRESS_DIALOG);
        this.setProgress(CONSTANT_START_PROGRESS);
    }

}
