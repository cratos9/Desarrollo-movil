package com.example.practica11

import android.media.MediaPlayer
import android.net.Uri
import androidx. activity. compose.rememberLauncherForActivityResult
import androidx.activity. result.contract.ActivityResultContracts
import androidx. compose. foundation. layout .*
import androidx.compose.foundation.layout.Arrangement
import androidx. compose. material. icons. Icons
import androidx.compose.material. icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime .*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx. compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

// Funcion para la pantalla del reproductor
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReproductorScreen(){

    // Variables para el reproductor
    val context = LocalContext.current

    var audioUri by remember { mutableStateOf<Uri?>(null) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var isPlaying by remember { mutableStateOf(false) }

    // Variables para el selector de audio
    val selectorAudio = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
        uri: Uri? ->
        if (uri != null) {
            audioUri = uri
            mediaPlayer?.release()
            mediaPlayer = MediaPlayer.create(context, uri)
            mediaPlayer?.start()
            isPlaying = true
            mediaPlayer?.setOnCompletionListener { isPlaying = false }
        }
    }

    // Efecto para liberar los recursos del reproductor cuando se desmonta la pantalla
    DisposableEffect(Unit) {
        onDispose { mediaPlayer?.release() }
    }

    // Contenido de la pantalla
    Scaffold(topBar = {TopAppBar(title = {Text("Mini reproductor MP#")}, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer))}) {
        paddingValue ->
        // Contenido del reproductor
        Column(modifier = Modifier.fillMaxSize().padding(paddingValue).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            // Texto para mostrar el estado del reproductor
            Text( text = if (audioUri == null) "No hay audio seleccionado" else "Audio cargado correctamente", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(24.dp))
            Button(onClick = { selectorAudio.launch("audio/*") }) {
                Text(text = "Seleccionar audio")
            }
            Spacer(modifier = Modifier.height(32.dp))
            // Botones para controlar el reproductor
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                // Boton para reproducir el audio
                FloatingActionButton(onClick = {
                    if (mediaPlayer != null && !isPlaying) {
                        mediaPlayer?.start()
                        isPlaying = true
                    }
                }){ Icon(Icons.Default.PlayArrow, contentDescription = "Play") }
                // Botones para pausar el audio
                FloatingActionButton(onClick = {
                    if (mediaPlayer != null && isPlaying) {
                        mediaPlayer?.pause()
                        isPlaying = false
                    }
                }){ Text(" || ", style = MaterialTheme.typography.titleLarge) }
                // Boton para detener el audio y reiniciar a 0
                FloatingActionButton(onClick = {
                    if (mediaPlayer != null) {
                        mediaPlayer?.pause()
                        mediaPlayer?.seekTo(0)
                        isPlaying = false
                    }
                }){ Text(" o ", style = MaterialTheme.typography.titleLarge) }
            }
        }
    }
}