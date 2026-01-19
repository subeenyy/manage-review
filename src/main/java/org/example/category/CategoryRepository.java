package org.example.category;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByIsActiveInOrderByDisplayOrderAsc(java.util.Collection<String> isActives);

    default List<Category> findAllActive() {
        return findAllByIsActiveInOrderByDisplayOrderAsc(java.util.List.of("1"));
    }
}
