package br.com.gubee.interview.application.hero.service;

import br.com.gubee.interview.application.hero.useCases.GetHeroesUseCase;
import br.com.gubee.interview.domain.model.Hero;
import br.com.gubee.interview.domain.repositories.HeroPersistence;
import org.springframework.stereotype.Service;

import java.util.List;

public class GetHeroesService implements GetHeroesUseCase {

    private final HeroPersistence heroRepository;

    public GetHeroesService(HeroPersistence heroRepository) {
        this.heroRepository = heroRepository;
    }

    public List<Hero> execute(){
        return heroRepository.findAll();
    }
}
