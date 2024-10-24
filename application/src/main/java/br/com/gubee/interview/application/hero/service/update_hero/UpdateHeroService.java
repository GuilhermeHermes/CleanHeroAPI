package br.com.gubee.interview.application.hero.service.update_hero;

import br.com.gubee.interview.application.hero.useCases.UpdateHeroUseCase;
import br.com.gubee.interview.application.hero.exception.ObjectNotFoundException;
import br.com.gubee.interview.domain.model.Hero;
import br.com.gubee.interview.domain.repositories.HeroPersistence;
import org.springframework.stereotype.Service;

import java.util.Optional;

public class UpdateHeroService implements UpdateHeroUseCase {

    private final HeroPersistence heroRepository;

    public UpdateHeroService(HeroPersistence heroRepository) {
        this.heroRepository = heroRepository;
    }



    public Hero execute(UpdateHeroInput input) {
        return heroRepository.findById(input.getId())
                .map(hero -> {
                    Optional.ofNullable(input.getName()).ifPresent(hero::setName);
                    Optional.ofNullable(input.getRace()).ifPresent(hero::setRace);
                    Optional.ofNullable(input.getPowerStats()).ifPresent(hero::setPowerStats);
                    Optional.ofNullable(input.getEnabled()).ifPresent(hero::setEnabled);
                    return heroRepository.save(hero);
                })
                .orElseThrow(() -> new ObjectNotFoundException(input.getId()));
    }

}
