package base.imonitore.com.br.mapsapplication.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ccouto on 27/11/2017.
 */

public class HandlerUpTimer {

    private long mDuration = 40000;//40segundos
    private Handler handlerTime;
    private Runnable runnableTime;
    private CountUpTimerUtils mTimer;
    private CountUpTimerCallback mCallback;

    public HandlerUpTimer(CountUpTimerCallback mCallback, long duration) {
        this.mCallback = mCallback;
        this.mDuration = duration;
    }

    public HandlerUpTimer(CountUpTimerCallback mCallback) {
        this.mCallback = mCallback;
    }

    public void startCountUpTimer() {
        handlerTime = new Handler(Looper.getMainLooper());
        runnableTime = new Runnable(){
            @Override
            public void run(){
                mTimer = new CountUpTimerUtils(mDuration) {
                    @Override
                    public void onTick(int second) {
                        mCallback.onTick(second);
                    }

                    @Override
                    public void onFinish(boolean finish) {
                        mCallback.onFinish();
                    }
                };
                mTimer.start();
            }
        };
        handlerTime.post(runnableTime);
    }

    public void finish(){
        mTimer.cancel = true;
    }
}
