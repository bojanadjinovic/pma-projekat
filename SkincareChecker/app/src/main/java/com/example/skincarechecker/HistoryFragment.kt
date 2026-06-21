package com.example.skincarechecker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_history, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerHistory)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        // Citamo istoriju iz SharedPreferences
        val prefs = requireContext().getSharedPreferences("history", Context.MODE_PRIVATE)
        val historySet = prefs.getStringSet("viewed", emptySet()) ?: emptySet()
        val historyList = historySet.toMutableList()

        if (historyList.isEmpty()) {
            historyList.add("Još nema pregledanih sastojaka")
        }

        recycler.adapter = SearchAdapter(historyList)
        return view
    }

    companion object {
        fun addToHistory(context: Context, ingredientName: String) {
            val prefs = context.getSharedPreferences("history", Context.MODE_PRIVATE)
            val current = prefs.getStringSet("viewed", mutableSetOf())?.toMutableSet() ?: mutableSetOf()
            current.add(ingredientName)
            prefs.edit().putStringSet("viewed", current).apply()
        }
    }
}