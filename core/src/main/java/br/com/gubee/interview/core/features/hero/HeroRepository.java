package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface HeroRepository extends MongoRepository<Hero, String> {



    List<Hero> findByNameContainingIgnoreCase(String name);

    @Override
    List<Hero> findAll();

    List<Hero> findAllByIdIn(List<String> ids);


    void deleteById(String id);

    void deleteAllByNameIn(List<String> names);
}
