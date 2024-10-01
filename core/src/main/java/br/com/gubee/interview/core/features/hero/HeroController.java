package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.dtos.HeroDtoRequest;
import br.com.gubee.interview.model.dtos.HeroDtoResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/heroes")
@Validated
public class HeroController {

    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }


    @GetMapping
    public ResponseEntity<List<HeroDtoResponse>> listHeroes() {
        List<HeroDtoResponse> heroes = heroService.findAll();
        return ResponseEntity.ok().body(heroes);
    }



    @GetMapping("/{id}")
    public ResponseEntity<HeroDtoResponse> findById(@PathVariable String id) {
        HeroDtoResponse hero = heroService.findById(id);
        return ResponseEntity.ok().body(hero);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<HeroDtoResponse>> findByName(@PathVariable String name) {
        List<HeroDtoResponse> heroes = heroService.findByName(name);
        return ResponseEntity.ok().body(heroes);
    }



    @PostMapping
    public ResponseEntity<HeroDtoResponse> insert(@RequestBody @Valid HeroDtoRequest hero) {
        System.out.println("HeroDTO: " + hero);
        HeroDtoResponse savedHero = heroService.save(hero);
        URI uri = URI.create("/api/v1/heroes/" + savedHero.getId());
        return ResponseEntity.created(uri).body(savedHero);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        heroService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<HeroDtoResponse> update( @RequestBody @Valid HeroDtoRequest hero) {
        HeroDtoResponse updatedHero = heroService.update(hero);
        return ResponseEntity.ok().body(updatedHero);
    }

    @GetMapping("/compare/{id1}/{id2}")
    public ResponseEntity<Map<String, Object>> compare(@PathVariable String id1, @PathVariable String id2) {
        Map<String, Object> result = heroService.compare(id1, id2);
        return ResponseEntity.ok().body(result);
    }


}
