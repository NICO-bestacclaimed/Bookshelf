package com.example.bookshelf.model

import kotlinx.serialization.Serializable

@Serializable
data class BookResponse(
    val items: List<BookItem>? = null
)

@Serializable
data class BookItem(
    val id: String,
    val volumeInfo: VolumeInfo? = null
)

@Serializable
data class VolumeInfo(
    val title: String? = null,
    val authors: List<String>? = null,
    val description: String? = null,
    val imageLinks: ImageLinks? = null
)

@Serializable
data class ImageLinks(
    val thumbnail: String? = null
)

data class Book(
    val id: String,
    val title: String,
    val thumbnailUrl: String
)
