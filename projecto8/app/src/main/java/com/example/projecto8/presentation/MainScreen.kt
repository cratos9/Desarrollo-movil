
package com.example.projecto8.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.projecto8.data.Usuario
import com.example.projecto8.domain.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun MainScreen() {
    val context = LocalContext.current
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.usuarioDao()
    val coroutineScope = rememberCoroutineScope()
    val usuarios by dao.obtenerTodos().collectAsState(initial = emptyList())
    var nuevoNombre by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp).padding(top = 32.dp)) {
        OutlinedTextField(value = nuevoNombre, onValueChange = { nuevoNombre = it }, label = { Text("Nombre del usuario") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val nombre = nuevoNombre.trim()
                if (nombre.isNotBlank()) {
                    coroutineScope.launch {
                        withContext(Dispatchers.IO) { dao.insertar(Usuario(nombre = nombre)) }
                        nuevoNombre = ""
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) { Text("Guardar Usuario") }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Lista de usuarios registrados", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(items = usuarios, key = { usuario -> usuario.id }) { usuario ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "ID: ${usuario.id} | Nombre: ${usuario.nombre}", modifier = Modifier.weight(1f).padding(vertical = 16.dp))
                        IconButton(onClick = { coroutineScope.launch { withContext(Dispatchers.IO) { dao.eliminar(usuario) } } }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar usuario", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}
