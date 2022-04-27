package fr.insideapp.turnipoffkmm.android.ui.screens.credit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import fr.insideapp.turnipoffkmm.android.logic.Service
import fr.insideapp.turnipoffkmm.model.movie.MovieCredits
import fr.insideapp.turnipoffkmm.model.person.Person
import kotlinx.coroutines.launch

class PersonScreenViewModel(val personId: Long): ViewModel() {
    var personDetails: Person? by mutableStateOf(null)
    var personCredits: MovieCredits? by mutableStateOf(null)

    init {
        refreshPerson()
        refreshPersonCredits()
    }

    fun refreshPerson() {
        viewModelScope.launch {
            val personResult = Service.getInstance().client.getPerson(
                personId = personId
            )

            personDetails = personResult
        }
    }

    fun refreshPersonCredits() {
        viewModelScope.launch {
            val personCreditResult = Service.getInstance().client.getPersonCredits(
                personId = personId
            )

            personCredits = personCreditResult
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