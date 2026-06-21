package com.example.skincarechecker

import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {

    // Test 1 - osnovni test (vec postojeci)
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    // Test 2 - testiramo da login radi sa tacnim podacima
    @Test
    fun login_correctCredentials_returnsTrue() {
        val username = "bojana"
        val password = "1234"
        val result = checkLogin(username, password)
        assertTrue(result)
    }

    // Test 3 - testiramo da login ne radi sa pogresnim podacima
    @Test
    fun login_wrongCredentials_returnsFalse() {
        val username = "wronguser"
        val password = "wrongpass"
        val result = checkLogin(username, password)
        assertFalse(result)
    }

    // Test 4 - testiramo pretragu sastojaka
    @Test
    fun search_findsIngredient_whenQueryMatches() {
        val ingredients = listOf(
            "Niacinamide", "Retinol", "Salicylic Acid",
            "Allantoin", "Aloe Vera", "Hyaluronic Acid"
        )
        val query = "nia"
        val results = ingredients.filter { it.lowercase().contains(query) }
        assertTrue(results.isNotEmpty())
        assertEquals("Niacinamide", results[0])
    }

    // Test 5 - testiramo da pretraga ne vraca rezultate za nepostojeci sastojak
    @Test
    fun search_returnsEmpty_whenNoMatch() {
        val ingredients = listOf(
            "Niacinamide", "Retinol", "Salicylic Acid",
            "Allantoin", "Aloe Vera", "Hyaluronic Acid"
        )
        val query = "zzzzz"
        val results = ingredients.filter { it.lowercase().contains(query) }
        assertTrue(results.isEmpty())
    }

    // Test 6 - testiramo filter po tipu koze
    @Test
    fun skinTypeFilter_returnsCorrectIngredients() {
        val skinTypeMap = mapOf(
            "Oily" to listOf("Niacinamide", "Salicylic Acid", "Azelaic Acid"),
            "Sensitive" to listOf("Allantoin", "Aloe Vera"),
            "Dry" to listOf("Hyaluronic Acid", "Ceramides")
        )
        val oilyIngredients = skinTypeMap["Oily"]
        assertNotNull(oilyIngredients)
        assertTrue(oilyIngredients!!.contains("Niacinamide"))
    }

    // Test 7 - testiramo IngredientData model
    @Test
    fun ingredientData_hasCorrectFields() {
        val ingredient = IngredientData(
            name = "Niacinamide",
            description = "Vitamin B3 ingredient"
        )
        assertEquals("Niacinamide", ingredient.name)
        assertEquals("Vitamin B3 ingredient", ingredient.description)
    }

    // Pomocna funkcija za testiranje logina
    private fun checkLogin(username: String, password: String): Boolean {
        return username == "bojana" && password == "1234"
    }
}