package com.example.betareadingapp.presentation.mytexts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.betareadingapp.feature_text.data.repository.AuthRepository
import com.example.betareadingapp.domain.model.Text
import com.example.betareadingapp.domain.util.Resource
import com.example.betareadingapp.domain.util.networkState.AuthState
import com.example.betareadingapp.domain.util.networkState.MyTextsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MyTextsViewModel
@Inject
constructor(
    private var authRepository: AuthRepository
) : ViewModel() {
    val lista: List<Text> = listOf(
        Text("1", "Janusz Kowalski", "Tutyl1", "Tekst1"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text("2", "Janusz Kowalski", "Tutyl2", "Tekasdsadsadsadsadadasdadasdst2"),
        Text(
            "3",
            "Janusz Kowalski",
            "Tutyl3",
            "Tekrawrwararwararskrawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra krawrwararwararsdadt3 rwarwarawra dadt3 rwarwarawra rawrwarawrawrawrwaw"
        )
    )
    val state = mutableStateOf(lista)

    private val _myTexts = MutableStateFlow(MyTextsState())

    val myTexts: StateFlow<MyTextsState> = _myTexts

    val _search = mutableStateOf("")
    val search: State<String> = _search

    init {
        getUserTexts()
    }

    fun setSearch(setString: String) {
        _search.value = setString
    }

    fun getUserTexts() {
        authRepository.getTexts().onEach {
            _myTexts.value = when (it) {
                is Resource.Loading -> MyTextsState(isLoading = true)
                is Resource.Error -> MyTextsState(error = it.message ?: "")
                is Resource.Success -> MyTextsState(data = it.data)
            }
        }.launchIn(viewModelScope)
    }
}
