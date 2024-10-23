package br.com.gubee.interview.core.hero.adapter.out.persistence;

import br.com.gubee.interview.core.hero.application.port.out.LoadHeroPort;
import br.com.gubee.interview.domain.Hero;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
 class HeroPersistenceAdapter implements LoadHeroPort {

    private final HeroMongoRepository heroRepository;

    public HeroPersistenceAdapter(HeroMongoRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    @Override
    public Hero save(Hero hero) {
        System.out.println("HeroPersistenceAdapter.save");
        System.out.println(hero);
      return heroRepository.save(hero);
    }

    @Override
    public Optional<Hero> findById(String id) {
        return Optional.ofNullable(heroRepository.findById(id));
    }

    @Override
    public List<Hero> findByName(String name) {
        return heroRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Hero update(Hero hero) {
        return heroRepository.save(hero);
    }

    @Override
    public void delete(String id) {
        heroRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
    heroRepository.deleteAll();
    }

    @Override
    public void deleteById(String id) {
        heroRepository.deleteById(id);
    }

    @Override
    public List<Hero> findAll() {
        return heroRepository.findAll();
    }
}
