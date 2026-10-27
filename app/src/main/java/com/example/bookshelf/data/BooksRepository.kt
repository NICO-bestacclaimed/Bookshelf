package com.example.bookshelf.data

import com.example.bookshelf.model.Book
import com.example.bookshelf.network.BooksApi

interface BooksRepository {
    suspend fun getBooks(query: String): List<Book>
}

class NetworkBooksRepository : BooksRepository {
    override suspend fun getBooks(query: String): List<Book> {
        val response = BooksApi.retrofitService.getBooks(query)
        val items = response.items.orEmpty()
        return items.mapNotNull { item ->
            val volumeInfo = item.volumeInfo
            val title = volumeInfo?.title ?: "No Title"
            val rawThumbnail = volumeInfo?.imageLinks?.thumbnail
            // Google Books API returns http thumbnails; convert to https to avoid cleartext HTTP errors
            val thumbnailUrl = rawThumbnail?.replace("http://", "https://") ?: ""
            if (thumbnailUrl.isNotBlank()) {
                Book(
                    id = item.id,
                    title = title,
                    thumbnailUrl = thumbnailUrl
                )
            } else {
                null
            }
        }
    }
}
