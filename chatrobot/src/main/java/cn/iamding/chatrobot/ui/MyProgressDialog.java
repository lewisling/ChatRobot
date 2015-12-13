package cn.iamding.chatrobot.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import cn.iamding.chatrobot.R;

/**
 * 自定义progressDialog
 */

public class MyProgressDialog extends Dialog {

    public MyProgressDialog(Context context) {
        this(context, "正在拼命加载中。。。");
    }

    public MyProgressDialog(Context context, String progressDialogText) {
        this(context, R.style.CustomProgressDialog, progressDialogText);
    }

    /**
     * @param context 要显示ProgressDialog的环境
     * @param theme 自定义的Dialog样式
     * @param progressDialogText 要显示的文字
     */
    public MyProgressDialog(Context context, int theme, String progressDialogText) {
        super(context, theme);
        this.setContentView(R.layout.progress_dialog);
        this.getWindow().getAttributes().gravity = Gravity.CENTER;
        TextView progressDialogTextView = (TextView) this.findViewById(R.id.progressDialog_textView);
        if (progressDialogTextView != null) {
            progressDialogTextView.setText(progressDialogText);
        }
        show();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (!hasFocus) {
            dismiss();
        }
    }

}

