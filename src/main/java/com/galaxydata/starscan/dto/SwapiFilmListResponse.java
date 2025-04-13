package com.galaxydata.starscan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SwapiFilmListResponse {

    private String message;

    @JsonProperty("result")
    private List<FilmResult> results;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FilmResult> getResults() {
        return results;
    }

    public void setResults(List<FilmResult> results) {
        this.results = results;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilmResult {
        private String uid;
        private String description;
        private String _id;
        private Film.FilmProperties properties;

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public Film.FilmProperties getProperties() {
            return properties;
        }

        public void setProperties(Film.FilmProperties properties) {
            this.properties = properties;
        }
    }
}