package com.kodilla.webflux;

import reactor.core.publisher.Flux;

public class FirstFluxCheck {

    public static void main(String[] args) {
        Book b1 = new Book("title1", "author1", 2000);
        Book b2 = new Book("title2", "author2", 2001);
        Flux<Book> bookFlux = Flux.just(b1, b2);
        bookFlux = bookFlux.concatWith(Flux.error(new Exception("test exception")));
        bookFlux.subscribe(System.out::println, FirstFluxCheck::handleException);
    }

    private static void handleException(Throwable e) {
        System.out.println("there was an error: " + e);
    }
}
