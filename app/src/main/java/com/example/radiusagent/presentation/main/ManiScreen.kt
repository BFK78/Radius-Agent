package com.example.radiusagent.presentation.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.radiusagent.presentation.main.viewmodel.MainViewModel
import com.example.radiusagent.presentation.util.mapToDrawable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val facilityState = viewModel.facilityState

    val optionList = viewModel.optionsList

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .shadow(1.dp)
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(16.dp)
            ) {
                Text(
                    text = facilityState.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {
                    items(optionList) {
                        CustomCard(
                            painter = painterResource(id = it.icon.mapToDrawable()),
                            contentDescription = "",
                            title = it.name
                        ) {
                            viewModel.onCardSelect(
                                facilityState.facilityId,
                                option = it
                            )
                        }
                    }
                }
                if (facilityState.facilityId.toInt() > 1)
                    Button(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                        onClick = {
                            viewModel.onBackPressed(facilityId = facilityState.facilityId)
                        }
                    ) {
                        Text(text = "Back")
                    }
            }
        }
    }
}

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    contentDescription: String,
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .height(150.dp)
            .padding(16.dp)
            .clickable {
                onClick()
            }
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Icon(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painter,
            contentDescription = contentDescription
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = title,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

    }
}