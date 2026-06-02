package be.howest.eliasosaer.fittrackpro.di

import be.howest.eliasosaer.fittrackpro.BuildConfig
import be.howest.eliasosaer.fittrackpro.data.messaging.DeliveryConsumer
import be.howest.eliasosaer.fittrackpro.data.messaging.OrderPublisher
import be.howest.eliasosaer.fittrackpro.data.messaging.RabbitMQDeliveryConsumer
import be.howest.eliasosaer.fittrackpro.data.messaging.RabbitMQOrderPublisher
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
        RabbitMQOrderPublisher(rabbitMQHost, BuildConfig.AMQPpublishtopic)
    }.value

    @Provides
    @Singleton
    fun provideConsumeService(): DeliveryConsumer = lazy {
        RabbitMQDeliveryConsumer(rabbitMQHost, BuildConfig.AMQPsubscribetopic)
    }.value
}