package com.galaxydata.starscan.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class Person extends SwapiBaseEntity {

    private PersonProperties properties;

    public PersonProperties getProperties() {
        return properties;
    }

    public void setProperties(PersonProperties properties) {
        this.properties = properties;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PersonProperties {
        private String created;
        private String edited;
        private String name;
        private String gender;
        private String skin_color;
        private String hair_color;
        private String height;
        private String eye_color;
        private String mass;
        private String birth_year;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getSkin_color() {
            return skin_color;
        }

        public void setSkin_color(String skin_color) {
            this.skin_color = skin_color;
        }

        public String getHair_color() {
            return hair_color;
        }

        public void setHair_color(String hair_color) {
            this.hair_color = hair_color;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getEye_color() {
            return eye_color;
        }

        public void setEye_color(String eye_color) {
            this.eye_color = eye_color;
        }

        public String getMass() {
            return mass;
        }

        public void setMass(String mass) {
            this.mass = mass;
        }

        public String getBirth_year() {
            return birth_year;
        }

        public void setBirth_year(String birth_year) {
            this.birth_year = birth_year;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}