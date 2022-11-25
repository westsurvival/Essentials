package com.earth2me.essentials.config.mongo;

import com.earth2me.essentials.config.holders.UserConfigHolder;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.experimental.filters.Filters;
import org.bson.UuidRepresentation;

import java.util.UUID;

public class MongoConfigStorage {

    public static MongoConfigStorage INSTANCE = new MongoConfigStorage();

    private final MongoClient mongoClient;
    private final Datastore datastore;

    public MongoConfigStorage() {
        final String mongoUrlEnv = System.getenv("MONGO_URL");
        this.mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyConnectionString(new ConnectionString(mongoUrlEnv == null ? "mongodb://localhost:27017" : mongoUrlEnv))
                        .uuidRepresentation(UuidRepresentation.STANDARD)
                        .build()
        );
        this.datastore = Morphia.createDatastore(this.mongoClient, "essentials");
    }

    public void saveUser(EssentialsUserDocument userDocument) {
        this.datastore.save(userDocument);
    }

    public EssentialsUserDocument loadUser(UUID uuid) {
        EssentialsUserDocument document = this.datastore.find(EssentialsUserDocument.class).filter(Filters.eq("_id", uuid)).first();
        if(document == null) {
            document = new EssentialsUserDocument(uuid, "", new UserConfigHolder());
            this.datastore.save(document);
        }
        return document;
    }
}
