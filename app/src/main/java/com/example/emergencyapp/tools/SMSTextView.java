package com.example.emergencyapp.tools;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.example.emergencyapp.R;


/**
 * 获取短信验证码时的textview
 */
public class SMSTextView extends AppCompatTextView {

    private static final String DEFAULT_FORMATTED = "重新获取(%ds)", DEFAULT_NOT_FORMAT = "获取验证码";
    private static final int DEFAULT_DELAY = 60;
    private static final Handler HANDLER = new Handler();
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (mDelaying-- > 0) {
                removeLastDelay();
                startDelay();
                setText(StringUtil.format(mSendingFormatted, mDelaying));
            } else {
                reset();
            }
        }
    };

    private final int mCurrentColor, mCurrentHintColor;
    private final String mCurrentText;
    private int mDelayInSecond = DEFAULT_DELAY;//存放时长，如60s
    private int mDelaying = mDelayInSecond;//存放当前时长，如58/57/56...
    private String mSendingFormatted = DEFAULT_FORMATTED;

    public SMSTextView(Context context) {
        this(context, null, 0);
    }

    public SMSTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SMSTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
        mCurrentColor = getCurrentTextColor();
        mCurrentHintColor = getCurrentHintTextColor();
        String str = getText().toString();
        if (!TextUtils.isEmpty(str)) {
            mCurrentText = str;
        } else {
            setText(mCurrentText = DEFAULT_NOT_FORMAT);
        }
        int gravity = getGravity();
        //水平或垂直方向上没有任何居中，则设置为默认全居中
        if ((gravity | Gravity.CENTER_HORIZONTAL) != Gravity.CENTER_HORIZONTAL
                && (gravity | Gravity.CENTER_VERTICAL) != Gravity.CENTER_VERTICAL) {
            setGravity(Gravity.CENTER);
        }
    }

    private void init(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.SMSTextView);
            mDelayInSecond = array.getInteger(R.styleable.SMSTextView_sms_internal_time_second, DEFAULT_DELAY);
            mSendingFormatted = array.getString(R.styleable.SMSTextView_sms_sending_formatted_text);
            if (TextUtils.isEmpty(mSendingFormatted)) mSendingFormatted = DEFAULT_FORMATTED;
            array.recycle();
        }
    }

    private void removeLastDelay() {
        HANDLER.removeCallbacks(mRunnable);
    }

    private void startDelay() {
        HANDLER.postDelayed(mRunnable, 1000);
    }

    public void setDelayInSecond(int delayInSecond) {
        mDelayInSecond = delayInSecond;
    }

    public void setSendingFormatted(String sendingFormatted) {
        mSendingFormatted = sendingFormatted;
        if (mDelaying != mDelayInSecond) setText(StringUtil.format(mSendingFormatted, mDelaying));
    }


    public void start() {
        setEnabled(false);
        removeLastDelay();
        setTextColor(mCurrentHintColor);
        startDelay();
    }

    public void reset() {
        mDelaying = mDelayInSecond;
        setText(mCurrentText);
        setTextColor(mCurrentColor);
        setEnabled(true);
    }

}
