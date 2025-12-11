package com.digitalsamurai.jni_test.screens.interpolation.bicubic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.core.views.MyButton
import com.digitalsamurai.jni_test.view.BitmapRenderer
import io.opentelemetry.api.trace.Span

object BicubicScreen : BaseScreen<BicubicScreenState, BicubicScreenEvent, BicubicScreenActions>() {

    override val routeName: String = "interpolation/bicubic"
    override val screenName: String = "BicubicScreen"

    override suspend fun onEvent(
        event: BicubicScreenEvent,
        actions: BicubicScreenActions,
        snackbar: SnackbarHostState
    ) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun MakeViewModel(screenSpan: Span, navController: NavController): BicubicScreenViewModel {
        return hiltViewModel<BicubicScreenViewModel, BicubicScreenViewModel.Factory> { f ->
            f.get(screenSpan, navController)
        }
    }

    @Composable
    override fun Screen(state: BicubicScreenState, actions: BicubicScreenActions) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (generateButton, containerContent) = createRefs()
            Column(
                modifier = Modifier
                    .constrainAs(containerContent) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(generateButton.top, margin = 8.dp)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    },
                verticalArrangement = Arrangement.Center
            ) {
                BitmapRenderer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally)
                        .weight(1f),
                    state = state.bitmapRendererState,
                    onClick = actions::onBitmapRendererClicked
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxSize()
                ) {

                }
            }

            MyButton(
                modifier = Modifier
                    .height(50.dp)
                    .constrainAs(generateButton) {
                        bottom.linkTo(parent.bottom, margin = 8.dp)
                        start.linkTo(parent.start, margin = 8.dp)
                        end.linkTo(parent.end, margin = 8.dp)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
                onClick = actions::onGenerateButtonClicked
            ) {
                Text("Generate")
            }
        }
    }
}