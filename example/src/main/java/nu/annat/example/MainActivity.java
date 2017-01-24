package nu.annat.example;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import java.util.List;

import nu.annat.beholder.BeholderAdapter;
import nu.annat.beholder.ComponentFactory;
import nu.annat.beholder.ComponentFactory.ComponentInfo;
import nu.annat.beholder.presenter.Presenter;
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
    public void provide(List<Presenter> data) {
        ComponentFactory factory = getFactory();
        binding.list.swapAdapter(new BeholderAdapter(factory, data, null), false);
    }

    private ComponentFactory getFactory() {
        // you can either send one, many or a collection in the constructor

        ComponentFactory factory = new ComponentFactory(
            new ComponentInfo(SingleLineComponent.class, R.layout.single_line_layout, SingleLineData.class)
        );

        // or register one, many or a collection through the registerComponent or registerComponents
        factory.registerComponents(
            new ComponentInfo(DualLineComponent.class, R.layout.dual_line_layout, DualLineData.class)
        );

        return factory;
    }
}
