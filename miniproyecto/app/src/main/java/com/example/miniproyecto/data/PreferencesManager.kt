package com.example.miniproyecto.data

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager (context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("UserPreferences", Context.MODE_PRIVATE)

    companion object{
        const val KEY_NOMBRE_COMPLETO = "key_nombre_completo"
        const val KEY_MATRICULA = "key_matricula"
        const val KEY_CARRERA = "key_carrera"
        const val KEY_TURNO = "key_turno"
        const val KEY_ACTIVO = "key_activo"
    }

    fun saveSettings(nombreCompleto: String, matricula: String, carrera: String, turno: String, activo: Boolean){
        val editor = sharedPreferences.edit()
        editor.putString(KEY_NOMBRE_COMPLETO, nombreCompleto)
        editor.putString(KEY_MATRICULA, matricula)
        editor.putString(KEY_CARRERA, carrera)
        editor.putString(KEY_TURNO, turno)
        editor.putBoolean(KEY_ACTIVO, activo)
        editor.apply()
    }

    fun getNombreCompleto(): String{
        return sharedPreferences.getString(KEY_NOMBRE_COMPLETO, "") ?: ""
    }

    fun getMatricula(): String{
        return sharedPreferences.getString(KEY_MATRICULA, "") ?: ""
    }

    fun getCarrera(): String{
        return sharedPreferences.getString(KEY_CARRERA, "") ?: ""
    }

    fun getTurno(): String{
        return sharedPreferences.getString(KEY_TURNO, "") ?: ""
    }

    fun getActivo(): Boolean{
        return sharedPreferences.getBoolean(KEY_ACTIVO, false)
    }

    fun clearPreferences() {
        sharedPreferences.edit().clear().apply()
    }
}