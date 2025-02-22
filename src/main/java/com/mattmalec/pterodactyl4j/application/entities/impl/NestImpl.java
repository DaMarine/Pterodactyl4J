package com.mattmalec.pterodactyl4j.application.entities.impl;

import com.mattmalec.pterodactyl4j.PteroAction;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationServer;
import com.mattmalec.pterodactyl4j.application.entities.ApplicationEgg;
import com.mattmalec.pterodactyl4j.application.entities.Nest;
import com.mattmalec.pterodactyl4j.utils.Relationed;
import org.json.JSONObject;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class NestImpl implements Nest {

    private JSONObject json;
    private JSONObject relationships;
    private PteroApplicationImpl impl;

    public NestImpl(JSONObject json, PteroApplicationImpl impl) {
        this.json = json.getJSONObject("attributes");
        this.relationships = json.getJSONObject("attributes").optJSONObject("relationships");
        this.impl = impl;
    }

    @Override
    public String getUUID() {
        return json.getString("uuid");
    }

    @Override
    public String getAuthor() {
        return json.getString("author");
    }

    @Override
    public String getName() {
        return json.getString("name");
    }

    @Override
    public String getDescription() {
        return json.getString("description");
    }

    @Override
    public Relationed<List<ApplicationEgg>> getEggs() {
        NestImpl nest = this;
        return new Relationed<List<ApplicationEgg>>() {
            @Override
            public PteroAction<List<ApplicationEgg>> retrieve() {
                return impl.retrieveEggsByNest(nest);
            }

            @Override
            public Optional<List<ApplicationEgg>> get() {
                if(!json.has("relationships")) return Optional.empty();
                List<ApplicationEgg> eggs = new ArrayList<>();
                JSONObject json = relationships.getJSONObject("eggs");
                if(json.isNull("attributes")) return Optional.empty();
                for(Object o : json.getJSONArray("data")) {
                    JSONObject egg = new JSONObject(o.toString());
                    eggs.add(new ApplicationEggImpl(egg, impl));
                }
                return Optional.of(Collections.unmodifiableList(eggs));
            }
        };
    }

    @Override
    public Optional<List<ApplicationServer>> getServers() {
        if(!json.has("relationships")) return Optional.empty();
        List<ApplicationServer> servers = new ArrayList<>();
        JSONObject json = relationships.getJSONObject("servers");
        if(json.isNull("attributes")) return Optional.empty();
        for(Object o : json.getJSONArray("data")) {
            JSONObject server = new JSONObject(o.toString());
            servers.add(new ApplicationServerImpl(impl, server));
        }
        return Optional.of(Collections.unmodifiableList(servers));
    }

    @Override
    public long getIdLong() {
        return json.getLong("id");
    }

    @Override
    public OffsetDateTime getCreationDate() {
        return OffsetDateTime.parse(json.optString("created_at"));
    }

    @Override
    public OffsetDateTime getUpdatedDate() {
        return OffsetDateTime.parse(json.optString("updated_at"));
    }

    @Override
    public String toString() {
        return json.toString(4);
    }
}
