package com.digitalsamurai.jni_test.presentation.screens.interpolation.neighbor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import com.digitalsamurai.jni_test.presentation.view.BitmapRenderer
import io.opentelemetry.api.trace.Span

object NeighborScreen :
    BaseScreen<NeighborScreenState, NeighborScreenEvent, NeighborScreenActions>() {

    override val routeName: String = "interpolation/neighbor"
    override val screenName: String = "NeighborScreen"

    @Composable
    override fun MakeViewModel(screenSpan: Span, navController: NavController): NeighborScreenViewModel {
        return hiltViewModel<NeighborScreenViewModel, NeighborScreenViewModel.Factory> { f ->
            f.get(screenSpan, navController)
        }
    }

    override suspend fun onEvent(
        event: NeighborScreenEvent,
        actions: NeighborScreenActions,
        snackbar: SnackbarHostState
    ) {
        when (event) {
            NeighborScreenEvent.BitmapSaving.Failed -> snackbar.showSnackbar(
                message = "Autosaving failed",
                duration = SnackbarDuration.Long
            )

            is NeighborScreenEvent.BitmapSaving.Success -> {
                val actionResult = snackbar.showSnackbar(
                    message = "Autosaved!",
                    actionLabel = "Cancel",
                    duration = SnackbarDuration.Short
                )
                if (actionResult == SnackbarResult.ActionPerformed) actions.undoImageSaving(event.fileName)
            }
        }
    }

    @Composable
    override fun Screen(state: NeighborScreenState, actions: NeighborScreenActions) {
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

            // generation status
            // image
            // settings
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