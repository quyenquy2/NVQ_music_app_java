package com.quyen.musicapp.services;

import android.os.CountDownTimer;

public class OtpTimer {
    private static final long COUNTDOWN_INTERVAL = 1000; // 1 giây
    private static final long OTP_EXPIRATION_TIME = 60 * 1000; // 60 giây

    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private OnOtpTimerListener onOtpTimerListener;

    public void startTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }

        countDownTimer = new CountDownTimer(OTP_EXPIRATION_TIME, COUNTDOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                if (onOtpTimerListener != null) {
                    onOtpTimerListener.onTimerTick(secondsRemaining);
                }
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                if (onOtpTimerListener != null) {
                    onOtpTimerListener.onTimerFinish();
                }
            }
        };

        countDownTimer.start();
        isTimerRunning = true;
    }

    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            isTimerRunning = false;
        }
    }

    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    public void setOnOtpTimerListener(OnOtpTimerListener listener) {
        onOtpTimerListener = listener;
    }

    public interface OnOtpTimerListener {
        void onTimerTick(long secondsRemaining);

        void onTimerFinish();
    }
}
