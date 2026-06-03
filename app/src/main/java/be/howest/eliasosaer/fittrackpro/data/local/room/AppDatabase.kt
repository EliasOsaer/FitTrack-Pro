package be.howest.eliasosaer.fittrackpro.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WorkoutDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WorkoutEntity
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.CartDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.CartItemEntity
import be.howest.eliasosaer.fittrackpro.data.local.room.dao.WeeklyGoalDao
import be.howest.eliasosaer.fittrackpro.data.local.room.entity.WeeklyGoalEntity

@Database(
    entities = [WorkoutEntity::class, CartItemEntity::class, WeeklyGoalEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun cartDao(): CartDao
    abstract fun weeklyGoalDao(): WeeklyGoalDao
}
