package br.com.dioavanade.springboot.services;

import java.util.List;

import br.com.dioavanade.springboot.models.Book;

public interface IBookService {

    Book create(Book book);

    Boolean delete(String id);

    List<Book> getAll();

    List<Book> getByCriteria(String cirteria, String search);

    Book getById(String id);

    Boolean update(Book book);
}
