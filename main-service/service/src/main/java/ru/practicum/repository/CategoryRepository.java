package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.model.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
