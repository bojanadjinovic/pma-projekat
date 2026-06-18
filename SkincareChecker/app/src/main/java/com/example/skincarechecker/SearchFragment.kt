package com.example.skincarechecker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private val filteredIngredients = mutableListOf<String>()
    private lateinit var adapter: SearchAdapter

    private val skincareNames = listOf(
        "Niacinamide", "Retinol", "Salicylic Acid",
        "Allantoin", "Aloe Vera", "Alpha Arbutin", "Azelaic Acid",
        "Hyaluronic Acid", "Vitamin C", "Glycolic Acid",
        "Lactic Acid", "Ceramides", "Peptides", "Zinc",
        "Benzoyl Peroxide", "Kojic Acid", "Ferulic Acid",
        "Squalane", "Centella Asiatica", "Green Tea Extract"
    )

    private val skinTypeMap = mapOf(
        "Oily" to listOf("Niacinamide", "Salicylic Acid", "Azelaic Acid", "Glycolic Acid", "Zinc", "Benzoyl Peroxide"),
        "Sensitive" to listOf("Allantoin", "Aloe Vera", "Centella Asiatica", "Green Tea Extract", "Lactic Acid"),
        "Dry" to listOf("Hyaluronic Acid", "Ceramides", "Squalane", "Retinol", "Lactic Acid", "Peptides")
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val startLayout = view.findViewById<View>(R.id.searchStartLayout)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerSearch)
        val btnOily = view.findViewById<TextView>(R.id.btnSearchOily)
        val btnSensitive = view.findViewById<TextView>(R.id.btnSearchSensitive)
        val btnDry = view.findViewById<TextView>(R.id.btnSearchDry)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(filteredIngredients)
        recycler.adapter = adapter

        val skinTypeFilter = arguments?.getString("skin_type_filter")
        if (skinTypeFilter != null) {
            val filtered = skinTypeMap[skinTypeFilter] ?: emptyList()
            startLayout.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            adapter.updateList(filtered.toMutableList())
        }

        // Ucitavamo API
        ApiService.create().getIngredients().enqueue(object : Callback<List<IngredientData>> {
            override fun onResponse(call: Call<List<IngredientData>>, response: Response<List<IngredientData>>) {}
            override fun onFailure(call: Call<List<IngredientData>>, t: Throwable) {
                Toast.makeText(requireContext(), "Podaci učitani lokalno", Toast.LENGTH_SHORT).show()
            }
        })

        fun showList(list: List<String>) {
            startLayout.visibility = View.GONE
            recycler.visibility = View.VISIBLE
            adapter.updateList(list.toMutableList())
        }

        fun highlightButton(selected: TextView, vararg others: TextView) {
            selected.setBackgroundColor(android.graphics.Color.parseColor("#A8A86A"))
            selected.setTextColor(android.graphics.Color.WHITE)
            others.forEach {
                it.setBackgroundColor(android.graphics.Color.parseColor("#EFE8B8"))
                it.setTextColor(android.graphics.Color.parseColor("#6B6B4E"))
            }
        }

        btnOily.setOnClickListener {
            highlightButton(btnOily, btnSensitive, btnDry)
            showList(skinTypeMap["Oily"]!!)
        }

        btnSensitive.setOnClickListener {
            highlightButton(btnSensitive, btnOily, btnDry)
            showList(skinTypeMap["Sensitive"]!!)
        }

        btnDry.setOnClickListener {
            highlightButton(btnDry, btnOily, btnSensitive)
            showList(skinTypeMap["Dry"]!!)
        }

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()
                if (query.isEmpty()) {
                    startLayout.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                } else {
                    val matched = skincareNames.filter {
                        it.lowercase().contains(query)
                    }.sortedWith(compareBy {
                        if (it.lowercase().startsWith(query)) 0 else 1
                    })
                    showList(matched)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}