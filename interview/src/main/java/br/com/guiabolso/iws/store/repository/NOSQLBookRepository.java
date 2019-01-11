package br.com.guiabolso.iws.store.repository;

import br.com.guiabolso.iws.store.model.entity.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NOSQLBookRepository extends MongoRepository<Book,Long> {
}
