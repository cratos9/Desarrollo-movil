package com.example.miniproyecto

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miniproyecto.components.CarreraSpinner
import com.example.miniproyecto.components.TurnoRarioButton
import com.example.miniproyecto.components.ActivoSwitch
import com.example.miniproyecto.data.PreferencesManager

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }
    var nombre by remember { mutableStateOf(preferencesManager.getNombreCompleto()) }
    var matricula by remember { mutableStateOf(preferencesManager.getMatricula()) }
    var carrera by remember { mutableStateOf(preferencesManager.getCarrera().ifEmpty { "Seleccionar Opcion" }) }
    var turno by remember { mutableStateOf(preferencesManager.getTurno().ifEmpty { "Matutino" }) }
    var activo by remember { mutableStateOf(preferencesManager.getActivo()) }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Text(text = "Datos del alumno", fontSize = 22.sp, style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre Completo") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(value = matricula, onValueChange = { matricula = it }, label = { Text("Matricula") }, singleLine = true, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(12.dp))

            CarreraSpinner(carrera = carrera, onCarreraChange = { carrera = it })
            Spacer(modifier = Modifier.height(12.dp))

            TurnoRarioButton(selectedOption = turno, onOptionSelected = { turno = it })
            Spacer(modifier = Modifier.height(12.dp))

            ActivoSwitch(isChecked = activo, onCheckedChange = { activo = it })
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    preferencesManager.saveSettings(
                        nombreCompleto = nombre,
                        matricula = matricula,
                        carrera = carrera,
                        turno = turno,
                        activo = activo
                    )

                    val intent = Intent(context, DetailsActivity::class.java).apply {
                        putExtra("EXTRA_NOMBRE_COMPLETO", nombre)
                        putExtra("EXTRA_MATRICULA", matricula)
                        putExtra("EXTRA_CARRERA", carrera)
                        putExtra("EXTRA_TURNO", turno)
                        putExtra("EXTRA_ACTIVO", activo)
                    }
                    context.startActivity(intent)
                }
            ) { Text("Mostrar datos") }
        }
    }
}