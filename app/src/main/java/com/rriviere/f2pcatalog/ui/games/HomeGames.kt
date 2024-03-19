package com.rriviere.f2pcatalog.ui.games

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rriviere.f2pcatalog.model.Game
import com.rriviere.f2pcatalog.ui.theme.F2PCatalogTheme
import com.rriviere.f2pcatalog.utils.EndlessLazyColumn
import com.rriviere.f2pcatalog.utils.NetworkImage

@Composable
fun HomeGames(
    modifier: Modifier = Modifier,
    games: List<Game>,
    selectGame: (Long) -> Unit,
) {
    EndlessLazyColumn(
        items = games,
        itemKey = { game: Game -> game.id },
        itemContent = { game: Game ->
            HomeGame(
                game = game,
                selectGame = selectGame
            )
        },
        loadingItem = { Text(text = "Loading...") }) {

    }
}

@Composable
private fun HomeGame(
    modifier: Modifier = Modifier,
    game: Game,
    selectGame: (Long) -> Unit = {},
) {
    Surface(
        modifier = modifier
            .padding(4.dp)
            .clickable(
                onClick = { selectGame(game.id) }
            ),
        color = MaterialTheme.colorScheme.onBackground,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {

            if (!game.thumbnail.isNullOrEmpty()) NetworkImage(
                url = game.thumbnail,
            )

            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = game.title,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center,
            )

            if (!game.releaseDate.isNullOrEmpty()) Text(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .padding(bottom = 12.dp),
                text = game.releaseDate,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }

    }
}

@Composable
@Preview(name = "HomeGame Light Theme")
private fun HomeGamePreviewLight() {
    F2PCatalogTheme(darkTheme = false) {
        HomeGame(
            game = Game.mock()
        )
    }
}

@Composable
@Preview(name = "HomeGame Dark Theme")
private fun HomeGamePreviewDark() {
    F2PCatalogTheme(darkTheme = true) {
        HomeGame(
            game = Game.mock()
        )
    }
}
