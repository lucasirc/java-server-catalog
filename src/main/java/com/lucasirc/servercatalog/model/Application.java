package com.lucasirc.servercatalog.model;

import de.caluga.morphium.annotations.Entity;
import de.caluga.morphium.annotations.Id;

@Entity(translateCamelCase = true)
public class Application {

    @Id
    private Long id;
    private String name;
    private String owner;

    public Application(Long id, String name, String owner) {
        this.id = id;
        this.name = name;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
