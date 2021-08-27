package com.example.moviecity.models


data class SearchResultList(
    val results: List<SearchMovie>
)

data class SearchMovie(
    val id: String,
    val title: String,
    val poster_path: String,
)