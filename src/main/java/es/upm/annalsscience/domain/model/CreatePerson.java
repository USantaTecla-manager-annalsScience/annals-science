package es.upm.annalsscience.domain.model;

import java.util.List;

public class CreatePerson {
    private String name;
    private String surname;
    private String birthDate;
    private String deathDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<Long> categoriesId;

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public List<Long> getCategoriesId() {
        return categoriesId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public void setCategoriesId(List<Long> categoriesId) {
        this.categoriesId = categoriesId;
    }
}
