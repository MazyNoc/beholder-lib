# beholder-lib

Populate RecyclerViews easily with Databinding and Beholder.

If you ever created a RecyclerView that should show a range of different list items you might have noticed that you often need a lot of 'plumbing' code to make that happen

The beholder lib is trying to help you reduce your codebase and makes it easier to present in a unified way.

Views are created from their registered presenter classes

Usage:

First you need a factory and some components. The easiest way to create one is to create and instance of ComponentFactory and then call registerComponent or registerComponents.

```
	private ComponentFactory getFactory() {
		// you can either send one, many or a collection in the constructor

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

```

As you can see, the component is build up of three parts.
First is a class that implements the Presenter interface.
Second is the Component class that provides the binding logic and also normally the callback handlers
The third part is a layout that utilizes databinding (more about that later)




```
class ListItem1 extends ComponentPresenter {
}

class ListItem1Component extends ComponentViewHolder<BinderClass, PresenterClass>{
}

ComponentFactory factory = new ComponentFactory(
    new ComponentInfo(ListItem1Component.class, R.layout.list_item_1, ListItem1.class)
  );

List<ComponentInfo> presenterList = new ArrayList<>();

presenterList.add(new ListItem1(listItem1Data));
presenterList.add(new ListItem2(listItem2Data));
presenterList.add(new ListItem3(listItem3Data));
presenterList.add(new ListItem4(listItem4Data));


recyclerView.setAdapter (new BeholderAdapter(factory, presenterList, null));
```

To simplify interactions, the library also contains an ActionHandler and OnAction interface.

The actionHandler forwards the action to the correct implementation of the OnAction interface depending on the Class type.
Since the ActionHandler will be accessible by all views created in the beholderAdapter you get an easy way to forward information down to where the handler was created.

```
class ToastAction {
    public final String message;
    public ToastAction(message: String) {
        this.message = message;
    }
}

ActionHandler actionHandler = new ActionHandler();

actionHandler.register(ToastAction.class, new OnAction<ToastAction>() {
			@Override
			public void execute(ToastAction action) {
				Toast.makeText(MainActivity.this, action.message, Toast.LENGTH_SHORT).show();
			}
		});


// and in the Component
class ListItem1Component extends ComponentViewHolder<BinderClass, PresenterClass>{

	@Override
	protected void prepareView() {
		binding.setHandler(this);
	}

    // bound via DataBinding
    public void onCardClick(View view){
        // act is a convinience method that nullchecks the actionHandler before calling the execute function.
		act(new ToastAction(presenter.getHeader()));
	}
}


```


