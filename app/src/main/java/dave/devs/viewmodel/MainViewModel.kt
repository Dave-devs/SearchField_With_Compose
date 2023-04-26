package dave.devs.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dave.devs.model.countriesList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(FlowPreview::class)
class MainViewModel: ViewModel() {

    private val _searchText = MutableStateFlow(" ")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _countries = MutableStateFlow(countriesList)
    val countries = _searchText
            //To give enough time for search text change or not
        .debounce(1000L)
            //To simulate network call
        .onEach { _isSearching.update { true } }
        .combine(_countries) { text, countries ->
        if(text.isBlank()) {
            countries
        } else {
            delay(2000L)
            countries.filter {
                it.matchSearchQuery(text)
            }
        }
    }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _countries.value
        )

    fun searchCountries(text: String) {
        _searchText.value = text
    }
}