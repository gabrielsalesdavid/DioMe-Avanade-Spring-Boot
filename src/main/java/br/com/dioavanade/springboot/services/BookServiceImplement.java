package br.com.dioavanade.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslBundleProperties;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dioavanade.springboot.models.Book;
import br.com.dioavanade.springboot.repositories.IBookRepository;

@Service
@Transactional
public class BookServiceImplement implements IBookService {

    @Autowired
    private IBookRepository bookRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Book create(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Boolean delete(String id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    public List<Book> getByCriteria(String criteria, String search) {
        Query query = new Query();
        query.addCriteria(Criteria.where(criteria).regex(String.format(".*%$*.", search), "i"));
        List<Book> books = mongoTemplate.find(query, Book.class);
        return books;
    }

    public Book getById(String id) {
        Optional<Book> book = bookRepository.findById(id);

        if (book.isPresent()) {
            return book.get();
        }

        return null;
    }

    public Boolean update(Book book) {
        Optional<Book> bookDb = bookRepository.findById(book.getId());

        if (bookDb.isPresent()) {
            bookRepository.save(book);
            return true;
        }

        return false;
    }
}
