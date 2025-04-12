package com.digitalsamurai.jni_test.screens.storage

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.BitmapRenderer

object GalleryScreen : BaseScreen<GalleryScreenState, GalleryScreenEvent, GalleryScreenActions>() {

    override val routeName: String = "gallery"

    @Composable
    override fun MakeViewModel(): GalleryScreenViewModel {
        return hiltViewModel()
    }

    override suspend fun onEvent(event: GalleryScreenEvent, actions: GalleryScreenActions, snackbar: SnackbarHostState) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Screen(state: GalleryScreenState, actions: GalleryScreenActions) {

        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Adaptive(120.dp)) {
            state.bitmapItems.forEach { item ->
                item {
                    BitmapRenderer(
                        modifier = Modifier
                            .fillMaxWidth(),
                        state = item,
                        onClick = actions::onGalleryItemClicked
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
//    StorageScreen.Screen()
}