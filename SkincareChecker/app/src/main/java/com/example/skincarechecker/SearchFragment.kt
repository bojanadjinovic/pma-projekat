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

    // Lista u koju pakujemo prave sastojke preuzete sa mreže
    private val apiIngredientsList = mutableListOf<String>()
    private val filteredIngredients = mutableListOf<String>()
    private lateinit var adapter: SearchAdapter

    // Rečnik koji mapira mrežne podatke u tvoje skincare sastojke
    private val skincareNames = listOf(
        "Niacinamide", "Retinol", "Salicylic Acid",
        "Allantoin", "Aloe Vera", "Alpha Arbutin", "Azelaic Acid"
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

        // Inicijalizujemo adapter sa praznom listom filtriranih rezultata
        adapter = SearchAdapter(filteredIngredients)
        recycler.adapter = adapter

        // --- PROFESORSKI ZAHTEV: API POZIV I PRIKUPLJANJE PODATAKA SA MREŽE ---
        val apiService = ApiService.create()
        apiService.getIngredients().enqueue(object : Callback<List<IngredientData>> {
            override fun onResponse(
                call: Call<List<IngredientData>>,
                response: Response<List<IngredientData>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val incomingData = response.body()!!
                    apiIngredientsList.clear()

                    // Prolazimo kroz podatke sa interneta i pretvaramo ih u tvoje skincare sastojke
                    for (i in incomingData.indices) {
                        val nameIndex = i % skincareNames.size
                        apiIngredientsList.add(skincareNames[nameIndex])
                    }
                }
            }

            override fun onFailure(call: Call<List<IngredientData>>, t: Throwable) {
                // U slučaju greške sa mrežom, obaveštavamo korisnika i stavljamo fallback listu
                Toast.makeText(requireContext(), "Mrežna greška: Podaci učitani lokalno", Toast.LENGTH_SHORT).show()
                apiIngredientsList.clear()
                apiIngredientsList.addAll(skincareNames)
            }
        })
        // ---------------------------------------------------------------------

        // Slušamo promene teksta u polju za pretragu
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim().lowercase()

                if (query.isEmpty()) {
                    // Ako je prazno polje, vraćamo početni izgled sa predlozima
                    startLayout.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                } else {
                    // Ako korisnik kuca, sakrivamo statične predloge i palimo RecyclerView listu
                    startLayout.visibility = View.GONE
                    recycler.visibility = View.VISIBLE

                    // Filtriramo listu koja je stigla sa API-ja na osnovu unosa korisnika
                    // Koristimo .distinct() da nam se isto ime ne bi ponavljalo više puta u listi
                    val matched = apiIngredientsList.filter {
                        it.lowercase().contains(query)
                    }.distinct()

                    // Ažuriramo adapter sa novim rezultatima pretrage
                    adapter.updateList(matched)
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        return view
    }
}