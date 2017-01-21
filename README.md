# beholder-lib

Populate RecyclerViews easily with Databinding and Beholder.


Usage:

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
