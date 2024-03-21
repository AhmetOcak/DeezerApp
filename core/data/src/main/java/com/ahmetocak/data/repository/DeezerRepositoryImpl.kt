package com.ahmetocak.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.ahmetocak.common.Response
import com.ahmetocak.common.mapResponse
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
import com.ahmetocak.models.AlbumDetails
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
) : DeezerRepository {

    override suspend fun getAlbumDetails(albumId: Long): Response<AlbumDetails> {
        return remoteDataSource.getAlbumDetails(albumId).mapResponse { it.toAlbumDetails() }
    }

    override suspend fun getAllFavoriteSongs(): Response<Flow<List<FavoriteSongs>>> {
        return localDataSource.getAllFavoriteSongs()
            .mapResponse {
                it.map { value ->
                    value.toFavoriteSongsList()
                }
            }
    }

    override suspend fun addFavoriteSong(favoriteSongs: FavoriteSongs): Response<Unit> {
        return localDataSource.addFavoriteSong(favoriteSongs.toFavoriteSongsEntity())
    }

    override suspend fun removeFavoriteSong(songId: Long): Response<Unit> {
        return localDataSource.removeFavoriteSong(songId)
    }

    override suspend fun getArtists(genreId: Long): Response<Artist> {
        return remoteDataSource.getArtists(genreId).mapResponse { it.toArtist() }
    }

    override suspend fun getArtistDetails(artistId: Long): Response<ArtistDetail> {
        return remoteDataSource.getArtistDetails(artistId).mapResponse { it.toArtistDetail() }
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

    override suspend fun getMusicGenres(): Response<MusicGenre> {
        return remoteDataSource.getMusicGenres().mapResponse { it.toMusicGenre() }
    }
}