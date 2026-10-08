package com.example.miniproyecto.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun CarreraSpinner(carrera: String,  onCarreraChange: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(value = carrera, onValueChange = {}, readOnly = true, label = { Text("Carrera") }, modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Seleccionar carrera")
                }
            }
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text("Ingenieria de Software") },
                onClick = { onCarreraChange("Ingenieria de Software")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("Ingenieria civil") },
                onClick = { onCarreraChange("Ingenieria civil")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("Ingenieria de Procesos Administrativos") },
                onClick = { onCarreraChange("Ingenieria de Procesos Administrativos")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("Ingenieria de Nanotecnologia") },
                onClick = { onCarreraChange("Ingenieria de Nanotecnologia")
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text("Ingenieria de Sistemas") },
                onClick = { onCarreraChange("Ingenieria de Sistemas")
                    expanded = false
                }
            )
        }
    }
}