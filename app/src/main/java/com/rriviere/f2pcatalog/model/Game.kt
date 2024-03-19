package com.rriviere.f2pcatalog.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
@Immutable
data class Game(
    @PrimaryKey val id: Long,
    val title: String,
    val thumbnail: String?,
    val shortDescription: String?,
    val gameUrl: String?,
    val genre: String?,
    val platform: String?,
    val publisher: String?,
    val developer: String?,
    val releaseDate: String?,
    val freetogameProfileUrl: String?
) {

    companion object {

        fun mock() = Game(
            id = 540,
            title = "Overwatch 2",
            thumbnail = "https://www.freetogame.com/g/540/thumbnail.jpg",
            shortDescription = "A hero-focused first-person team shooter from Blizzard Entertainment.",
            gameUrl = "https://www.freetogame.com/open/overwatch-2",
            genre = "Shooter",
            platform = "PC (Windows)",
            publisher = "Activision Blizzard",
            developer = "Blizzard Entertainment",
            releaseDate = "2022-10-04",
            freetogameProfileUrl = "https://www.freetogame.com/overwatch-2"
        )
    }
}
