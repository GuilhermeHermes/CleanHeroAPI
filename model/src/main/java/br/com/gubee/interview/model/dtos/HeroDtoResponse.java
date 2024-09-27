package br.com.gubee.interview.model.dtos;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class HeroDtoResponse {

    private String id;

    private String name;

    private Race race;

    private PowerStats powerStats;

    public HeroDtoResponse(){

    }

    public HeroDtoResponse(String id, String name, Race race, PowerStats powerStats) {
        this.id = id;
        this.name = name;
        this.race = race;
        this.powerStats = powerStats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    @Override
    public String toString() {
        return "HeroDtoResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", race=" + race +
                ", powerStats=" + powerStats +
                '}';
    }
}
