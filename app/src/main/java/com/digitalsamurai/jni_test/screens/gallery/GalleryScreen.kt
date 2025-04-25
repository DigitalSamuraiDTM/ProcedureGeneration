package com.digitalsamurai.jni_test.screens.gallery

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.digitalsamurai.jni_test.core.screen.BaseScreen
import com.digitalsamurai.jni_test.view.BitmapRenderer
import com.digitsamurai.utils.extensions.pixToDp

object GalleryScreen : BaseScreen<GalleryScreenState, GalleryScreenEvent, GalleryScreenActions>() {

    override val routeName: String = "gallery"
    override val screenName: String = "GalleryScreen"


    @Composable
    override fun MakeViewModel(): GalleryScreenViewModel {
        return hiltViewModel()
    }

    override suspend fun onEvent(
        event: GalleryScreenEvent,
        actions: GalleryScreenActions,
        snackbar: SnackbarHostState
    ) {
        TODO("Not yet implemented")
    }

    @Composable
    override fun Screen(state: GalleryScreenState, actions: GalleryScreenActions) {
        LazyVerticalGrid(modifier = Modifier.fillMaxSize(), columns = GridCells.Fixed(2)) {
            items(state.bitmapItems) { item ->
                BitmapRenderer(
                    modifier = Modifier.height((LocalView.current.width / 2).pixToDp()),
                    state = item,
                    onClick = actions::onGalleryItemClicked,
                    onBitmapIdLoad = actions::onGalleryItemLoad
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
//    StorageScreen.Screen()
}