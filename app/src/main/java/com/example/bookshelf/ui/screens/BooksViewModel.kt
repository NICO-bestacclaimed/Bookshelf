package com.example.bookshelf.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.data.NetworkBooksRepository
import com.example.bookshelf.model.Book
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface BooksUiState {
    data class Success(val books: List<Book>) : BooksUiState
    object Error : BooksUiState
    object Loading : BooksUiState
}

class BooksViewModel(
    private val booksRepository: BooksRepository = NetworkBooksRepository()
) : ViewModel() {

    var uiState: BooksUiState by mutableStateOf(BooksUiState.Loading)
        private set

    var searchQuery: String by mutableStateOf("Jetpack Compose")
        private set

    init {
        getBooks(searchQuery)
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun getBooks(query: String) {
        viewModelScope.launch {
            uiState = BooksUiState.Loading
            uiState = try {
                val list = booksRepository.getBooks(query)
                BooksUiState.Success(list)
            } catch (e: IOException) {
                BooksUiState.Error
            } catch (e: HttpException) {
                BooksUiState.Error
            } catch (e: Exception) {
                BooksUiState.Error
            }
        }
    }
}
