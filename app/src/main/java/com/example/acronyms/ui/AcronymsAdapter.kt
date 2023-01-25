package com.example.acronyms.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.acronyms.data.Acronyms
import com.example.acronyms.databinding.AdapterAcronymsBinding
import javax.inject.Inject


class AcronymsAdapter @Inject constructor() : RecyclerView.Adapter<AcronymsAdapter.AcronymViewHolder>() {

    var acronyms = mutableListOf<Acronyms>()
    private var clickInterface: ClickInterface<Acronyms>? = null

    fun updateAcronyms(acronyms: List<Acronyms>) {
        notifyItemRangeRemoved(0, itemCount);
        this.acronyms = acronyms.toMutableList()
        notifyItemRangeInserted(0, acronyms.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcronymViewHolder {
        val binding =
            AdapterAcronymsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AcronymViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AcronymViewHolder, position: Int) {
        val acronym = acronyms[position]
        holder.view.tvTitle.text = acronym.lf
        holder.view.tvSince.text = "Since : ${acronym.since}"
        holder.view.tvOccurrences.text = "Occurrences : ${acronym.freq}"
        holder.view.acronymsCard.setOnClickListener {
            clickInterface?.onClick(acronym)
        }
    }

    override fun getItemCount(): Int {
        return acronyms.size
    }

    fun setItemClick(clickInterface: ClickInterface<Acronyms>) {
        this.clickInterface = clickInterface
    }

    class AcronymViewHolder(val view: AdapterAcronymsBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}