package com.rriviere.f2pcatalog.ui.details

import androidx.annotation.StringRes
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ViewCarousel
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rriviere.f2pcatalog.R
import com.rriviere.f2pcatalog.model.Game
import com.rriviere.f2pcatalog.ui.games.CarouselGames
import com.rriviere.f2pcatalog.ui.games.HomeGames
import com.rriviere.f2pcatalog.ui.games.SearchGames
import com.rriviere.f2pcatalog.ui.main.MainViewModel

@Composable
fun GameDetails(
    viewModel: MainViewModel,
    selectGame: (Long) -> Unit
) {
    val games: List<Game> by viewModel.gameList.collectAsState(initial = listOf())
    val isLoading: Boolean by viewModel.isLoading
    val selectedTab = BGGHomeTab.getTabFromResource(viewModel.selectedTab.value)
    val tabs = BGGHomeTab.entries.toTypedArray()

    Scaffold(
        Modifier.background(MaterialTheme.colorScheme.primaryContainer),
        topBar = { GameAppBar() },
        bottomBar = {
            NavigationBar(
                modifier = Modifier.navigationBarsPadding()
            ) {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                        label = { Text(text = stringResource(tab.title), color = Color.White) },
                        selected = tab == selectedTab,
                        onClick = { viewModel.selectTab(tab.title) },
                    )
                }
            }
        }
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)
        Crossfade(selectedTab) { destination ->
            when (destination) {
                BGGHomeTab.HOME -> HomeGames(modifier, games, selectGame)
                BGGHomeTab.CAROUSEL -> CarouselGames(modifier, games, selectGame)
                BGGHomeTab.SEARCH -> SearchGames(modifier, games, selectGame)
            }
        }
    }
    if (isLoading) {
        CircularProgressIndicator(
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun GameAppBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = stringResource(R.string.app_name),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        modifier = Modifier
          .statusBarsPadding()
          .height(58.dp)
    )
}

enum class BGGHomeTab(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    HOME(R.string.menu_home, Icons.Filled.Home),
    CAROUSEL(R.string.menu_carousel, Icons.Filled.ViewCarousel),
    SEARCH(R.string.menu_search, Icons.Filled.Search);

    companion object {
        fun getTabFromResource(@StringRes resource: Int): BGGHomeTab {
            return when (resource) {
                R.string.menu_carousel -> CAROUSEL
                R.string.menu_search -> SEARCH
                else -> HOME
            }
        }
    }
}
