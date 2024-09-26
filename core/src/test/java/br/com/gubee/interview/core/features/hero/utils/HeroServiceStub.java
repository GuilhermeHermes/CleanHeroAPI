package br.com.gubee.interview.core.features.hero.utils;

import br.com.gubee.interview.core.features.hero.HeroService;
import br.com.gubee.interview.core.configuration.exception.ObjectNotFoundException;
import br.com.gubee.interview.core.utils.HeroMapper;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import br.com.gubee.interview.model.enums.Race;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class HeroServiceStub implements HeroService {

    HeroMapper heroMapper;

    private final Map<String, HeroDtoResponse> heroes = new HashMap<>();

    public HeroServiceStub() {
        heroes.put("1", new HeroDtoResponse("1", "Hero One", Race.HUMAN, new PowerStats(50, 60, 70, 80)));
        heroes.put("2", new HeroDtoResponse("2", "Hero Two", Race.ALIEN, new PowerStats(55, 65, 75, 85)));
    }

    @Override
    public HeroDtoResponse save(HeroDtoRequest hero) {
        return null;
    }

    @Override
    public List<HeroDtoResponse> saveAll(List<HeroDtoRequest> heroes) {
        return List.of();
    }

    @Override
    public List<HeroDtoResponse> findAll() {

        HeroDtoResponse hero1 = new HeroDtoResponse("1", "Hero One", Race.HUMAN, new PowerStats(50, 60, 70, 80));
        HeroDtoResponse hero2 = new HeroDtoResponse("2", "Hero Two", Race.ALIEN, new PowerStats(55, 65, 75, 85));
        return List.of(hero1, hero2);
    }

    @Override
    public List<HeroDtoResponse> findAll(List<String> ids) {
        return List.of();
    }



    @Override
    public long count() {
        return 0;
    }

    public HeroDtoResponse findById(String id) {
        if (!heroes.containsKey(id)) {
            throw new ObjectNotFoundException(id);
        }
        return heroes.get(id);
    }

    @Override
    public List<HeroDtoResponse> findByName(String name) {
        return heroes.values().stream()
                .filter(hero -> hero.getName().toLowerCase().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> compare(String id1, String id2) {
        return Map.of();
    }

    @Override
    public void delete(String id) {
    }

    @Override
    public void delete(List<String> ids) {
    }

    @Override
    public void deleteAll() {
    }

    @Override
    public HeroDtoResponse update(HeroDtoRequest heroDtoRequest) {
        return null;
    }

    @Override
    public List<Hero> update(List<HeroDtoRequest> heroes) {
        return List.of();
    }


}
