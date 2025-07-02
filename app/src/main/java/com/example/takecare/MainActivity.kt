package com.example.takecare

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.rememberAsyncImagePainter
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    private val viewModel: MedicationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TakeCareApp(viewModel)
        }
    }
}

@Composable
fun TakeCareApp(viewModel: MedicationViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            val medications by viewModel.medications.collectAsState()
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = { navController.navigate("add") }) { Text("+") }
                }
            ) { padding ->
                MedicationList(
                    medications = medications,
                    modifier = Modifier.padding(padding)
                )
            }
        }
        composable("add") {
            AddMedicationScreen { med ->
                viewModel.addMedication(med)
                navController.popBackStack()
            }
        }
    }
}

@Composable
fun MedicationList(medications: List<Medication>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(medications) { med ->
            MedicationItem(med)
        }
    }
}

@Composable
fun MedicationItem(medication: Medication) {
    Card(modifier = Modifier.padding(8.dp).fillMaxWidth()) {
        Column(Modifier.padding(16.dp)) {
            Text(text = medication.name, style = MaterialTheme.typography.titleMedium)
            Text(text = medication.description)
            medication.imageUri?.let { uri ->
                val painter = rememberAsyncImagePainter(uri.toUri())
                Image(painter = painter, contentDescription = null, modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp), contentScale = ContentScale.Crop)
            }
            Text(text = "${medication.dailyDose} pills/day")
        }
    }
}
