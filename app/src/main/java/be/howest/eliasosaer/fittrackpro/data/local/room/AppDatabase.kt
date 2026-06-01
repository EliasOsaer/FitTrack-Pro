package be.howest.eliasosaer.fittrackpro.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WorkoutDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WorkoutEntity

@Database(
    entities = [WorkoutEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}