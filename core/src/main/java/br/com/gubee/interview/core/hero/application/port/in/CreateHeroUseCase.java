package br.com.gubee.interview.core.hero.application.port.in;

import br.com.gubee.interview.core.hero.application.service.create_hero.CreateHeroInput;

import java.util.Map;

public interface CreateHeroUseCase {

    public Map<String, Object> execute(CreateHeroInput input);

}
