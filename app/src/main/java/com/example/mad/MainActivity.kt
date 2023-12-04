package com.example.mad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mad.ui.theme.MADTheme
import com.example.mad.ui.theme.screens.GameRatingScreens
import com.example.mad.Viewmodel.GameViewModel
import com.gowtham.ratingbar.RatingBar

import com.gowtham.ratingbar.RatingBarConfig
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MADTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    GameNavHost(navController = navController)
                }
            }
        }
    }
}

@Composable
private fun GameNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    val viewModel: GameViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = GameRatingScreens.StartScreen.name,
        modifier = modifier
    ) {
        composable(GameRatingScreens.StartScreen.name) {
            GameRatingStartScreen(
                viewModel = viewModel,
                onNavigateToRatingScreen = {
                    navController.navigate(GameRatingScreens.RatingScreen.name)
                }
            )
        }
        composable(GameRatingScreens.RatingScreen.name) {
            GameRatingScreen(
                viewModel = viewModel,
                onNavigateToSummaryScreen = {
                    navController.navigate(GameRatingScreens.SummaryScreen.name) {
                        popUpTo(GameRatingScreens.StartScreen.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(GameRatingScreens.SummaryScreen.name) {
            GameRatingSummaryScreen(
                viewModel = viewModel,
                onNavigateToStartScreen = {
                    navController.navigate(GameRatingScreens.StartScreen.name) {
                        popUpTo(GameRatingScreens.StartScreen.name) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun GameRatingStartScreen(
    viewModel: GameViewModel,
    onNavigateToRatingScreen: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.welcome),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = stringResource(R.string.click_start),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                viewModel.randomAssessableGame()
                onNavigateToRatingScreen()
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.start))
        }
    }
}

@Composable
fun GameRatingScreen(
    viewModel: GameViewModel,
    onNavigateToSummaryScreen: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.rate_game),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Text(
            text = viewModel.randomlyChosenGame.value,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        RatingBar(
            value = viewModel.gameRatingAccordingToUser.value,
            onValueChange = { newRating ->
                viewModel.gameRatingAccordingToUser.value = newRating
            },
            onRatingChanged = { rating ->
                // Do something with the final rating (e.g. save it to a database)
            },
            config = RatingBarConfig()
                .stepSize(StepSize.HALF)
                .style(RatingBarStyle.HighLighted)
        )


        Button(
            onClick = { onNavigateToSummaryScreen() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.to_summary))
        }
    }
}

@Composable
fun GameRatingSummaryScreen(
    viewModel: GameViewModel,
    onNavigateToStartScreen: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(
                R.string.rate_result,
                viewModel.randomlyChosenGame.value,
                viewModel.gameRatingAccordingToUser.value
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = { onNavigateToStartScreen() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = stringResource(R.string.start_over))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MADTheme {
    }
}