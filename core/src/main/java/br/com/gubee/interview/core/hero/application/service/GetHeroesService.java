package br.com.gubee.interview.core.hero.application.service;

import br.com.gubee.interview.core.hero.application.port.in.GetHeroesUseCase;
import br.com.gubee.interview.core.hero.application.port.out.LoadHeroPort;
import br.com.gubee.interview.domain.Hero;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class GetHeroesService implements GetHeroesUseCase {

    private final LoadHeroPort heroRepository;

    public GetHeroesService(LoadHeroPort heroRepository) {
        this.heroRepository = heroRepository;
    }

    public List<Hero> execute(){
        return heroRepository.findAll();
    }
}
