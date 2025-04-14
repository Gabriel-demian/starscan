package com.galaxydata.starscan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Base class for all SWAPI entities, containing common properties.")
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class SwapiBaseEntity {

    private String _id;
    private String description;
    private String uid;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}
