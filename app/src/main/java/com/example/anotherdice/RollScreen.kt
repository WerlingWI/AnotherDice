package com.example.anotherdice

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RollScreen(navController: NavHostController, dice: String?) {
    var result by remember { mutableStateOf(listOf<Int>()) }
    var rotation by remember { mutableStateOf(0f) }
    val animatedRotation by animateFloatAsState(targetValue = rotation)
    var showCelebration by remember { mutableStateOf(false) }
    var showDice by remember { mutableStateOf(false) }
    var diceCount by remember { mutableStateOf(1) }

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
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Rolling $diceCount $dice",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                Slider(
                    value = diceCount.toFloat(),
                    onValueChange = { diceCount = it.toInt() },
                    valueRange = 1f..10f,
                    steps = 9,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                Text(
                    text = "Number of Dice: $diceCount",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                AnimatedVisibility(visible = showDice) {
                    FlowRow(
                        mainAxisSpacing = 16.dp,
                        crossAxisSpacing = 16.dp,
                        modifier = Modifier.fillMaxWidth(),
                        mainAxisAlignment = FlowMainAxisAlignment.Center
                    ) {
                        result.forEach { res ->
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
                                    drawDiceFace(res)
                                }
                            }
                        }
                    }
                }
                Button(
                    onClick = {
                        rotation += 360f
                        result = List(diceCount) {
                            when (dice) {
                                "d6" -> Random.nextInt(1, 7)
                                "d20" -> Random.nextInt(1, 21)
                                else -> 0
                            }
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
                    Column {
                        result.forEachIndexed { index, res ->
                            Text(
                                text = "Result of Dice ${index + 1}: $res",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Green,
                                modifier = Modifier.padding(top = 16.dp)
                            )
                        }
                    }
                }
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