package com.galaxydata.starscan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Represents a Star Wars vehicle with detailed properties.")
public class Vehicle extends SwapiBaseEntity {

    private VehicleProperties properties;

    public Vehicle.VehicleProperties getProperties() {
        return properties;
    }

    public void setProperties(Vehicle.VehicleProperties properties) {
        this.properties = properties;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VehicleProperties {
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
        private String vehicle_class;
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

        public String getVehicle_class() {
            return vehicle_class;
        }

        public void setVehicle_class(String vehicle_class) {
            this.vehicle_class = vehicle_class;
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
