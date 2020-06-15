package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

@Service
public class PersonService {

    private final BiFunction<PersonRepository, Person, Mono<Person>> validateBeforeInsert
            = (repo, person) -> repo.findByName(person.getName());

    private final BiFunction<PersonRepository, Person, Mono<Person>> validateBeforeUpdate
            = (repo, person) -> repo.findById(person.getId());

    @Autowired
    private PersonRepository personRepository;

    public Flux<Person> listAll() {
        return personRepository.findAll();
    }

    public Mono<Void> deleteById(String id) {
        return personRepository.deleteById(id);
    }

    public Mono<Void> updateById(Mono<Person> personMono){
        return personMono
                .flatMap(person -> validateBeforeUpdate.apply(personRepository, person))
                .switchIfEmpty(Mono.defer(() -> personMono.doOnNext(personRepository::save)))
                .then();
    }

    public Mono<Void> insert(Mono<Person> personMono) {
        return personMono
                .flatMap(person -> validateBeforeInsert.apply(personRepository, person))
                .switchIfEmpty(Mono.defer(() -> personMono.doOnNext(personRepository::save)))
                .then();
    }

    public Mono<Person> findById(String id) {
        return personRepository.findById(id);
    }

}
