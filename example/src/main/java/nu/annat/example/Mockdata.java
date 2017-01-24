package nu.annat.example;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import nu.annat.beholder.presenter.Presenter;

public class Mockdata implements Runnable {
    private final Callback callback;

    public interface Callback {
        void provide(List<Presenter> data);
    }

    public Mockdata(Callback callback) {
        this.callback = callback;
        AsyncTask.SERIAL_EXECUTOR.execute(this);
    }

    @Override
    public void run() {
        sleep(250);

        final List<Presenter> mock = new ArrayList<>();
        mock.add(new DualLineData("This is a beholder test", "with some subtext"));
        for (int i = 0; i < 5; i++) {
            mock.add(new SingleLineData("row " + i));
        }
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                callback.provide(mock);
            }
        });

    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
