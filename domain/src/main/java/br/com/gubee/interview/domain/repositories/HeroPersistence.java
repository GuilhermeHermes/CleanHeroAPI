package br.com.gubee.interview.domain.repositories;

import br.com.gubee.interview.domain.model.Hero;

import java.util.List;
import java.util.Optional;

public interface HeroPersistence {

    Hero save(Hero hero);

    Optional<Hero> findById(String id);

    List<Hero> findByName(String name);

    Hero update(Hero hero);

    void delete(String id);

    void deleteAll();

    void deleteById(String id);

    List<Hero> findAll();
}
