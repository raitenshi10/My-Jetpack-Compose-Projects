package com.example.basicjetpackcompose

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.basicjetpackcompose.data.OnBoardingItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

@ExperimentalPagerApi
@Composable
@Preview
fun OnBoarding() {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val items = OnBoardingItem.get()
        val state = rememberPagerState(pageCount = items.size)

        TopSection()
        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxSize()
                .weight(0.8f)
        ) { page ->
            OnBoardingItem(items[page])
        }

        BottomSection(items.size, index = state.currentPage) {
            if (state.currentPage + 1 < items.size) {
                scope.launch {
                    state.scrollToPage(state.currentPage + 1)
                }
            }
        }
    }
}

@Composable
fun TopSection(modifier: Modifier = Modifier) {

    val systemUiController = rememberSystemUiController()
    val useDarkIcon = MaterialTheme.colors.isLight

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcon
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
    ) {
        IconButton(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.CenterStart)
        ) {
            Icon(Icons.Outlined.KeyboardArrowLeft, null)
        }

        TextButton(
            onClick = { /*TODO*/ },
            modifier = modifier
                .align(Alignment.CenterEnd),
        ) {
            Text(
                text = "SKIP",
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun BottomSection(
    size: Int,
    index: Int,
    onNextClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Indicators(size, index)

        FloatingActionButton(
            onClick = onNextClicked,
            modifier = Modifier.align(Alignment.CenterEnd),
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ) {
            Icon(Icons.Outlined.KeyboardArrowRight, null)
        }
    }
}

@Composable
fun Indicators(size: Int, index: Int) {
    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            repeat(size) {
                Indicator(isSelected = it == index)
            }
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val width = animateDpAsState(
        targetValue = if (isSelected) 15.dp else 10.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = Modifier
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onBackground.copy(
                    alpha = 0.5f
                )
            )
    )
}

@Composable
fun OnBoardingItem(onBoardingItem: OnBoardingItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp)
    ) {

        Image(
            painter = painterResource(onBoardingItem.image),
            alignment = Alignment.Center,
            contentDescription = "null"
        )

        Text(
            text = onBoardingItem.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.onBackground
        )

        Text(
            text = onBoardingItem.text,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colors.onBackground.copy(0.8f)
        )
    }
}