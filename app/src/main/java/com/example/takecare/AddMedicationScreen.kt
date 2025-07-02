package com.example.takecare

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import java.io.File

@Composable
fun AddMedicationScreen(onSave: (Medication) -> Unit) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var reason by remember { mutableStateOf("") }
    var dailyDose by remember { mutableStateOf("1") }
    var startQty by remember { mutableStateOf("0") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success -> }

    Column(Modifier.padding(16.dp)) {
        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Name") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Description") })
        OutlinedTextField(value = reason, onValueChange = { reason = it }, label = { Text("Reason") })
        OutlinedTextField(value = dailyDose, onValueChange = { dailyDose = it }, label = { Text("Daily dose") })
        OutlinedTextField(value = startQty, onValueChange = { startQty = it }, label = { Text("Start quantity") })
        Spacer(Modifier.height(8.dp))
        imageUri?.let { uri ->
            Image(painter = rememberAsyncImagePainter(uri), contentDescription = null, modifier = Modifier.height(120.dp).fillMaxWidth(), contentScale = ContentScale.Crop)
        }
        Button(onClick = {
            val file = File.createTempFile("med", ".jpg", context.cacheDir)
            val uri = FileProvider.getUriForFile(context, context.packageName + ".provider", file)
            imageUri = uri
            cameraLauncher.launch(uri)
        }) { Text("Take Picture") }
        Spacer(Modifier.height(8.dp))
        Button(onClick = {
            val med = Medication(
                name = name,
                description = description,
                reason = reason,
                dailyDose = dailyDose.toIntOrNull() ?: 1,
                startQuantity = startQty.toIntOrNull() ?: 0,
                imageUri = imageUri?.toString()
            )
            onSave(med)
        }) { Text("Save") }
    }
}
