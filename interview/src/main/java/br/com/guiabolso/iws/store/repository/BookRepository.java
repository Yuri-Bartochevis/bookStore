package br.com.guiabolso.iws.store.repository;

import br.com.guiabolso.iws.store.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}