package es.upm.annalsscience.infrastructure.api.dto;

import java.util.List;

public class CreateProductDTO {
    private String name;
    private String creationDate;
    private String endDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<Long> categoriesId;
    private List<Long> personsId;
    private List<Long> entitiesId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getWikiUrl() {
        return wikiUrl;
    }

    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    public List<Long> getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(List<Long> categoriesId) {
        this.categoriesId = categoriesId;
    }

    public List<Long> getPersonsId() {
        return personsId;
    }

    public void setPersonsId(List<Long> personsId) {
        this.personsId = personsId;
    }

    public List<Long> getEntitiesId() {
        return entitiesId;
    }

    public void setEntitiesId(List<Long> entitiesId) {
        this.entitiesId = entitiesId;
    }
}
