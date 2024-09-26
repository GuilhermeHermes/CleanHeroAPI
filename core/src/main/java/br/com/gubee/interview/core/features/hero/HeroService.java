package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface HeroService {

    HeroDtoResponse save(HeroDtoRequest hero);

    List<HeroDtoResponse> saveAll(List<HeroDtoRequest> heroes);

    List<HeroDtoResponse> findAll();

    List<HeroDtoResponse> findAll(List<String> ids);

    HeroDtoResponse findById(String id);

    long count();

    void delete(String id);

    void delete(List<String> ids);

    void deleteAll();

    HeroDtoResponse update(HeroDtoRequest heroDtoRequest);

    List<Hero> update(List<HeroDtoRequest> heroes);
    
    Map<String, Object> compare(String id1, String id2);

    List<HeroDtoResponse> findByName(String name);
}
