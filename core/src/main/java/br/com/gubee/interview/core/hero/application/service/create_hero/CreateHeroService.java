package br.com.gubee.interview.core.hero.application.service.create_hero;

import br.com.gubee.interview.core.hero.application.port.in.CreateHeroUseCase;
import br.com.gubee.interview.core.hero.application.port.out.LoadHeroPort;
import br.com.gubee.interview.domain.Hero;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
 class CreateHeroService implements CreateHeroUseCase {

    private final LoadHeroPort heroRepository;

    public CreateHeroService(LoadHeroPort heroRepository) {
        this.heroRepository = heroRepository;
        heroRepository.deleteAll();
    }

    public  Map<String, Object> execute(CreateHeroInput input) {
        Hero hero = heroRepository.save(input.toHero());
        Map<String, Object> response = new HashMap<>();
        response.put("id", hero.getId().toString());
        response.put("timestamp", Instant.now().getEpochSecond());
        response.put("date", new Date());
        return response;

    }
}
