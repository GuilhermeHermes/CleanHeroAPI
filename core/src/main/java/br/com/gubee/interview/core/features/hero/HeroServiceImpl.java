package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.hero.exception.ObjectNotFoundException;
import br.com.gubee.interview.core.utils.HeroMapper;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class HeroServiceImpl implements HeroService {

    private final HeroRepository heroRepository;

    private final HeroMapper heroMapper;

    public HeroServiceImpl(HeroRepository heroRepository, HeroMapper heroMapper) {
        this.heroRepository = heroRepository;
        this.heroMapper = heroMapper;
    }


    public HeroDtoResponse save(HeroDtoRequest heroDtoRequest){
        return heroMapper.mapToDtoResponse(heroRepository.save(heroMapper.mapToHero(heroDtoRequest)));
    }

    @Override
    public List<HeroDtoResponse> saveAll(List<HeroDtoRequest> heroes) {
        return heroes.stream().map(heroMapper::mapToHero).peek(heroRepository::save).map(heroMapper::mapToDtoResponse).toList();
    }

    @Override
    public List<HeroDtoResponse> findAll() {
        return heroRepository.findAll().stream().map(heroMapper::mapToDtoResponse).toList();
    }

    @Override
    public List<HeroDtoResponse> findAll(List<String> ids) {
        return heroRepository.findAllById(ids).stream().map(heroMapper::mapToDtoResponse).toList();
    }

    @Override
    public HeroDtoResponse findById(String id) {
        Optional<Hero> hero = heroRepository.findById(id);
        if (hero.isEmpty()) {
            throw new ObjectNotFoundException(id);
        }
        return heroMapper.mapToDtoResponse(hero.get());
    }

    @Override
    public long count() {
        return heroRepository.count();
    }

    @Override
    public void delete(String id) {
         heroRepository.deleteById(id);
    }

    @Override
    public void delete(List<String> ids) {
         heroRepository.deleteAllByIdIn(ids);
    }

    @Override
    public void deleteAll() {
         heroRepository.deleteAll();
    }

    @Override
    public HeroDtoResponse update(HeroDtoRequest heroDtoRequest) {
        Hero hero = heroRepository.findById(heroDtoRequest.getId())
                .orElseThrow(() -> new ObjectNotFoundException(heroDtoRequest.getId()));
        heroMapper.updateHero(hero, heroDtoRequest);
        return heroMapper.mapToDtoResponse(heroRepository.save(hero));
    }

    @Override
    public List<Hero> update(List<HeroDtoRequest> personEntities) {
        return heroRepository.saveAll(personEntities.stream().map(heroMapper::mapToHero).toList());
    }



    public Map<String, Object> compare(String id1, String id2) {
        HeroDtoResponse hero1 = findById(id1);
        HeroDtoResponse hero2 = findById(id2);
        Map<String, Object> result = new HashMap<>();

        result.put("id1", hero1.getId());
        result.put("id2", hero2.getId());

        PowerStats powerStats1 = hero1.getPowerStats();
        PowerStats powerStats2 = hero2.getPowerStats();

        int strengthDiff = powerStats1.getStrength() - powerStats2.getStrength();
        int agilityDiff = powerStats1.getAgility() - powerStats2.getAgility();
        int dexterityDiff = powerStats1.getDexterity() - powerStats2.getDexterity();
        int intelligenceDiff = powerStats1.getIntelligence() - powerStats2.getIntelligence();

        result.put("strengthDiff", strengthDiff);
        result.put("agilityDiff", agilityDiff);
        result.put("dexterityDiff", dexterityDiff);
        result.put("intelligenceDiff", intelligenceDiff);

        return result;
    }

    @Override
    public List<HeroDtoResponse> findByName(String name) {
        return heroRepository.findByNameContainingIgnoreCase(name).stream().map(heroMapper::mapToDtoResponse).toList();
    }


}
