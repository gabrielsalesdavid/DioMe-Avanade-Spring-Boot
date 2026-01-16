package br.com.dioavanade.springboot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.dioavanade.springboot.models.Book;
import br.com.dioavanade.springboot.services.BookServiceImplement;

@RestController
@RequestMapping("/api")
public class BookController {

    @Autowired
    private BookServiceImplement bookServiceImplement;

    @GetMapping("/Books")
    public ResponseEntity<List<Book>> getAll() {
        return ResponseEntity.ok().body(bookServiceImplement.getAll());
    }

    @RequestMapping(path = "/Books/{crietria}/{search}", method = RequestMethod.GET)
    public ResponseEntity<List<Book>> getByCriteria(@PathVariable String criteria, @PathVariable String search) {
        return ResponseEntity.ok().body(bookServiceImplement.getByCriteria(criteria, search));
    }

    @GetMapping("/Book/{id}")
    public ResponseEntity<Book> getById(@PathVariable String id) {
        return ResponseEntity.ok().body(bookServiceImplement.getById(id));
    }

    @PostMapping("/Books")
    public ResponseEntity<Book> create(@RequestBody Book book) {
        return ResponseEntity.ok().body(bookServiceImplement.create(book));
    }

    @PutMapping("/Books")
    public ResponseEntity<Boolean> update(@RequestBody Book book) {
        return ResponseEntity.ok().body(bookServiceImplement.update(book));
    }

    @DeleteMapping("/Books")
    public ResponseEntity<Boolean> delete(@PathVariable String id) {
        return ResponseEntity.ok().body(bookServiceImplement.delete(id));
    }
}
