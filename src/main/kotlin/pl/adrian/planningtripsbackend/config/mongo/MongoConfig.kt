package pl.adrian.planningtripsbackend.config.mongo

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.boot.autoconfigure.mongo.MongoProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
@EnableMongoAuditing
class MongoConfig(private val mongoProperties: MongoProperties) {

    @Bean
    fun mongoClient(): MongoClient? {
        val connectionString = ConnectionString(mongoProperties.uri)
        val mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build()
        return MongoClients.create(mongoClientSettings)
    }

    @Bean
    fun mongoTemplate(): MongoTemplate? {
        return MongoTemplate(mongoClient()!!, mongoProperties.database)
    }
}