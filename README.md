# beholder-lib

Populate RecyclerViews easily with Databinding and Beholder.


Usage:

First you need a factory and some components. The easiest way to create one is to create and instance of ComponentFactory and then call registerComponent or registerComponents.

```
ComponentFactory factory = new ComponentFactory();
factory.registerComponent(ListItem1Component.class, R.layout.list_item_1, ListItem1Presenter.class);

factory.registerComponent(
  new ComponentInformation(ListItem2Component.class, R.layout.list_item_2, ListItem2Presenter.class)
);

factort.registerComponents(
  new ComponentInformation(ListItem3Component.class, R.layout.list_item_3, ListItem3Presenter.class),
  new ComponentInformation(ListItem4Component.class, R.layout.list_item_4, ListItem4Presenter.class)
)
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
