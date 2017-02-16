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

As you can see, the component is build up of three parts. First is the Component class that provides the binding logic and also normally the callback handlers
The second part is a layout that utilizes databinding (more about that later)
The third part is a class that implements the Presenter interface.



```
class ListItem1 extends AbstractPresenter(){
}

Class ListItem1Component extends ComponentViewHolder<BinderClass, PresenterClass>{
}

class Myfactory = new ComponentFactory(){
  public MyFactory(){
    registerComponents(
      new ComponentInfo(ListItem1Component.class, R.layout.list_item_1, ListItem1.class),
      ....
  }
}

ParentPresenter basePresenters = new ParentPresenter()

basePresenters.add(new listItem1(listItem1Data));
basePresenters.add(new listItem2(listItem1Data));
basePresenters.add(new listItem3(listItem1Data));
basePresenters.add(new listItem4(listItem1Data));


recyclerView.setAdapter (new BeholderAdapter(factory, basePresenters, null));
```
