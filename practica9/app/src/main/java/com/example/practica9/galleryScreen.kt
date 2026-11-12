package com.example.practica9

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

// funcion que contiene la pantalla de la galeria
@Composable
fun GaleriaScreen() {

    // variable para almacenar la imagen seleccionada
    var selectedImageUrl by remember { mutableStateOf<String?>(null) }

    // lista de imagenes
    val imagenes = listOf(
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQZpxmlcSdSZpLws7x00xWNN6wyKW9O0uJriuytdYs0uA&s=10",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTDd370s1mGQzEiNVJWOYTIeHUTwOCIPwLYPFPYAGg5kw&s=10",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJhXf8POyFChRr32TxaRwBszKT2EL5Vhhd2hgoVh2q3g&s=10",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSHVWlXSMuudYBHNpcpVP1TQIBiJPlQcnolYSS7fQyA_w&s=10",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSQY-zt3bP7zG-23tUAeQ1fLKVVPtb-KGZKtc1JZV5i4g&s=10",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTLQOa8HHKyxqMBbZnC8uV_-7HRtYHis9chVSm9P2SuOA&s=10",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQBfsihCjIV5Ok98Nq7kuvkzQdj0amGaw4zHjtUfkyKgg&s=10",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTbrnnkLAqJfB1P2Rzo_OY2IfcJXtcDPJXXLXABSO31pA&s=10"
    )

    // pantalla de la galeria
    Column(
        modifier = Modifier.fillMaxSize().padding(8.dp).padding(top = 32.dp)
    ) {
        // titulo de la galeria
        Text(text = "Galeria", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(8.dp))
        // lista de imagenes
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(imagenes) { url ->
                // imagen de la galeria
                ImageItem(url = url, onClick = { selectedImageUrl = url })
            }
        }
    }

    // imagen seleccionada
    selectedImageUrl?.let { url ->
        Dialog(onDismissRequest = { selectedImageUrl = null }) {
            Card(modifier = Modifier.fillMaxWidth().padding(16.dp), shape = RoundedCornerShape(16.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
                AsyncImage(model = url, contentDescription = "Imagen ampliada", contentScale = ContentScale.Fit, modifier = Modifier.fillMaxWidth().wrapContentHeight())
            }
        }
    }
}

// funcion para mostrar una imagen en la galeria
@Composable
fun ImageItem(url: String, onClick: () -> Unit) {
    // card para mostrar la imagen
    Card(modifier = Modifier.fillMaxWidth().aspectRatio(1f).clickable { onClick() }, elevation = CardDefaults.cardElevation(defaultElevation = 4.dp), shape = RoundedCornerShape(8.dp)) {
        // imagen de la galeria
        AsyncImage(model = url, contentDescription = "Imagen de la galeria", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize())
    }
}