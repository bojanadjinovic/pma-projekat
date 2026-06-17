package com.example.skincarechecker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private val apiIngredientsList = mutableListOf<String>()
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        val etSearch = view.findViewById<EditText>(R.id.etSearch)
        val startLayout = view.findViewById<View>(R.id.searchStartLayout)
        val recycler = view.findViewById<RecyclerView>(R.id.recyclerSearch)

        recycler.layoutManager = LinearLayoutManager(requireContext())
        adapter = SearchAdapter(filteredIngredients)
        recycler.adapter = adapter

        apiIngredientsList.addAll(skincareNames)

        val apiService = ApiService.create()
        apiService.getIngredients().enqueue(object : Callback<List<IngredientData>> {
            override fun onResponse(
                call: Call<List<IngredientData>>,
                response: Response<List<IngredientData>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    apiIngredientsList.clear()
                    apiIngredientsList.addAll(skincareNames)
                }
            }

            override fun onFailure(call: Call<List<IngredientData>>, t: Throwable) {
                Toast.makeText(requireContext(), "Podaci učitani lokalno", Toast.LENGTH_SHORT).show()
            }
        })

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()

                if (query.isEmpty()) {
                    startLayout.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                } else {
                    startLayout.visibility = View.GONE
                    recycler.visibility = View.VISIBLE

                    val matched = skincareNames.filter {
                        it.lowercase().contains(query)
                    }.sortedWith(compareBy {
                        if (it.lowercase().startsWith(query)) 0 else 1
                    }).distinct()

                    adapter.updateList(matched)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}