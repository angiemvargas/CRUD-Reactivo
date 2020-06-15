package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/person")
public class PersonaController {

    @Autowired
    private PersonService personService;

    @PostMapping
    private Mono<Void> post(@RequestBody Mono<Person> personMono) {
        return personService.insert(personMono);
    }

    @GetMapping("/{id}")
    private Mono<Person> getPerson(@PathVariable("id") String id) {
        return personService.findById(id);
    }

    @PutMapping
    private Mono<Void> put(@RequestBody Mono<Person> personMono) {
        return personService.updateById(personMono);
    }

    @DeleteMapping("/{id}")
    private Mono<Void> delete(@PathVariable("id") String id) {
        return personService.deleteById(id);
    }

    @GetMapping
    private Flux<Person> list() {
        return personService.listAll();
    }
}
