package uz.gita.dictionaryuzen.data.model

import java.io.Serializable

data class ItemData(
    val id: Int,
    val english : String,
    val type: String,
    val translate: String,
    val uzbek : String,
    var isFavourite: Int
) : Serializable