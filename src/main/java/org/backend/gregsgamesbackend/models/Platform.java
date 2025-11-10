package org.backend.gregsgamesbackend.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
public class Platform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer platform_id; // Matches your (platform_id INT PRIMARY KEY)

    private String title;
    private String developer;

    @Column(name = "is_portable")
    private String isPortable;

    @Column(name = "release_date")
    private String releaseDate;
    private Double price;

    // This links to the 'platform' field in the Game entity
    @JsonBackReference // <-- ADD THIS
    @OneToMany(mappedBy = "platform")
    private Set<Game> games;

    public Integer getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getIsPortable() {
        return isPortable;
    }

    public void setIsPortable(String isPortable) {
        this.isPortable = isPortable;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
}