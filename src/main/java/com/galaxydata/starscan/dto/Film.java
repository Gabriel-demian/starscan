package com.galaxydata.starscan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a Star Wars film with detailed properties.")
public class Film extends SwapiBaseEntity {

    private FilmProperties properties;

    public FilmProperties getProperties() {
        return properties;
    }

    public void setProperties(FilmProperties properties) {
        this.properties = properties;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilmProperties {
        private String created;
        private String edited;
        private String producer;
        private String title;
        private int episode_id;
        private String director;
        private String release_date;
        private String opening_crawl;
        private String url;
        private String[] starships;
        private String[] vehicles;
        private String[] characters;

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public String getEdited() {
            return edited;
        }

        public void setEdited(String edited) {
            this.edited = edited;
        }

        public String getProducer() {
            return producer;
        }

        public void setProducer(String producer) {
            this.producer = producer;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getEpisode_id() {
            return episode_id;
        }

        public void setEpisode_id(int episode_id) {
            this.episode_id = episode_id;
        }

        public String getDirector() {
            return director;
        }

        public void setDirector(String director) {
            this.director = director;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getOpening_crawl() {
            return opening_crawl;
        }

        public void setOpening_crawl(String opening_crawl) {
            this.opening_crawl = opening_crawl;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String[] getStarships() {
            return starships;
        }

        public void setStarships(String[] starships) {
            this.starships = starships;
        }

        public String[] getVehicles() {
            return vehicles;
        }

        public void setVehicles(String[] vehicles) {
            this.vehicles = vehicles;
        }

        public String[] getCharacters() {
            return characters;
        }

        public void setCharacters(String[] characters) {
            this.characters = characters;
        }
    }
}