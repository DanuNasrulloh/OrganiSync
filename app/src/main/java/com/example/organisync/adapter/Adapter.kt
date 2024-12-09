package com.example.organisync.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.organisync.databinding.NewsitemBinding
import com.example.organisync.model.EventAcara
import com.example.organisync.model.News


class Adapter : RecyclerView.Adapter<Adapter.UserViewHolder>() {

    private val list = ArrayList<News>()
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<News>) {
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    

    inner class UserViewHolder(private val binding: NewsitemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: News) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            binding.apply {
                Glide.with(itemView)
                    .load(user.photo)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivNewsImage)
                tvNewsLabel.text = user.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = NewsitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback {
        fun onItemClicked(data: News)
    }
}