package nu.annat.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nu.annat.beholder.presenter.Presenter;

public class Mockdata {
	public interface Callback {
		void provide(List<Presenter> data);
	}

	public Mockdata(Callback callback) {
		List<Presenter> mock = new ArrayList<>();
		mock.add(new DualLineData("This is a beholder test", "with some subtext"));
		for (int i = 0; i < 5; i++) {
			mock.add(new SingleLineData("row " + i));
		}
		callback.provide(mock);
	}
}
