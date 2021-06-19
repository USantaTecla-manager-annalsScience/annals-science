package es.upm.annalsscience.infrastructure.api.dto;

import java.util.List;

public class EntityDTO {
    private Long id;
    private String name;
    private String creationDate;
    private String endDate;
    private String description;
    private String imageUrl;
    private String wikiUrl;
    private List<CategoryDTO> categoriesId;
    private List<PersonDTO> personsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<CategoryDTO> getCategoriesId() {
        return categoriesId;
    }

    public void setCategoriesId(List<CategoryDTO> categoriesId) {
        this.categoriesId = categoriesId;
    }

    public List<PersonDTO> getPersonsId() {
        return personsId;
    }

    public void setPersonsId(List<PersonDTO> personsId) {
        this.personsId = personsId;
    }
}
