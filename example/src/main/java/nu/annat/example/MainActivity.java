package nu.annat.example;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import java.util.List;

import nu.annat.beholder.ActionHandler;
import nu.annat.beholder.BeholderAdapter;
import nu.annat.beholder.ComponentFactory;
import nu.annat.beholder.ComponentFactory.Component;
import nu.annat.beholder.OnAction;
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
		ActionHandler handler = getActionHandler();
		binding.list.swapAdapter(new BeholderAdapter(factory, data, handler), false);
	}

	private ActionHandler getActionHandler() {
		ActionHandler actionHandler = new ActionHandler();
		actionHandler.register(ToastAction.class, new OnAction<ToastAction>() {
			@Override
			public <T> T executeAction(ToastAction action) {
				Toast.makeText(MainActivity.this, action.getMessage(), Toast.LENGTH_SHORT).show();
				return null;
			}
		});
		return actionHandler;
	}

	private ComponentFactory getFactory() {
		// you can either
		// er send one, many or a collection in the constructor

		ComponentFactory factory = new ComponentFactory(
			new Component(SingleLineComponent.class, R.layout.single_line_layout, SingleLineData.class)
		);

		// or register one, many or a collection through the registerComponent or registerComponents
		factory.registerComponents(
			new Component(DualLineComponent.class, R.layout.dual_line_layout, DualLineData.class),
			new Component(CardComponent.class, R.layout.card, CardData.class)
		);

		return factory;
	}

}
