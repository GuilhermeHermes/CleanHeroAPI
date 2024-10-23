package br.com.gubee.interview.core.hero.application.port.in;

import br.com.gubee.interview.core.hero.application.service.update_hero.UpdateHeroInput;
import br.com.gubee.interview.domain.Hero;

public interface UpdateHeroUseCase {

    public Hero execute(UpdateHeroInput input);

}
