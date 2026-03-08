package org.example.spring26.books;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends ListCrudRepository<Book, Long> {

    @Modifying
    @Query("UPDATE Book b SET b.title = :newTitle WHERE b.id IN :ids")
    int updateTitles(@Param("ids") List<Long> ids,
                     @Param("newTitle") String newTitle);
}
