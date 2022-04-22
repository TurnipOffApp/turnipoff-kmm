package fr.insideapp.turnipoffkmm.android.ui.screens.credit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.insideapp.turnipoffkmm.model.person.Person
import fr.insideapp.turnipoffkmm.network.TheMovieDBClient
import kotlinx.coroutines.launch

class PersonScreenViewModel(val personId: Long): ViewModel() {
    var personDetails: Person? by mutableStateOf(null)

    init {
        refreshPerson()
    }

    fun refreshPerson() {
        viewModelScope.launch {
            val personResult = TheMovieDBClient.getPerson(
                personId = personId
            )

            personDetails = personResult
        }
    }
}

class PersonScreenViewModelFactory constructor(private val personId: Long): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(PersonScreenViewModel::class.java)) {
            PersonScreenViewModel(personId) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}