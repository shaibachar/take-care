package com.example.takecare

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MedicationViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MedicationRepository(
        AppDatabase.getDatabase(application).medicationDao()
    )
    private val context = application.applicationContext

    val medications = repository.getAll()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun addMedication(medication: Medication) {
        viewModelScope.launch {
            repository.add(medication)
            ReminderScheduler.schedule(context, medication)
        }
    }

    fun updateMedication(medication: Medication) {
        viewModelScope.launch {
            repository.update(medication)
            ReminderScheduler.schedule(context, medication)
        }
    }

    fun deleteMedication(medication: Medication) {
        viewModelScope.launch {
            repository.delete(medication)
            ReminderScheduler.cancel(context, medication)
        }
    }

    fun disableReminder(medication: Medication) {
        ReminderScheduler.cancel(context, medication)
    }

    fun enableReminder(medication: Medication) {
        ReminderScheduler.schedule(context, medication)
    }
}
