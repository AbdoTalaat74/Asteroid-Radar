package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidDataListBinding

class MainAdapter(private val clickListener: AsteroidClickListener) :
    ListAdapter<Asteroid, MainAdapter.MainViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val asteroidProperty = getItem(position)
        holder.bind(clickListener,asteroidProperty)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {


        return MainViewHolder(
            AsteroidDataListBinding.inflate(
                (LayoutInflater.from(parent.context)),
                parent, false
            )
        )
    }
//    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
//        val layoutInflater = LayoutInflater.from(parent.context)
//        val binding = AsteroidDataListBinding.inflate(layoutInflater,parent,false)
//        return MainViewHolder(binding)
//    }


    class MainViewHolder(var binding: AsteroidDataListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: AsteroidClickListener, asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }


}

class DiffCallback : DiffUtil.ItemCallback<Asteroid>() {
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem == newItem
    }

}

class AsteroidClickListener(val clickListener: (asteroid: Asteroid) -> Unit){
    fun asteroidOnClick(asteroid: Asteroid) = clickListener(asteroid)
}
