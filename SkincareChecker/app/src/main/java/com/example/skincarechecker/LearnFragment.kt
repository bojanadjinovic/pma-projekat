package com.example.skincarechecker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment

class LearnFragment : Fragment() {

    data class IngredientInfo(
        val title: String,
        val description: String,
        val benefits: String,
        val skinType: String
    )

    private val ingredientDatabase = mapOf(
        "Niacinamide" to IngredientInfo(
            "Niacinamide",
            "Niacinamide, also known as Vitamin B3, is a multi-purpose skincare ingredient that helps strengthen the skin barrier and improve overall skin health.",
            "✔ Controls excess oil and sebum\n✔ Minimizes the appearance of pores\n✔ Strengthens the skin barrier\n✔ Reduces redness and inflammation\n✔ Evens out skin tone",
            "Oily / Combination"
        ),
        "Retinol" to IngredientInfo(
            "Retinol",
            "Retinol is a Vitamin A derivative and one of the most researched anti-aging ingredients available.",
            "✔ Boosts collagen production\n✔ Speeds up cell turnover\n✔ Reduces fine lines and wrinkles\n✔ Fades dark spots\n✔ Improves skin texture",
            "Dry / Mature"
        ),
        "Salicylic Acid" to IngredientInfo(
            "Salicylic Acid",
            "Salicylic acid is a beta-hydroxy acid (BHA) that penetrates deep into pores to dissolve excess oil and dead skin cells.",
            "✔ Unclogs and minimizes pores\n✔ Prevents and treats acne\n✔ Reduces blackheads\n✔ Exfoliates dead skin cells\n✔ Controls oiliness",
            "Oily / Acne-prone"
        ),
        "Allantoin" to IngredientInfo(
            "Allantoin",
            "Allantoin is a skin-protecting ingredient derived from plants that helps calm irritation and support skin healing.",
            "✔ Soothes irritation and redness\n✔ Supports skin healing\n✔ Hydrates and softens skin\n✔ Improves skin texture\n✔ Strengthens the skin barrier",
            "Sensitive / All"
        ),
        "Aloe Vera" to IngredientInfo(
            "Aloe Vera",
            "Aloe vera is a natural ingredient with powerful soothing and hydrating properties, used for centuries in skincare.",
            "✔ Calms and soothes irritated skin\n✔ Provides lightweight hydration\n✔ Reduces redness\n✔ Helps with sunburn recovery\n✔ Anti-inflammatory properties",
            "Sensitive / All"
        ),
        "Alpha Arbutin" to IngredientInfo(
            "Alpha Arbutin",
            "Alpha Arbutin is a brightening ingredient that works by inhibiting melanin production to fade dark spots.",
            "✔ Fades dark spots and hyperpigmentation\n✔ Evens out skin tone\n✔ Brightens complexion\n✔ Safe for all skin types\n✔ Gentle and non-irritating",
            "All Skin Types"
        ),
        "Azelaic Acid" to IngredientInfo(
            "Azelaic Acid",
            "Azelaic acid is a naturally occurring acid with anti-inflammatory and antibacterial properties.",
            "✔ Reduces acne and breakouts\n✔ Fades post-acne marks\n✔ Calms redness and rosacea\n✔ Evens skin tone\n✔ Safe during pregnancy",
            "Oily / Sensitive"
        ),
        "Hyaluronic Acid" to IngredientInfo(
            "Hyaluronic Acid",
            "Hyaluronic acid is a powerful humectant that can hold up to 1000 times its weight in water.",
            "✔ Deep and lasting hydration\n✔ Plumps and firms skin\n✔ Reduces fine lines caused by dryness\n✔ Suitable for all skin types\n✔ Lightweight and non-greasy",
            "Dry / All"
        ),
        "Vitamin C" to IngredientInfo(
            "Vitamin C",
            "Vitamin C is a powerful antioxidant that brightens skin and protects against environmental damage.",
            "✔ Brightens dull skin\n✔ Fades dark spots\n✔ Boosts collagen production\n✔ Protects against free radicals\n✔ Improves overall radiance",
            "All Skin Types"
        ),
        "Glycolic Acid" to IngredientInfo(
            "Glycolic Acid",
            "Glycolic acid is an alpha-hydroxy acid (AHA) derived from sugar cane that exfoliates the skin's surface.",
            "✔ Exfoliates dead skin cells\n✔ Improves skin texture and tone\n✔ Reduces fine lines\n✔ Fades hyperpigmentation\n✔ Boosts radiance",
            "Oily / Normal"
        ),
        "Lactic Acid" to IngredientInfo(
            "Lactic Acid",
            "Lactic acid is a gentle AHA that exfoliates while also providing hydration to the skin.",
            "✔ Gently exfoliates dead skin\n✔ Hydrates while exfoliating\n✔ Smooths rough texture\n✔ Brightens skin tone\n✔ Great for beginners",
            "Dry / Sensitive"
        ),
        "Ceramides" to IngredientInfo(
            "Ceramides",
            "Ceramides are lipids that naturally occur in the skin and are essential for maintaining the skin barrier.",
            "✔ Restores and strengthens skin barrier\n✔ Locks in moisture\n✔ Reduces dryness and flakiness\n✔ Protects against environmental damage\n✔ Soothes irritated skin",
            "Dry / Sensitive"
        ),
        "Peptides" to IngredientInfo(
            "Peptides",
            "Peptides are short chains of amino acids that act as building blocks of proteins like collagen and elastin.",
            "✔ Boosts collagen production\n✔ Firms and tightens skin\n✔ Reduces appearance of wrinkles\n✔ Supports skin repair\n✔ Improves skin elasticity",
            "All / Mature"
        ),
        "Zinc" to IngredientInfo(
            "Zinc",
            "Zinc is a mineral with powerful anti-inflammatory and oil-controlling properties.",
            "✔ Controls excess sebum\n✔ Reduces acne and breakouts\n✔ Anti-inflammatory properties\n✔ Speeds up skin healing\n✔ Protects against UV damage",
            "Oily / Acne-prone"
        ),
        "Benzoyl Peroxide" to IngredientInfo(
            "Benzoyl Peroxide",
            "Benzoyl peroxide is one of the most effective acne-fighting ingredients, killing bacteria in pores.",
            "✔ Kills acne-causing bacteria\n✔ Reduces active breakouts\n✔ Unclogs pores\n✔ Prevents new pimples\n✔ Fast-acting results",
            "Oily / Acne-prone"
        ),
        "Kojic Acid" to IngredientInfo(
            "Kojic Acid",
            "Kojic acid is a natural brightening agent derived from fungi that inhibits melanin production.",
            "✔ Fades dark spots\n✔ Brightens skin tone\n✔ Reduces hyperpigmentation\n✔ Evens complexion\n✔ Natural origin",
            "All Skin Types"
        ),
        "Ferulic Acid" to IngredientInfo(
            "Ferulic Acid",
            "Ferulic acid is a plant-based antioxidant that enhances the stability and effectiveness of other antioxidants.",
            "✔ Boosts Vitamin C effectiveness\n✔ Protects against free radicals\n✔ Anti-aging properties\n✔ Stabilizes other antioxidants\n✔ Reduces sun damage",
            "All Skin Types"
        ),
        "Squalane" to IngredientInfo(
            "Squalane",
            "Squalane is a lightweight, stable oil that mimics the skin's natural sebum for excellent hydration.",
            "✔ Lightweight deep hydration\n✔ Does not clog pores\n✔ Softens and smooths skin\n✔ Suitable for all skin types\n✔ Strengthens skin barrier",
            "Dry / All"
        ),
        "Centella Asiatica" to IngredientInfo(
            "Centella Asiatica",
            "Centella Asiatica, also known as Cica, is a plant extract widely used in Korean skincare for its healing properties.",
            "✔ Calms inflammation and redness\n✔ Supports skin healing\n✔ Strengthens skin barrier\n✔ Boosts collagen synthesis\n✔ Soothes sensitive skin",
            "Sensitive / All"
        ),
        "Green Tea Extract" to IngredientInfo(
            "Green Tea Extract",
            "Green tea extract is rich in antioxidants called catechins that protect and soothe the skin.",
            "✔ Powerful antioxidant protection\n✔ Reduces redness and irritation\n✔ Controls excess oil\n✔ Anti-aging properties\n✔ Protects against UV damage",
            "Sensitive / Oily"
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_learn, container, false)

        val tvTitle = view.findViewById<TextView>(R.id.tvIngredientTitle)
        val tvDescription = view.findViewById<TextView>(R.id.tvIngredientDesc)
        val tvSkinTypeTag = view.findViewById<TextView>(R.id.tvSkinTypeTag)

        val cardIngredientHeader = view.findViewById<CardView>(R.id.cardIngredientHeader)
        val cardBasicsHeader = view.findViewById<CardView>(R.id.cardBasicsHeader)
        val cardMorning = view.findViewById<CardView>(R.id.cardMorning)
        val cardEvening = view.findViewById<CardView>(R.id.cardEvening)
        val cardDidYouKnow = view.findViewById<CardView>(R.id.cardDidYouKnow)

        val skinType = arguments?.getString("skin_type")
        val ingredientName = arguments?.getString("ingredient_name")

        when {
            skinType == "Oily" -> {
                // Sakrivamo basics kartice
                cardBasicsHeader.visibility = View.GONE
                cardMorning.visibility = View.GONE
                cardEvening.visibility = View.GONE
                cardDidYouKnow.visibility = View.GONE
                cardIngredientHeader.visibility = View.VISIBLE

                tvTitle.text = "Best for Oily Skin"
                tvDescription.text = "✔ Niacinamide — controls sebum and minimizes pores\n\n✔ Salicylic Acid — unclogs pores and prevents breakouts\n\n✔ Azelaic Acid — reduces redness and acne\n\n✔ Alpha Arbutin — brightens and evens skin tone\n\n⚠ Avoid heavy oils and thick creams"
                tvSkinTypeTag.text = "Oily"
            }
            skinType == "Sensitive" -> {
                cardBasicsHeader.visibility = View.GONE
                cardMorning.visibility = View.GONE
                cardEvening.visibility = View.GONE
                cardDidYouKnow.visibility = View.GONE
                cardIngredientHeader.visibility = View.VISIBLE

                tvTitle.text = "Best for Sensitive Skin"
                tvDescription.text = "✔ Allantoin — soothes irritation and redness\n\n✔ Aloe Vera — calms and hydrates gently\n\n✔ Niacinamide — strengthens skin barrier (low %)\n\n⚠ Avoid Retinol, high % acids and fragrances\n\n⚠ Always patch test new products"
                tvSkinTypeTag.text = "Sensitive"
            }
            skinType == "Dry" -> {
                cardBasicsHeader.visibility = View.GONE
                cardMorning.visibility = View.GONE
                cardEvening.visibility = View.GONE
                cardDidYouKnow.visibility = View.GONE
                cardIngredientHeader.visibility = View.VISIBLE

                tvTitle.text = "Best for Dry Skin"
                tvDescription.text = "✔ Hyaluronic Acid — deep hydration\n\n✔ Aloe Vera — soothes and moisturizes\n\n✔ Allantoin — softens and repairs skin\n\n✔ Retinol — anti-aging (start with low %)\n\n⚠ Avoid alcohol-based products and harsh cleansers"
                tvSkinTypeTag.text = "Dry"
            }
            ingredientName != null -> {
                // Dosli smo sa Search — sakrivamo basics kartice
                cardBasicsHeader.visibility = View.GONE
                cardMorning.visibility = View.GONE
                cardEvening.visibility = View.GONE
                cardDidYouKnow.visibility = View.GONE
                cardIngredientHeader.visibility = View.GONE

                val info = ingredientDatabase[ingredientName]
                if (info != null) {
                    tvTitle.text = info.title
                    tvDescription.text = "${info.description}\n\n📋 Benefits:\n${info.benefits}"
                    tvSkinTypeTag.text = info.skinType
                } else {
                    tvTitle.text = ingredientName
                    tvDescription.text = "Details about $ingredientName coming soon."
                    tvSkinTypeTag.text = "All Skin Types"
                }
            }
            else -> {
                // Default — prikazujemo sve kartice
                cardIngredientHeader.visibility = View.VISIBLE
                cardBasicsHeader.visibility = View.VISIBLE
                cardMorning.visibility = View.VISIBLE
                cardEvening.visibility = View.VISIBLE
                cardDidYouKnow.visibility = View.VISIBLE

                tvTitle.text = "Niacinamide"
                tvDescription.text = "Niacinamide, also known as Vitamin B3, is a multi-purpose skincare ingredient that helps strengthen the skin barrier, improve texture, and reduce inflammation."
                tvSkinTypeTag.text = "Oily / Combination"
            }
        }

        return view
    }
}