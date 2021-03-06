package nu.annat.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import nu.annat.beholder.BeholderAdapter;
import nu.annat.beholder.ComponentFactory;
import nu.annat.beholder.ComponentFactory.Component;
import nu.annat.beholder.ComponentViewHolder;
import nu.annat.beholder.action.ActionHandler;
import nu.annat.beholder.action.OnAction;
import nu.annat.beholder.jsonconverter.Converter;
import nu.annat.beholder.presenter.ComponentInfo;
import nu.annat.example.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements Mockdata.Callback {

	private ActivityMainBinding binding;
	private ComponentViewHolder componentViewHolder;
	private ComponentFactory factory;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
		binding.list.setLayoutManager(new LinearLayoutManager(this));
		binding.setHandler(this);
		factory = getFactory();
		new Mockdata(this); // simulate data loading from network resource

	}

	@Override
	public void provide(List<ComponentInfo> data) {

		Converter converter = new Converter(factory);

		String json = "[{" +
			"'component':'DualLineData'," +
			"'data':{" +
			"'header':'weff'}}]";
		//	List<ComponentInfo> arr = converter.convert(json);

		List<ComponentInfo> arr = new ArrayList<>();
		arr.clear();
		arr.add(new ListLayoutPresenter(
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("first header", "first text"),
			new DualLineData("second header", "second text")
		));

		ActionHandler handler = getActionHandler();
		binding.list.swapAdapter(new BeholderAdapter<>(factory, arr, handler), false);

//		CardData componentInfos = new CardData(
//			new DualLineData("first header", "first text"),
//			new DualLineData("second header", "second text")
//		);
//
//		componentViewHolder = factory.create(componentInfos, binding.place, null);
//		binding.place.addView(componentViewHolder.itemView);

	}

	public void changeData(View view) {
		ComponentInfo presenter = componentViewHolder.getPresenter();
		presenter.getChildren().clear();
		presenter.add(new SingleLineData("second text"));
		presenter.add(new DualLineData("second header", "second text"));
		presenter.add(new DualLineData("third header", "third text"));
		factory.update(componentViewHolder, false);

	}

	public void changeData2(View view) {
		ComponentInfo presenter = componentViewHolder.getPresenter();
		presenter.getChildren().clear();
		presenter.add(new SingleLineData("third text"));
		factory.update(componentViewHolder, false);
	}

	private ActionHandler getActionHandler() {
		ActionHandler actionHandler = new ActionHandler();
		actionHandler.register(ToastAction.class, (OnAction<ToastAction>) this::executeToastAction);
		return actionHandler;
	}

	public void executeToastAction(ToastAction action) {
		action.data.setBody("clicked");
		Toast.makeText(MainActivity.this, action.getMessage(), Toast.LENGTH_SHORT).show();
	}

	private ComponentFactory getFactory() {
		// you can either send one, many or a collection in the constructor

		ComponentFactory factory = new ComponentFactory(
			new Component<>(SingleLineData.class, SingleLineComponent::new, R.layout.single_line_layout)
		);

		// or register one, many or a collection through the registerComponent or registerComponents
		factory.registerComponents(
			new Component<>(DualLineData.class, DualLineComponent::new, R.layout.dual_line_layout),
			//new Component(CardData.class, CardComponent.class, R.layout.card)
			new Component<>(CardData.class, CardComponent::new, R.layout.card),
			new Component<>(ListLayoutPresenter.class, ListLayoutComponent::new, R.layout.list_layout)
		);

		return factory;
	}

}
