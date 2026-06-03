package be.howest.eliasosaer.fittrackpro.data.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import be.howest.eliasosaer.fittrackpro.domain.model.GoalType

@Entity(tableName = "weekly_goal")
data class WeeklyGoalEntity(
    @PrimaryKey val id: Long = 1L,
    val goalType: GoalType,
    val goalValue: Float
)
