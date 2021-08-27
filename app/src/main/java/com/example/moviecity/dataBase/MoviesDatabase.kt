package com.example.moviecity.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviecity.dao.*
import com.example.moviecity.dataBase.converters.*
import com.example.moviecity.models.*

@Database(
    entities = [ViewAllMoviesList::class, PopularResultList::class, TopRatedResultList::class, UpcomingResultList::class, MovieDetails::class, Favourite::class],
    version = 1, exportSchema = false
)
@TypeConverters(
    PopularListConverter::class,
    UpcomingListConverter::class,
    TopRatedListConverter::class,
    ViewAllListConverter::class,
    GenresConverter::class,
    CreditsConverter::class,
    CastConverter::class,
    ImageConverter::class
)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun popularFragDao(): FragPopularDao

    abstract fun topRatedFragDao(): FragTopRatedDao

    abstract fun upcomingFragDao(): FragUpcomingDao

    abstract fun popularDao(): PopularMoviesDao

    abstract fun topRatedDao(): TopRatedMoviesDao

    abstract fun upcomingDao(): UpcomingMoviesDao

    abstract fun detailsDao(): DetailsDao

    abstract fun favouriteDao(): FavouriteDao

    companion object {

        @Volatile
        private var INSTANCE: MoviesDatabase? = null

        fun getDatabase(context: Context): MoviesDatabase {

            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java,
                        "moviesDB"
                    ).build()
                }
            }
            return INSTANCE!!
        }

    }

}