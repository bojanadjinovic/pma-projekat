package com.example.skincarechecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class LearnFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_learn, container, false)

        // Povezujemo se sa komponentama preko tačnih ID-jeva iz fragment_learn.xml
        val tvTitle = view.findViewById<TextView>(R.id.tvIngredientTitle)
        val tvDescription = view.findViewById<TextView>(R.id.tvIngredientDesc)

        // Prihvata ime sastojka koji je kliknut (ako nema ništa, podrazumevano je Niacinamide)
        val ingredientName = arguments?.getString("ingredient_name") ?: "Niacinamide"

        tvTitle.text = ingredientName
        tvDescription.text = "Details and benefits about $ingredientName will be shown here."

        return view
    }
}