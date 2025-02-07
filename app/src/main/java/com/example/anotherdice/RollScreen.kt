package com.example.anotherdice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlin.random.Random
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RollScreen(navController: NavHostController, dice: String?) {
    val result = remember { mutableStateOf(0) }
    val animatedResult by animateIntAsState(targetValue = result.value)
    var rotation by remember { mutableStateOf(0f) }
    val animatedRotation by animateFloatAsState(targetValue = rotation)
    var showCelebration by remember { mutableStateOf(false) }
    var showDice by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Dice Roller") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Rolling a $dice",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            AnimatedVisibility(visible = showDice) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .background(Color.Gray, shape = RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Canvas(
                        modifier = Modifier
                            .size(100.dp)
                            .graphicsLayer(rotationZ = animatedRotation)
                    ) {
                        drawDiceFace(result.value)
                    }
                }
            }
            Button(
                onClick = {
                    rotation += 360f
                    result.value = when (dice) {
                        "d6" -> Random.nextInt(1, 7)
                        "d20" -> Random.nextInt(1, 21)
                        else -> 0
                    }
                    showCelebration = true
                    showDice = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text("Roll")
            }
            AnimatedVisibility(visible = showCelebration) {
                Text(
                    text = "Result: $animatedResult",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Green,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

fun DrawScope.drawDiceFace(result: Int) {
    val dotRadius = 10.dp.toPx()
    val centerX = size.width / 2
    val centerY = size.height / 2
    val offset = 30.dp.toPx()

    when (result) {
        1 -> drawCircle(Color.Black, dotRadius, center = center)
        2 -> {
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY + offset))
        }
        3 -> {
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center)
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY + offset))
        }
        4 -> {
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY + offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY + offset))
        }
        5 -> {
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center)
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY + offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY + offset))
        }
        6 -> {
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY - offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX - offset, y = centerY + offset))
            drawCircle(Color.Black, dotRadius, center = center.copy(x = centerX + offset, y = centerY + offset))
        }
    }
}