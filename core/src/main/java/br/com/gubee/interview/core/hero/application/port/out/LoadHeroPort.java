package br.com.gubee.interview.core.hero.application.port.out;

import br.com.gubee.interview.domain.Hero;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadHeroPort {

    Hero save(Hero hero);

    Optional<Hero> findById(String id);

    List<Hero> findByName(String name);

    Hero update(Hero hero);

    void delete(String id);

    void deleteAll();

    void deleteById(String id);

    List<Hero> findAll();
}
