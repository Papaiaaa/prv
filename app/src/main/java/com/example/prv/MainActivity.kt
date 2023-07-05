package com.example.prv

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.prv.ui.theme.PrvTheme
import kotlinx.coroutines.delay
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PrvTheme {
                MyApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun MyApp(modifier: Modifier = Modifier) {
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    Surface(modifier, color = MaterialTheme.colorScheme.background) {
        if (shouldShowOnboarding) {
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        } else {
            Greetings()
        }
    }
}

////new start///
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    buttonHeight: Dp = 48.dp, // Размер высоты кнопки (по умолчанию 48.dp)
    buttonWidth: Dp = 180.dp, // Размер ширины кнопки (по умолчанию 180.dp)
    modifier: Modifier = Modifier
) {
    var isButtonVisible by remember { mutableStateOf(false) }
    // Получаем ориентацию устройства
    val configuration = LocalConfiguration.current
    val isVertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val buttonAnimatable = remember { Animatable(0f) }
    // Анимация прозрачности кнопки с задержкой в 100 мс для создания эффекта появления
    val buttonAlpha by animateFloatAsState(
        targetValue = if (isButtonVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, delayMillis = 100)
    )

    val transition = updateTransition(targetState = isButtonVisible, label = "ButtonTransition")
    val translateY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 1500, easing = LinearOutSlowInEasing) }
    ) { state ->
        if (state) 0.dp else 600.dp // Change the vertical position here (200.dp is just an example)
    }

    LaunchedEffect(true) {
        delay(100)
        isButtonVisible = true
        buttonAnimatable.animateTo(1f)
    }
    if (isVertical)
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = R.drawable.prv),
            contentDescription = "logo",
            modifier = Modifier
                .padding(16.dp)
                .heightIn(min = 160.dp, max = 260.dp)
                .widthIn(min = 160.dp, max = 260.dp)
                .clip(RoundedCornerShape(120.dp))
        )
       Text("Welcome to my first App on the Kotlin")
    }
    else
        Column() {

        }
    if (isVertical)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(top = translateY.value.dp)
                .height(buttonHeight)
                .width(buttonWidth)
                .alpha(buttonAlpha),
            onClick = onContinueClicked
        ) {
            Text("Let`s go")
        }
    }
    else
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}
}

//////new end///////
////////////////work//////////////
/*
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isButtonVisible by remember { mutableStateOf(false) }
    // Получаем ориентацию устройства
    val configuration = LocalConfiguration.current
    val isVertical = configuration.orientation == Configuration.ORIENTATION_PORTRAIT

    val buttonAnimatable = remember { Animatable(0f) }
    val buttonAlpha by animateFloatAsState(
        targetValue = if (isButtonVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 1000, delayMillis = 100)
    )

    val transition = updateTransition(targetState = isButtonVisible, label = "ButtonTransition")
    val translateY by transition.animateDp(
        transitionSpec = { tween(durationMillis = 3000, easing = LinearOutSlowInEasing) }
    ) { state ->
        if (state) 0.dp else 600.dp // Change the vertical position here (200.dp is just an example)
    }

    LaunchedEffect(true) {
        delay(100)
        isButtonVisible = true
        buttonAnimatable.animateTo(1f)
    }

    Column(
        modifier = modifier.fillMaxSize(),
       // horizontalAlignment = Alignment.CenterHorizontally
        horizontalAlignment = if (isVertical) Alignment.CenterHorizontally else Alignment.Start
    ) {

        Image(
            painter = if (isVertical) painterResource(id = R.drawable.prv) else painterResource(id = R.drawable.prv),
            contentDescription = "logo",
            modifier = Modifier
                .padding(16.dp)
                .heightIn(min = 160.dp, max = 260.dp)
                .widthIn(min = 160.dp, max = 260.dp)
                .clip(RoundedCornerShape(120.dp))
        )
        Text("Welcome to my first App on the Kotlin")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier
                .padding(top = translateY.value.dp)
                .alpha(buttonAlpha),
            onClick = onContinueClicked
        ) {
            Text("Let`s go")
        }
    }
}
////////////////////////work end//////////////////////////////
 */
@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(1000) { "$it" }
) {
    LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
        items(items = names) { name ->
            Greeting(name = name)
        }
    }
}

@Composable
private fun Greeting(name: String) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        CardContent(name)
    }
}

@Composable
private fun CardContent(name: String) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .padding(12.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(12.dp)
        ) {
            Text(text = "Hello, ")
            Text(
                text = name, style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            if (expanded) {
                Text(
                    text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),
                )
            }
        }
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = if (expanded) Filled.ExpandLess else Filled.ExpandMore,
                contentDescription = if (expanded) {
                    stringResource(R.string.show_less)
                } else {
                    stringResource(R.string.show_more)
                }
            )
        }
    }
}

@Preview(
    showBackground = true,
    widthDp = 320,
    uiMode = UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    PrvTheme {
        Greetings()
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    PrvTheme {
        OnboardingScreen(onContinueClicked = {})
    }
}

@Preview
@Composable
fun MyAppPreview() {
    PrvTheme {
        MyApp(Modifier.fillMaxSize())
    }
}