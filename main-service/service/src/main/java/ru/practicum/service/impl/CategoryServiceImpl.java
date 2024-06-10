package ru.practicum.service.impl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.exception.NotFoundException;
import ru.practicum.model.dto.CategoryDto;
import ru.practicum.model.entity.Category;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.model.ApplicationConstant.CATEGORY_NAME;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    @Override
    public CategoryDto save(CategoryDto category) {
        Category categoryEntity = categoryRepository.save(modelMapper.map(category, Category.class));
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public CategoryDto update(Long catId, CategoryDto category) {
        categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, catId.toString()));
        category.setId(catId);
        Category categoryEntity = categoryRepository.save(modelMapper.map(category, Category.class));
        return modelMapper.map(categoryEntity, CategoryDto.class);
    }

    @Override
    public void delete(Long catId) {
        categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, catId.toString()));
        categoryRepository.deleteById(catId);
    }

    @Override
    public List<CategoryDto> findAll(Integer from, Integer size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).stream()
                .map(it -> modelMapper.map(it, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto findById(Long catId) {
        Category category = categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NAME, catId.toString()));
        return modelMapper.map(category, CategoryDto.class);
    }
}
