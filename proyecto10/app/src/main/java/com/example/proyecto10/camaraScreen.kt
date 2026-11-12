package com.example.proyecto10

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

// funcion para la pantalla de la camara
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CamaraScreen(){

    // variables para la camara
    val context = LocalContext.current
    var listaImagenes by remember { mutableStateOf(listOf<Bitmap>()) }

    val camaraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) { bitmap: Bitmap? ->
        if (bitmap != null){
            listaImagenes = listaImagenes + bitmap
        }
    }

    // variable para saber si tiene permiso para la camara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            camaraLauncher.launch(null)
        }
    }

    // menu de la pantalla
    Scaffold(
        // titulo de la pantalla
        topBar = { TopAppBar(title = { Text("Camara y galeria") }, colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) },
        // boton para tomar foto
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val hasPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

                if (hasPermission) {
                    camaraLauncher.launch(null)
                } else {
                    permissionLauncher.launch(Manifest.permission.CAMERA)
                }
            }) {
                Icon(Icons.Filled.Add, contentDescription = "Camara")
            }
        }
    ) { paddingValues ->
        // columna para mostrar las imagenes
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(8.dp)) {
            if (listaImagenes.isEmpty()){
                Text("No hay imagenes")
            } else {
                LazyVerticalGrid(columns = GridCells.Fixed(2), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxSize()) {
                    items(listaImagenes) { bitmap ->
                        ImagenCapturadaItem(bitmap = bitmap)
                    }
                }
            }
        }
    }
}

// funcion para mostrar las imagenes
@Composable
fun ImagenCapturadaItem(bitmap: Bitmap){
    // card para mostrar la imagen
    Card(modifier = Modifier.fillMaxWidth().aspectRatio(1f), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), shape = RoundedCornerShape(8.dp)) {
        // imagen para mostrar la imagen
        Image(bitmap = bitmap.asImageBitmap(), contentDescription = "Foto tomada", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
    }
}