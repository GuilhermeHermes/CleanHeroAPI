package br.com.gubee.interview.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;



public class PowerStats implements Serializable {
    private static final long serialVersionUID = 1L;

    @Min(value = 0, message = "message.powerstats.strength.min.0")
    @Max(value = 100, message = "message.powerstats.strength.max.100")
    @NotNull(message = "message.powerstats.strength.mandatory")
    private Integer strength;

    @Min(value = 0, message = "message.powerstats.agility.min.0")
    @Max(value = 100, message = "message.powerstats.agility.max.100")
    @NotNull(message = "message.powerstats.agility.mandatory")
    private Integer agility;

    @Min(value = 0, message = "message.powerstats.dexterity.min.0")
    @Max(value = 100, message = "message.powerstats.dexterity.max.100")
    @NotNull(message = "message.powerstats.dexterity.mandatory")
    private Integer dexterity;

    @Min(value = 0, message = "message.powerstats.intelligence.min.0")
    @Max(value = 100, message = "message.powerstats.intelligence.max.100")
    @NotNull(message = "message.powerstats.intelligence.mandatory")
    private Integer intelligence;

    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;


    public PowerStats() {

    }

    public PowerStats(Integer strength, Integer agility, Integer dexterity, Integer intelligence) {
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
    }




    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getAgility() {
        return agility;
    }

    public void setAgility(Integer agility) {
        this.agility = agility;
    }

    public Integer getDexterity() {
        return dexterity;
    }

    public void setDexterity(Integer dexterity) {
        this.dexterity = dexterity;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
    public Instant getUpdatedAt() {
        return updatedAt;
    }



    @Override
    public String toString() {
        return "PowerStats{" +
                ", strength=" + strength +
                ", agility=" + agility +
                ", dexterity=" + dexterity +
                ", intelligence=" + intelligence +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }


}
