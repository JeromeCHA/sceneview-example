package cha.jerome.sceneviewexample.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import cha.jerome.sceneviewexample.databinding.RowSceneviewBinding
import com.google.android.filament.Engine

class ListAdapter(
    private val sharedEngine: Engine,
    private val list: List<Int>,
) : RecyclerView.Adapter<SceneviewViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SceneviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowSceneviewBinding.inflate(inflater, parent, false)
        return SceneviewViewHolder(binding, sharedEngine)
    }

    override fun onBindViewHolder(holder: SceneviewViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}

