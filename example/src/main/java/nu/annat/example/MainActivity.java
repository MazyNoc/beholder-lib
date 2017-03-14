package nu.annat.example;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.List;

import nu.annat.beholder.BeholderAdapter;
import nu.annat.beholder.ComponentFactory;
import nu.annat.beholder.ComponentFactory.Component;
import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.action.OnAction;
import nu.annat.beholder.jsonconverter.Converter;
import nu.annat.beholder.presenter.ComponentInfo;
import nu.annat.example.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements Mockdata.Callback {

	private ActivityMainBinding binding;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		binding.list.setLayoutManager(new LinearLayoutManager(this));
		new Mockdata(this); // simulate data loading from network resource

	}

	@Override
	public void provide(List<ComponentInfo> data) {
		ComponentFactory factory = getFactory();
		Converter converter = new Converter(factory);

		String json = "[{" +
			"'component':'DualLineData'," +
			"'data':{" +
			"'header':'weff'}}]";
		List<ComponentInfo> arr = converter.convert(json);

		ActionHandler handler = getActionHandler();
		binding.list.swapAdapter(new BeholderAdapter(factory, arr, handler), false);
	}

	private ActionHandler getActionHandler() {
		ActionHandler actionHandler = new ActionHandler();
		actionHandler.register(ToastAction.class, new OnAction<ToastAction>() {
			@Override
			public void execute(ToastAction action) {
				Toast.makeText(MainActivity.this, action.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
		return actionHandler;
	}

	private ComponentFactory getFactory() {
		// you can either send one, many or a collection in the constructor

		ComponentFactory factory = new ComponentFactory(
			new Component(SingleLineData.class, SingleLineComponent.class, R.layout.single_line_layout)
		);

		// or register one, many or a collection through the registerComponent or registerComponents
		factory.registerComponents(
			new Component(DualLineData.class, DualLineComponent.class, R.layout.dual_line_layout),
			new Component(CardData.class, CardComponent.class, R.layout.card)
		);

		return factory;
	}

}
