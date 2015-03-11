package com.lucasirc.servercatalog.model;

import java.util.List;

public class Server {

    private Long id;

    private String hostname;
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
