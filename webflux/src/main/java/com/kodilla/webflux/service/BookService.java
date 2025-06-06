package com.kodilla.webflux.service;

import com.kodilla.webflux.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@AllArgsConstructor
public class BookService {

    public Flux<Book> getAllBooks() {
        Book b1 = new Book("title1", "author1", 2001);
        Book b2 = new Book("title2", "author2", 2002);
        Book b3 = new Book("title3", "author3", 2003);

        return Flux
                .just(b1, b2, b3)
                .log();
    }
}
