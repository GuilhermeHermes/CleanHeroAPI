package br.com.gubee.interview.core.hero.application.port.in;

import br.com.gubee.interview.domain.Hero;

import java.util.List;

public interface GetHeroesUseCase {

    public List<Hero> execute();
}
