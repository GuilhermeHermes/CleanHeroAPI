package br.com.gubee.interview.core.utils;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HeroMapper {
    private static final ModelMapper mapper = new ModelMapper();

    public HeroDtoResponse mapToDtoResponse(Hero hero) {
        return mapper.map(hero, HeroDtoResponse.class);
    }

    public HeroDtoRequest mapToDtoRequest(Hero hero) {
        return mapper.map(hero, HeroDtoRequest.class);
    }

    public HeroDtoRequest mapToDtoRequest(HeroDtoResponse response) {
        return mapper.map(response, HeroDtoRequest.class);
    }

    public static HeroDtoResponse mapToDtoResponse(HeroDtoRequest request) {
        return mapper.map(request, HeroDtoResponse.class);
    }

    public Hero mapToHero(HeroDtoRequest request) {
        Hero hero = mapper.map(request, Hero.class);

        return hero;
    }

    public List<HeroDtoResponse> mapToDtoResponseList(List<Hero> heroes) {
        return heroes.stream()
                .map(this::mapToDtoResponse)
                .collect(Collectors.toList());
    }

    public List<Hero> mapToHeroList(List<HeroDtoRequest> requests) {
        return requests.stream()
                .map(this::mapToHero)
                .collect(Collectors.toList());
    }

    public List<HeroDtoRequest> mapToDtoRequestList(List<Hero> heroes) {
        return heroes.stream()
                .map(this::mapToDtoRequest)
                .collect(Collectors.toList());
    }

    public Hero updateHero(Hero hero, HeroDtoRequest request) {
        hero.setName(request.getName());
        hero.setRace(request.getRace());
        hero.setPowerStats(request.getPowerStats());
        hero.setEnabled(request.isEnabled());
        return hero;
    }

}
