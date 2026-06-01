package be.howest.eliasosaer.fittrackpro.di

import be.howest.eliasosaer.fittrackpro.BuildConfig
import be.howest.eliasosaer.fittrackpro.data.messaging.OrderPublisher
import be.howest.eliasosaer.fittrackpro.data.messaging.RabbitMQPublisher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MessagingModule {
    private val rabbitMQHost =
        "amqp://${BuildConfig.AMQPusername}:${BuildConfig.AMQPpassword}@${BuildConfig.AMQPurl}"

    @Provides
    @Singleton
    fun providePublishService(): OrderPublisher = lazy {
        RabbitMQPublisher(rabbitMQHost, BuildConfig.AMQPpublishtopic)
    }.value
}