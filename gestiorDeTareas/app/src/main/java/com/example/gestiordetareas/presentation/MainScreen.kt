package com.example.gestiordetareas.presentation

import android.widget.Toast
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.gestiordetareas.data.Tarea
import com.example.gestiordetareas.domain.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

enum class FiltroEstado {
    TODAS, PENDIENTES, COMPLETADAS
}

enum class TipoOrden {
    DEFAULT, INVERSO, A_Z, Z_A
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    var menuExpandido by remember { mutableStateOf(false) }
    var filtroEstadoActual by remember { mutableStateOf(FiltroEstado.TODAS) }
    var ordenActual by remember { mutableStateOf(TipoOrden.DEFAULT) }
    val db = remember { AppDatabase.getDatabase(context) }
    val dao = db.tareaDao()
    val coroutineScope = rememberCoroutineScope()
    val tareasBase by dao.obtenerTareas().collectAsState(initial = emptyList())
    val tareas = remember(tareasBase, filtroEstadoActual, ordenActual) {
        val tareasFiltradas = when (filtroEstadoActual) {
            FiltroEstado.TODAS -> tareasBase
            FiltroEstado.PENDIENTES -> tareasBase.filter { !it.completada }
            FiltroEstado.COMPLETADAS -> tareasBase.filter { it.completada }
        }
        when (ordenActual) {
            TipoOrden.DEFAULT -> tareasFiltradas
            TipoOrden.INVERSO -> tareasFiltradas.reversed()
            TipoOrden.A_Z -> tareasFiltradas.sortedBy { it.titulo.lowercase() }
            TipoOrden.Z_A -> tareasFiltradas.sortedByDescending { it.titulo.lowercase() }
        }
    }
    var nuevaTarea by remember { mutableStateOf("") }
    var nuevaDescripcion by remember { mutableStateOf("") }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Gestion de Tareas") },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            actions = {
                IconButton(onClick = { menuExpandido = true }) { Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Mas opciones") }
                DropdownMenu(expanded = menuExpandido, onDismissRequest = { menuExpandido = false }) {
                    DropdownMenuItem(text = { Text(" Todas") }, onClick = {
                            filtroEstadoActual = FiltroEstado.TODAS
                            menuExpandido = false
                            Toast.makeText(context, "Todas las tareas", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(text = { Text(" Pendientes") }, onClick = {
                            filtroEstadoActual = FiltroEstado.PENDIENTES
                            menuExpandido = false
                            Toast.makeText(context, "Tareas pedientes", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(text = { Text(" Completadas") }, onClick = {
                            filtroEstadoActual = FiltroEstado.COMPLETADAS
                            menuExpandido = false
                            Toast.makeText(context, "Tareas completadas", Toast.LENGTH_SHORT).show()
                        }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(text = { Text(" Orden normal") }, onClick = {
                            ordenActual = TipoOrden.DEFAULT
                            menuExpandido = false
                            Toast.makeText(context, "Orden normal", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(text = { Text(" Orden inverso ") }, onClick = {
                            ordenActual = TipoOrden.INVERSO
                            menuExpandido = false
                            Toast.makeText(context, "Orden inverso", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(text = { Text(" Orden A-Z") }, onClick = {
                            ordenActual = TipoOrden.A_Z
                            menuExpandido = false
                            Toast.makeText(context, "Orden A-Z", Toast.LENGTH_SHORT).show()
                        }
                    )
                    DropdownMenuItem(text = { Text(" Orden Z-A") }, onClick = {
                        ordenActual = TipoOrden.Z_A
                        menuExpandido = false
                        Toast.makeText(context, "Orden Z-A", Toast.LENGTH_SHORT).show()
                    }
                    )
                    HorizontalDivider()
                    DropdownMenuItem(text = { Text(" Borrar completadas", color = MaterialTheme.colorScheme.error) }, onClick = {
                            menuExpandido = false
                            coroutineScope.launch { withContext(Dispatchers.IO) { dao.eliminarCompletadas() }
                            Toast.makeText(context, "Tareas completadas eliminadas", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            }
        )
    }) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            OutlinedTextField(value = nuevaTarea, onValueChange = { nuevaTarea = it }, label = { Text("Titulo de la tarea") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = nuevaDescripcion, onValueChange = { nuevaDescripcion = it }, label = { Text("Descripcion de la tarea") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val titulo = nuevaTarea.trim()
                    val descripcion = nuevaDescripcion.trim()
                    if (titulo.isNotBlank() && descripcion.isNotBlank()) {
                        coroutineScope.launch {
                            withContext(Dispatchers.IO) { dao.insertar(Tarea(titulo = titulo, descripcion = descripcion)) }
                            nuevaTarea = ""
                            nuevaDescripcion = ""
                        }
                    } else {
                        Toast.makeText(context, "Llena ambos campos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Guardar Tarea") }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Lista de tareas (${filtroEstadoActual.name.lowercase().capitalize()})", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = tareas, key = { tarea -> tarea.id }) { tarea ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = tarea.completada, onCheckedChange = { checked -> coroutineScope.launch { withContext(Dispatchers.IO) { dao.actualizar(tarea.copy(completada = checked)) } } })
                            Column(modifier = Modifier.weight(1f).padding(horizontal = 8.dp)) {
                                Text(text = tarea.titulo, style = MaterialTheme.typography.bodyLarge.copy(textDecoration = if (tarea.completada) TextDecoration.LineThrough else TextDecoration.None))
                                Text(text = tarea.descripcion, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                            IconButton(onClick = { coroutineScope.launch {
                                    val nuevoEstado = !tarea.completada
                                    withContext(Dispatchers.IO) { dao.actualizar(tarea.copy(completada = nuevoEstado)) }
                                    val msj = if (nuevoEstado) "Tarea completada" else "Tarea marcada incompleta"
                                    Toast.makeText(context, msj, Toast.LENGTH_SHORT).show()
                                }
                            }) {
                                Icon(imageVector = if (tarea.completada) Icons.Filled.CheckCircle else Icons.Outlined.CheckCircle, contentDescription = "Cambiar estado", tint = if (tarea.completada) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline)
                            }
                            IconButton(onClick = {
                                coroutineScope.launch { withContext(Dispatchers.IO) { dao.eliminar(tarea) } }
                                Toast.makeText(context, "Tarea Eliminada", Toast.LENGTH_SHORT).show()
                            }) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Eliminar tarea", tint = MaterialTheme.colorScheme.error)
                            }
                        }
                    }
                }
            }
        }
    }
}