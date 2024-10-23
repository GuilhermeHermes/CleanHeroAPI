package br.com.gubee.interview.core.hero.application.service.delete_hero;

import br.com.gubee.interview.core.hero.application.port.in.DeleteHeroUseCase;
import br.com.gubee.interview.core.hero.application.port.out.LoadHeroPort;
import org.springframework.stereotype.Service;

@Service
public class DeleteHeroService implements DeleteHeroUseCase {

    private LoadHeroPort heroRepository;

    public DeleteHeroService(LoadHeroPort heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public void execute(String id) {
        heroRepository.deleteById(id);
    }
}
