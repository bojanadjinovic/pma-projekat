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

    private val descriptions = mapOf(
        "Niacinamide" to "Controls oil, minimizes pores and strengthens skin barrier.",
        "Retinol" to "Anti-aging ingredient that boosts collagen and cell turnover.",
        "Salicylic Acid" to "Unclogs pores and prevents breakouts. Great for acne.",
        "Allantoin" to "Soothes irritation, calms redness and supports skin healing.",
        "Aloe Vera" to "Hydrates and calms the skin. Great for sensitive skin.",
        "Alpha Arbutin" to "Brightens skin tone and reduces dark spots.",
        "Azelaic Acid" to "Reduces redness, acne and evens skin tone.",
        "Hyaluronic Acid" to "Deep hydration that plumps and moisturizes the skin.",
        "Vitamin C" to "Brightens skin and protects against free radicals.",
        "Glycolic Acid" to "Exfoliates dead skin cells and improves texture.",
        "Lactic Acid" to "Gentle exfoliant that hydrates and smooths skin.",
        "Ceramides" to "Restores and strengthens the skin barrier.",
        "Peptides" to "Boost collagen production and firm the skin.",
        "Zinc" to "Controls oil and has anti-inflammatory properties.",
        "Benzoyl Peroxide" to "Kills acne-causing bacteria effectively.",
        "Kojic Acid" to "Fades dark spots and brightens skin tone.",
        "Ferulic Acid" to "Antioxidant that enhances Vitamin C effectiveness.",
        "Squalane" to "Lightweight oil that hydrates without clogging pores.",
        "Centella Asiatica" to "Calms inflammation and supports skin healing.",
        "Green Tea Extract" to "Antioxidant that soothes and protects the skin."
    )

    private val skinTypes = mapOf(
        "Niacinamide" to "Oily",
        "Retinol" to "Dry",
        "Salicylic Acid" to "Oily",
        "Allantoin" to "Sensitive",
        "Aloe Vera" to "Sensitive",
        "Alpha Arbutin" to "All",
        "Azelaic Acid" to "Oily",
        "Hyaluronic Acid" to "Dry",
        "Vitamin C" to "All",
        "Glycolic Acid" to "Oily",
        "Lactic Acid" to "Dry",
        "Ceramides" to "Dry",
        "Peptides" to "All",
        "Zinc" to "Oily",
        "Benzoyl Peroxide" to "Oily",
        "Kojic Acid" to "All",
        "Ferulic Acid" to "All",
        "Squalane" to "Dry",
        "Centella Asiatica" to "Sensitive",
        "Green Tea Extract" to "Sensitive"
    )

    class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvIngredientName)
        val desc: TextView = itemView.findViewById(R.id.tvIngredientDesc)
        val tag: TextView = itemView.findViewById(R.id.tvSkinTypeTag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredient, parent, false)
        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val ingredientName = items[position]
        holder.name.text = ingredientName
        holder.desc.text = descriptions[ingredientName] ?: "Skincare ingredient."
        holder.tag.text = skinTypes[ingredientName] ?: "All"

        holder.itemView.setOnClickListener { view ->
            val context = view.context as AppCompatActivity
            val learnFragment = LearnFragment()
            val bundle = Bundle().apply {
                putString("ingredient_name", ingredientName)
            }
            learnFragment.arguments = bundle

            context.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, learnFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = items.size

    fun updateList(newList: List<String>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }
}