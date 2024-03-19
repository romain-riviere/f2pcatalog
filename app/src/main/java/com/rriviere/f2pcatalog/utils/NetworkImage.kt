package com.rriviere.f2pcatalog.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.rriviere.f2pcatalog.R
import com.rriviere.f2pcatalog.model.Game
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.circular.CircularRevealPlugin
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PaletteLoadedListener
import com.skydoves.landscapist.palette.PalettePlugin

@Composable
fun NetworkImage(
  url: String,
  modifier: Modifier = Modifier,
  circularRevealEnabled: Boolean = false,
  contentScale: ContentScale = ContentScale.Crop,
  paletteLoadedListener: PaletteLoadedListener? = null
) {
  CoilImage(
    imageModel = { url },
    modifier = modifier,
    imageOptions = ImageOptions(contentScale = contentScale),
    component = rememberImageComponent {
      if (circularRevealEnabled) {
        +CircularRevealPlugin()
      } else {
        +CrossfadePlugin(duration = 350)
      }
      if (paletteLoadedListener != null) {
        +PalettePlugin(paletteLoadedListener = paletteLoadedListener)
      }
    },
    previewPlaceholder = painterResource(R.drawable.agricola),
    failure = {
      Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Text(
          text = "image request failed.",
          style = MaterialTheme.typography.bodyMedium
        )
      }
    },
  )
}

@Preview
@Composable
private fun NetworkImagePreview() {
  Game.mock().thumbnail?.let {
    NetworkImage(
      url = it,
      modifier = Modifier.fillMaxSize()
    )
  }
}
