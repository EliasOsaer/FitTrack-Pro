package be.howest.eliasosaer.fittrackpro.di

import be.howest.eliasosaer.fittrackpro.data.repository.GearRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.WorkoutRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.CartRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.LightSensorRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.LocationRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.MovementSensorRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.OrderRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.WeeklyGoalRepositoryImpl
import be.howest.eliasosaer.fittrackpro.domain.repository.GearRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.CartRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.LightSensorRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.LocationRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.MovementSensorRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.OrderRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.WeeklyGoalRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindWorkoutRepository(
        impl: WorkoutRepositoryImpl
    ): WorkoutRepository

    @Binds
    abstract fun bindGearRepository(
        impl: GearRepositoryImpl
    ): GearRepository

    @Binds
    abstract fun bindCartRepository(
        impl: CartRepositoryImpl
    ): CartRepository

    @Binds
    abstract fun bindLocationRepository(
        impl: LocationRepositoryImpl
    ): LocationRepository

    @Binds
    abstract fun bindOrderRepository(
        impl: OrderRepositoryImpl
    ): OrderRepository

    @Binds
    abstract fun bindLightSensorRepository(
        impl: LightSensorRepositoryImpl
    ): LightSensorRepository

    @Binds
    abstract fun bindMovementSensorRepository(
        impl: MovementSensorRepositoryImpl
    ): MovementSensorRepository

    @Binds
    abstract fun bindWeeklyGoalRepository(
        impl: WeeklyGoalRepositoryImpl
    ): WeeklyGoalRepository
}
