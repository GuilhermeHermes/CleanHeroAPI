package br.com.gubee.interview.application.hero.service.delete_hero;

import br.com.gubee.interview.application.hero.useCases.DeleteHeroUseCase;
import br.com.gubee.interview.domain.repositories.HeroPersistence;

public class DeleteHeroService implements DeleteHeroUseCase {

    private HeroPersistence heroRepository;

    public DeleteHeroService(HeroPersistence heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public void execute(String id) {
        heroRepository.deleteById(id);
    }
}
