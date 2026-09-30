package com.example.proyecto5

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.example.proyecto5.data.PreferencesManager
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormScreen() {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }

    var username by remember { mutableStateOf("") }
    var notificationsEnabled by remember {mutableStateOf(false)}
    var datkThemeEnabled by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        username = preferencesManager.getUsername()
        notificationsEnabled = preferencesManager.getNotifications()
        datkThemeEnabled = preferencesManager.getDarkTheme()
    }

    val colorScheme = if (datkThemeEnabled) {
        darkColorScheme()
    } else {
        lightColorScheme()
    }

    MaterialTheme(colorScheme = colorScheme) {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.spacedBy(20.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Practica 5: Configuracion", fontSize = 24.sp, style = MaterialTheme.typography.headlineMedium)
                HorizontalDivider()
                OutlinedTextField(value = username, onValueChange = {username=it}, label = {Text("Nombre del usuario")}, singleLine = true, modifier = Modifier.fillMaxWidth())
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Recibir notificaciones:", fontSize = 16.sp)
                    Switch(checked = notificationsEnabled, onCheckedChange = {notificationsEnabled=it})
                }
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Activar tema oscuro:", fontSize = 16.sp)
                    Switch(checked = datkThemeEnabled, onCheckedChange = {datkThemeEnabled=it})
                }
                HorizontalDivider()
                Button(onClick = {
                    preferencesManager.saveSettings(
                        username = username,
                        notifications = notificationsEnabled,
                        darkTheme = datkThemeEnabled
                    )
                    Toast.makeText(context, "Configuracion guardada", Toast.LENGTH_SHORT).show()
                },
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Guardar preferencias")
                }
                OutlinedButton(onClick = {
                    username = preferencesManager.getUsername()
                    notificationsEnabled = preferencesManager.getNotifications()
                    datkThemeEnabled = preferencesManager.getDarkTheme()
                    Toast.makeText(context, "Preferencias guardadas", Toast.LENGTH_SHORT).show()
                },
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Recargar Datos Guardados")
                }
                TextButton(onClick = {
                    preferencesManager.clearPreferences()
                    username = ""
                    notificationsEnabled = false
                    datkThemeEnabled = false
                    Toast.makeText(context, "Preferencias eliminadas", Toast.LENGTH_SHORT).show()
                },
                    modifier = Modifier.fillMaxWidth()) {
                    Text("Restablecer Configuracion", color = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}