package br.com.dioavanade.springboot.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.dioavanade.springboot.models.Book;

public interface IBookRepository extends MongoRepository<Book, String> {

}
