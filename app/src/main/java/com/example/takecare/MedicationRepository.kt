package com.example.takecare

class MedicationRepository(private val dao: MedicationDao) {
    fun getAll() = dao.getAll()

    suspend fun add(medication: Medication) = dao.insert(medication)

    suspend fun update(medication: Medication) = dao.update(medication)

    suspend fun delete(medication: Medication) = dao.delete(medication)
}
