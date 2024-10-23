package br.com.gubee.interview.core.hero.application.service.update_hero;

import br.com.gubee.interview.core.hero.application.port.in.UpdateHeroUseCase;
import br.com.gubee.interview.core.hero.application.port.out.LoadHeroPort;
import br.com.gubee.interview.core.hero.exception.ObjectNotFoundException;
import br.com.gubee.interview.domain.Hero;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Optional;

@Service
public class UpdateHeroService implements UpdateHeroUseCase {

    private final LoadHeroPort heroRepository;

    public UpdateHeroService(LoadHeroPort heroRepository) {
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
