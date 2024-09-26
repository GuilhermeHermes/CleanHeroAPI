package br.com.gubee.interview.model.dtos;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class HeroDtoRequest {



    private String id;

    @NotBlank(message = "message.name.mandatory")
    @Length(min = 1, max = 255, message = "message.name.length.min.1.max.255")
    private String name;

    @NotNull(message = "message.race.mandatory")
    private Race race;

    @NotNull(message = "message.enabled.mandatory")
    @Valid
    private PowerStats powerStats;

    @NotNull(message = "message.enabled.mandatory")
    private boolean enabled;

    public HeroDtoRequest(){}

    public HeroDtoRequest(String name, Race race, PowerStats powerStats, boolean enabled) {
        this.name = name;
        this.race = race;
        this.powerStats = powerStats;
        this.enabled = enabled;
    }

    public @NotBlank(message = "message.name.mandatory") @Length(min = 1, max = 255, message = "message.name.length.min.1.max.255") String getName() {
        return name;
    }

    public void setName(@NotBlank(message = "message.name.mandatory") @Length(min = 1, max = 255, message = "message.name.length.min.1.max.255") String name) {
        this.name = name;
    }

    public @NotNull(message = "message.race.mandatory") Race getRace() {
        return race;
    }

    public void setRace(@NotNull(message = "message.race.mandatory") Race race) {
        this.race = race;
    }

    public @NotNull(message = "message.enabled.mandatory") PowerStats getPowerStats() {
        return powerStats;
    }

    public void setPowerStats(@NotNull(message = "message.enabled.mandatory") PowerStats powerStats) {
        this.powerStats = powerStats;
    }

    @NotNull(message = "message.enabled.mandatory")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(@NotNull(message = "message.enabled.mandatory") boolean enabled) {
        this.enabled = enabled;
    }

}
