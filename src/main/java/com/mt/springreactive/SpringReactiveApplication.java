package com.mt.springreactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.annotation.Id;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.Executors;

@SpringBootApplication
public class SpringReactiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringReactiveApplication.class, args);
    }

}

@RestController
@RequiredArgsConstructor
class CustomerRestController {

    private final CustomerRepository customerRepository;

    @GetMapping("/customers")
    public Flux<Customer> customers() {
        return customerRepository.findAll();
    }
}

@Component
@RequiredArgsConstructor
@Slf4j
class InitializeData implements CommandLineRunner {

    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        Flux<String> names = Flux.just("Maziar", "Jimmy", "James", "Tom", "Olga", "Violetta");

        Flux<Customer> customers = names
                .map(name -> new Customer(null, name))
                .flatMap(this.customerRepository::save);

        this.customerRepository
                .deleteAll()
                .thenMany(customers)
                .subscribeOn(Schedulers.fromExecutor(Executors.newFixedThreadPool(10)))
                .subscribe(System.out::println);
    }
}

record Customer(@Id Long id, String name) {

}

interface CustomerRepository extends ReactiveCrudRepository<Customer, Long> {

}