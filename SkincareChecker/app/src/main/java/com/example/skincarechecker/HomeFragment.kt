package com.example.skincarechecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.skincarechecker.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        view.findViewById<TextView>(R.id.tvWelcome)?.text = "Welcome back!"

        // Learn more dugme
        val btnLearnMore = view.findViewById<Button>(R.id.btnLearnMore)
        btnLearnMore?.setOnClickListener {
            openLearnFragment("Niacinamide")
        }

        // Skin Type dugmici
        val btnOily = view.findViewById<TextView>(R.id.btnOily)
        val btnSensitive = view.findViewById<TextView>(R.id.btnSensitive)
        val btnDry = view.findViewById<TextView>(R.id.btnDry)

        btnOily?.setOnClickListener {
            highlightButton(btnOily, btnSensitive, btnDry)
            openSearchWithFilter("Oily")
        }

        btnSensitive?.setOnClickListener {
            highlightButton(btnSensitive, btnOily, btnDry)
            openSearchWithFilter("Sensitive")
        }

        btnDry?.setOnClickListener {
            highlightButton(btnDry, btnOily, btnSensitive)
            openSearchWithFilter("Dry")
        }

        return view
    }

    private fun highlightButton(selected: TextView, vararg others: TextView) {
        selected.setBackgroundColor(android.graphics.Color.parseColor("#A8A86A"))
        selected.setTextColor(android.graphics.Color.parseColor("#FFFFFF"))
        others.forEach {
            it.setBackgroundColor(android.graphics.Color.parseColor("#EFE8B8"))
            it.setTextColor(android.graphics.Color.parseColor("#6B6B4E"))
        }
    }

    private fun openSearchWithFilter(skinType: String) {
        val searchFragment = SearchFragment()
        val bundle = Bundle().apply {
            putString("skin_type_filter", skinType)
        }
        searchFragment.arguments = bundle

        val activity = context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, searchFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun openLearnFragment(ingredientName: String) {
        val learnFragment = LearnFragment()
        val bundle = Bundle().apply {
            putString("ingredient_name", ingredientName)
        }
        learnFragment.arguments = bundle

        val activity = context as AppCompatActivity
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, learnFragment)
            .addToBackStack(null)
            .commit()


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}