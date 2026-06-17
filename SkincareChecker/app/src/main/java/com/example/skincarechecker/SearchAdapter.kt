package com.example.skincarechecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class SearchAdapter(
    private val items: MutableList<String>
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvIngredientName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_search, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val ingredientName = items[position]
        holder.name.text = ingredientName

        // Klik na stavku otvara ekran sa detaljima i pomera donju navigaciju
        holder.itemView.setOnClickListener { view ->
            val context = view.context as AppCompatActivity
            val learnFragment = LearnFragment()

            val bundle = Bundle().apply {
                putString("ingredient_name", ingredientName)
            }
            learnFragment.arguments = bundle

            // Menjamo fragment na ekranu
            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, learnFragment)
                .commit()

            // Sređivanje donje navigacije da se lampica pomeri na 'learn'
            val bottomNav = context.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.navbar)
            bottomNav.selectedItemId = R.id.learn
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<String>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}