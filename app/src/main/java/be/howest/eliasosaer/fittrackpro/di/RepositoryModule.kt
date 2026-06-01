package be.howest.eliasosaer.fittrackpro.di

import be.howest.eliasosaer.fittrackpro.data.repository.GearRepositoryImpl
import be.howest.eliasosaer.fittrackpro.data.repository.WorkoutRepositoryImpl
import be.howest.eliasosaer.fittrackpro.domain.repository.GearRepository
import be.howest.eliasosaer.fittrackpro.domain.repository.WorkoutRepository
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
}