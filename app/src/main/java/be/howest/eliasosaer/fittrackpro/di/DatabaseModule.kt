package be.howest.eliasosaer.fittrackpro.di

import android.content.Context
import androidx.room.Room
import be.howest.eliasosaer.fittrackpro.data.local.room.AppDatabase
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WorkoutDao
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.CartDao
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WeeklyGoalDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "fittrack.db"
        ).build()

    @Provides
    fun provideWorkoutDao(db: AppDatabase): WorkoutDao =
        db.workoutDao()

    @Provides
    fun provideCartDao(db: AppDatabase): CartDao =
        db.cartDao()

    @Provides
    fun provideWeeklyGoalDao(db: AppDatabase): WeeklyGoalDao =
        db.weeklyGoalDao()
}
