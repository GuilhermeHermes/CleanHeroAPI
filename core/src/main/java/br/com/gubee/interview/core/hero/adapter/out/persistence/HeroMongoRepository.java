package br.com.gubee.interview.core.hero.adapter.out.persistence;



import br.com.gubee.interview.domain.Hero;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

interface HeroMongoRepository extends MongoRepository<Hero, ObjectId> {

    List<Hero> findByNameContainingIgnoreCase(String name);


    List<Hero> findAllByIdIn(List<ObjectId> ids);

    Hero findById(String id);

    void deleteById(String id);
}
