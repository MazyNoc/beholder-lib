package nu.annat.example;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.List;

import nu.annat.beholder.presenter.ComponentInfo;

public class Mockdata implements Runnable {
	private final Callback callback;

	public interface Callback {
		void provide(List<ComponentInfo> data);
	}

	public Mockdata(Callback callback) {
		this.callback = callback;
		AsyncTask.SERIAL_EXECUTOR.execute(this);
	}

	@Override
	public void run() {
		sleep(250);

		final List<ComponentInfo> mock = new ArrayList<>();
//		mock.add(new DualLineData("This is a beholder test", "with some subtext"));
//		for (int i = 0; i < 5; i++) {
//			mock.add(new SingleLineData("row " + i));
//		}

		for (int i = 0; i < 2; i++) {
			mock.add(new CardData(
				new DualLineData("inside a card", "With children"),
				new SingleLineData("child 1"),
				new SingleLineData("child 2"),
				new CardData(new DualLineData("inside a card", "With children"))
			));
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
