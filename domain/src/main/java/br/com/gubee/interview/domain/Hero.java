package br.com.gubee.interview.domain;

import br.com.gubee.interview.domain.enums.Race;
import br.com.gubee.interview.domain.vo.PowerStats;
import jakarta.validation.constraints.NotBlank;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Document
public class Hero implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private ObjectId id;

    @NotBlank
    private String name;

    private Race race;

    private Boolean enabled;

    private PowerStats powerStats;


    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    public Hero() {
    }

    public Hero(ObjectId id, String name, Race race, PowerStats powerStats, Boolean enabled) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.powerStats = powerStats;
        this.enabled = enabled;
    }


    public String getId() {
        return id != null ? id.toString() : null;
    }

    public void setId( ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public PowerStats getPowerStats() {
        return powerStats;
    }

    public void setPowerStats(PowerStats powerStats) {
        this.powerStats = powerStats;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return Objects.equals(id, hero.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", race=" + race +
                ", powerStats='" + powerStats + '\'' +
                ", enabled=" + enabled +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }



}
