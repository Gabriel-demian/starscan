package com.galaxydata.starscan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Starship extends SwapiBaseEntity {

    private StarshipProperties properties;

    public StarshipProperties getProperties() {
        return properties;
    }

    public void setProperties(StarshipProperties properties) {
        this.properties = properties;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StarshipProperties {
        private String created;
        private String edited;
        private String consumables;
        private String name;
        private String cargo_capacity;
        private String passengers;
        private String max_atmosphering_speed;
        private String crew;
        private String length;
        private String model;
        private String cost_in_credits;
        private String manufacturer;
        private String[] pilots;
        private String MGLT;
        private String starship_class;
        private String hyperdrive_rating;
        private String[] films;
        private String url;

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

        public String getConsumables() {
            return consumables;
        }

        public void setConsumables(String consumables) {
            this.consumables = consumables;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCargo_capacity() {
            return cargo_capacity;
        }

        public void setCargo_capacity(String cargo_capacity) {
            this.cargo_capacity = cargo_capacity;
        }

        public String getPassengers() {
            return passengers;
        }

        public void setPassengers(String passengers) {
            this.passengers = passengers;
        }

        public String getMax_atmosphering_speed() {
            return max_atmosphering_speed;
        }

        public void setMax_atmosphering_speed(String max_atmosphering_speed) {
            this.max_atmosphering_speed = max_atmosphering_speed;
        }

        public String getCrew() {
            return crew;
        }

        public void setCrew(String crew) {
            this.crew = crew;
        }

        public String getLength() {
            return length;
        }

        public void setLength(String length) {
            this.length = length;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getCost_in_credits() {
            return cost_in_credits;
        }

        public void setCost_in_credits(String cost_in_credits) {
            this.cost_in_credits = cost_in_credits;
        }

        public String getManufacturer() {
            return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
            this.manufacturer = manufacturer;
        }

        public String[] getPilots() {
            return pilots;
        }

        public void setPilots(String[] pilots) {
            this.pilots = pilots;
        }

        public String getMGLT() {
            return MGLT;
        }

        public void setMGLT(String MGLT) {
            this.MGLT = MGLT;
        }

        public String getStarship_class() {
            return starship_class;
        }

        public void setStarship_class(String starship_class) {
            this.starship_class = starship_class;
        }

        public String getHyperdrive_rating() {
            return hyperdrive_rating;
        }

        public void setHyperdrive_rating(String hyperdrive_rating) {
            this.hyperdrive_rating = hyperdrive_rating;
        }

        public String[] getFilms() {
            return films;
        }

        public void setFilms(String[] films) {
            this.films = films;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
