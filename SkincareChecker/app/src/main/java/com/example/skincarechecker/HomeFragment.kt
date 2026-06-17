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

        // REŠENJE ZA WELCOME TEKST: Ako u XML-u imaš tvWelcome, skloniti čudne simbole
        // Ako ti se XML buni za ovaj red, slobodno ga zakomentariši
        view.findViewById<TextView>(R.id.tvWelcome)?.text = "Welcome back!"

        // PRONAĐI DUGME: Tražimo tvoje "Learn more" dugme unutar kartice
        // Proveri da li ti se dugme u fragment_home.xml tačno zove btnLearnMore ili slično
        val btnLearnMore = view.findViewById<Button>(R.id.btnLearnMore)

        btnLearnMore?.setOnClickListener {
            val learnFragment = LearnFragment()

            // Pakujemo Niacinamide pošto je on "Ingredient of the Day" na tvojoj slici
            val bundle = Bundle().apply {
                putString("ingredient_name", "Niacinamide")
            }
            learnFragment.arguments = bundle

            // Otvaramo LearnFragment
            val activity = context as AppCompatActivity
            activity.supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, learnFragment)
                .commit()

            // Pomeramo i donju navigaciju na 'learn' tab
            val bottomNav = activity.findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.navbar)
            bottomNav.selectedItemId = R.id.learn
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}