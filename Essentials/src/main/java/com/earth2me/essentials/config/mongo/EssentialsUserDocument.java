package com.earth2me.essentials.config.mongo;

import com.earth2me.essentials.config.holders.UserConfigHolder;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;

import java.util.UUID;

@Entity("users")
public class EssentialsUserDocument {

    @Id
    private final UUID uuid;
    private String name;
    private final UserConfigHolder holder;

    public EssentialsUserDocument(UUID uuid, String name, UserConfigHolder holder) {
        this.uuid = uuid;
        this.name = name;
        this.holder = holder;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserConfigHolder getHolder() {
        return holder;
    }
}
