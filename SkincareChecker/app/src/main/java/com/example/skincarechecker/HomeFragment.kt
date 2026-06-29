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
        // Compatibility checker
        val spinnerIngredient1 = view.findViewById<android.widget.Spinner>(R.id.spinnerIngredient1)
        val spinnerIngredient2 = view.findViewById<android.widget.Spinner>(R.id.spinnerIngredient2)
        val btnCheck = view.findViewById<android.widget.Button>(R.id.btnCheckCompatibility)
        val tvResult = view.findViewById<TextView>(R.id.tvCompatibilityResult)

        val ingredients = listOf(
            "Niacinamide", "Retinol", "Vitamin C", "Salicylic Acid",
            "AHA/BHA", "Benzoyl Peroxide", "Hyaluronic Acid", "Peptides"
        )

        val adapterSpinner = android.widget.ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            ingredients
        )
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerIngredient1.adapter = adapterSpinner
        spinnerIngredient2.adapter = adapterSpinner

        val compatibility = mapOf(
            "Niacinamide+Retinol" to Pair(true, "✅ Great combo! Niacinamide reduces irritation from Retinol."),
            "Niacinamide+Vitamin C" to Pair(false, "⚠️ Use separately. Can reduce effectiveness of Vitamin C."),
            "Retinol+Vitamin C" to Pair(false, "❌ Avoid! Both are potent — can cause irritation."),
            "Retinol+AHA/BHA" to Pair(false, "❌ Too strong together! Can cause redness and peeling."),
            "Retinol+Benzoyl Peroxide" to Pair(false, "❌ Avoid! They cancel each other out."),
            "Vitamin C+Hyaluronic Acid" to Pair(true, "✅ Perfect combo! Vitamin C brightens, HA hydrates."),
            "Salicylic Acid+Niacinamide" to Pair(true, "✅ Great for oily skin! Clears pores and calms skin."),
            "Salicylic Acid+Benzoyl Peroxide" to Pair(false, "⚠️ Too drying together. Use one at a time."),
            "Hyaluronic Acid+Retinol" to Pair(true, "✅ Good combo! HA hydrates and reduces Retinol irritation."),
            "Peptides+Vitamin C" to Pair(true, "✅ Great anti-aging combo!"),
            "Peptides+AHA/BHA" to Pair(false, "⚠️ AHA/BHA can break down peptides. Use separately.")
        )

        btnCheck.setOnClickListener {
            val ing1 = spinnerIngredient1.selectedItem.toString()
            val ing2 = spinnerIngredient2.selectedItem.toString()

            if (ing1 == ing2) {
                tvResult.text = "⚠️ Please select two different ingredients!"
                tvResult.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val key1 = "$ing1+$ing2"
            val key2 = "$ing2+$ing1"
            val result = compatibility[key1] ?: compatibility[key2]

            if (result != null) {
                tvResult.text = result.second
            } else {
                tvResult.text = "✅ These ingredients are generally safe to use together."
            }
            tvResult.visibility = View.VISIBLE
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