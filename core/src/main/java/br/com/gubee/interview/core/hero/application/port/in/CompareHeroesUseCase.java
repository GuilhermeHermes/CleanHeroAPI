package br.com.gubee.interview.core.hero.application.port.in;

import br.com.gubee.interview.core.hero.application.service.compare_heroes.CompareHeroesInput;

import java.util.Map;

public interface CompareHeroesUseCase {

    Map<String, Object> execute(CompareHeroesInput input);
}
