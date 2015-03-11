package com.lucasirc.servercatalog.model;

import de.caluga.morphium.annotations.Entity;
import de.caluga.morphium.annotations.Id;
import de.caluga.morphium.annotations.Reference;
import de.caluga.morphium.annotations.caching.Cache;
import org.bson.types.ObjectId;

import java.util.List;
@Entity(translateCamelCase = true)
@Cache
public class Server {
    @Id
    private Long id;
    private String hostname;

    @Reference
    private List<Application> apps;

    public Server(Long id, String hostname) {
        this.id = id;

        this.hostname = hostname;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Application> getApps() {
        return apps;
    }

    public void setApps(List<Application> apps) {
        this.apps = apps;
    }
}
