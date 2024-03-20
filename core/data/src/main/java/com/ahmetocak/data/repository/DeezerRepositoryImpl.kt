package com.ahmetocak.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ahmetocak.data.mapper.toAlbumDetails
import com.ahmetocak.data.mapper.toArtist
import com.ahmetocak.data.mapper.toArtistAlbums
import com.ahmetocak.data.mapper.toArtistDetail
import com.ahmetocak.data.mapper.toFavoriteSongsEntity
import com.ahmetocak.data.mapper.toFavoriteSongsList
import com.ahmetocak.data.mapper.toMusicGenre
import com.ahmetocak.database.datasource.FavoriteSongsLocalDataSource
import com.ahmetocak.models.Artist
import com.ahmetocak.models.ArtistAlbums
import com.ahmetocak.models.ArtistDetail
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.models.MusicGenre
import com.ahmetocak.models.albumdetail.AlbumDetails
import com.ahmetocak.network.datasource.ArtistAlbumsPagingSource
import com.ahmetocak.network.datasource.RemoteDeezerDataSource
import com.ahmetocak.network.retrofit.DeezerApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeezerRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDeezerDataSource,
    private val localDataSource: FavoriteSongsLocalDataSource,
    private val deezerApi: DeezerApi
): DeezerRepository {

    override suspend fun getAlbumDetails(albumId: Long): AlbumDetails {
        return remoteDataSource.getAlbumDetails(albumId).toAlbumDetails()
    }

    override suspend fun getAllFavoriteSongs(): List<FavoriteSongs> {
        return localDataSource.getAllFavoriteSongs().toFavoriteSongsList()
    }

    override suspend fun addFavoriteSong(favoriteSongs: FavoriteSongs) {
        return localDataSource.addFavoriteSong(favoriteSongs.toFavoriteSongsEntity())
    }

    override suspend fun removeFavoriteSong(songId: Long) {
        return localDataSource.removeFavoriteSong(songId)
    }

    override suspend fun getArtists(genreId: Long): Artist {
        return remoteDataSource.getArtists(genreId).toArtist()
    }

    override suspend fun getArtistDetails(artistId: Long): ArtistDetail {
        return remoteDataSource.getArtistDetails(artistId).toArtistDetail()
    }

    override fun getArtistAlbums(artistId: Long): Flow<PagingData<ArtistAlbums>> {
        val artistAlbumsPagingSource = ArtistAlbumsPagingSource(artistId, deezerApi)
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = { artistAlbumsPagingSource }
        ).flow.map { pagingData ->
            pagingData.map { albumsDto ->
                albumsDto.toArtistAlbums()
            }
        }
    }

    override suspend fun getMusicGenres(): MusicGenre {
        return remoteDataSource.getMusicGenres().toMusicGenre()
    }
}