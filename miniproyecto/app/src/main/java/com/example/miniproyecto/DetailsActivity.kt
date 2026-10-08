package com.example.miniproyecto

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class DetailsActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val nombre = intent.getStringExtra("EXTRA_NOMBRE_COMPLETO") ?: "Sin nombre"
        val matricula = intent.getStringExtra("EXTRA_MATRICULA") ?: "Sin matricula"
        val carrera = intent.getStringExtra("EXTRA_CARRERA") ?: "Sin carrera"
        val turno = intent.getStringExtra("EXTRA_TURNO") ?: "Sin turno"
        val activo = intent.getBooleanExtra("EXTRA_ACTIVO", true)

        setContent {
            DetailsScreen(
                nombre = nombre,
                matricula = matricula,
                carrera = carrera,
                turno = turno,
                activo = activo,
                onBackClick = {finish()}
            )
        }
    }
}