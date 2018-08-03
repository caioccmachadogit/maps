package base.imonitore.com.br.mapsapplication.utils;

/**
 * Created by ccouto on 16/10/2017.
 */

import android.os.CountDownTimer;

public abstract class CountUpTimerUtils extends CountDownTimer {
    private static final long INTERVAL_MS = 1000;//1s
    private final long duration;
    public boolean cancel;

    protected CountUpTimerUtils(long durationMs) {
        super(durationMs, INTERVAL_MS);
        this.duration = durationMs;
    }

    protected CountUpTimerUtils(long durationMs, long intervalMs) {
        super(durationMs, intervalMs);
        this.duration = durationMs;
    }

    public abstract void onTick(int second);

    public abstract void onFinish(boolean finish);

    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((duration - msUntilFinished) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
        onFinish(true);
    }
}
