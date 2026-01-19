package org.example.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponseDto> getCategories() {
        return categoryService.getActiveCategories();
    }

    @PostMapping
    public CategoryResponseDto createCategory(@RequestBody CategoryResponseDto dto) {
        return categoryService.createCategory(dto);
    }

    @PatchMapping("/{id}")
    public CategoryResponseDto updateCategory(@PathVariable Long id, @RequestBody CategoryResponseDto dto) {
        return categoryService.updateCategory(id, dto);
    }
}
