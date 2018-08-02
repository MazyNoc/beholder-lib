package nu.annat.example

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nu.annat.beholder.ComponentAdapterViewHolder
import nu.annat.beholder.presenter.ComponentData
import nu.annat.beholder.presenter.ComponentInfo
import nu.annat.beholder.presenter.ObservablePresenter
import nu.annat.example.databinding.ListLayoutBinding

class ListLayoutPresenter(vararg val componentInfo: ComponentInfo) : ObservablePresenter() {
    override fun getChildren(): MutableList<ComponentInfo> {
        return componentInfo.toMutableList()
    }
}

class ListLayoutComponent(baseData: ComponentData?) : ComponentAdapterViewHolder<ListLayoutBinding, ListLayoutPresenter, Any>(baseData) {
    override fun getRecyclerView(): RecyclerView {
        return binding.list
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun prepareView() {
        super.prepareView()
        binding.list.layoutManager = LinearLayoutManager(binding.list.context, LinearLayoutManager.HORIZONTAL, false)
    }

    override fun prepareData() {
        super.prepareData()
        binding.presenter = presenter
    }
}
