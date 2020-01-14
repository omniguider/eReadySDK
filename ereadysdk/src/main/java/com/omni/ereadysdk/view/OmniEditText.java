package com.omni.ereadysdk.view;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class OmniEditText extends AppCompatEditText {

    public interface OmniEditTextListener {
        void onSoftKeyboardDismiss();

        void onTouch(MotionEvent event);
    }

    private Context mContext;
    private OmniEditTextListener mListener;
    private int mMaxLines = 1;

    public OmniEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        this.setFocusable(false);

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                OmniEditText.this.setFocusable(true);
                OmniEditText.this.setFocusableInTouchMode(true);
                mListener.onTouch(event);
                return false;
            }
        });

        this.setOnEditorActionListener(new OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    hideKeyboard();

                    OmniEditText.this.setFocusable(false);

                    mListener.onSoftKeyboardDismiss();
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyPreIme(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            hideKeyboard();

            OmniEditText.this.setFocusable(false);

            mListener.onSoftKeyboardDismiss();

            return true;
        }
        return super.onKeyPreIme(keyCode, event);
    }

    @Override
    public int getMaxLines() {
        return mMaxLines;
    }

    @Override
    public void setMaxLines(int maxLines) {
        mMaxLines = maxLines;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        TextWatcher watcher = new TextWatcher() {

            private String text;
            private int beforeCursorPosition = 0;

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO sth
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                text = s.toString();
                beforeCursorPosition = start;
            }

            @Override
            public void afterTextChanged(Editable s) {

                /* turning off listener */
                removeTextChangedListener(this);

                /* handling lines limit exceed */
                if (OmniEditText.this.getLineCount() > mMaxLines) {
                    OmniEditText.this.setText(text);
                    OmniEditText.this.setSelection(beforeCursorPosition);
                }

                /* handling character limit exceed */
//                if (s.toString().length() > maxCharacters) {
//                    OmniEditText.this.setText(text);
//                    OmniEditText.this.setSelection(beforeCursorPosition);
//                    Toast.makeText(context, "text too long", Toast.LENGTH_SHORT)
//                            .show();
//                }

                /* turning on listener */
                addTextChangedListener(this);
            }
        };

        this.addTextChangedListener(watcher);
    }

    public void setOmniEditTextListener(OmniEditTextListener mListener) {
        this.mListener = mListener;
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindowToken(), 0);
    }
}
