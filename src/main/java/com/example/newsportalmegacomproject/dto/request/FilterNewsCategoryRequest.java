package com.example.newsportalmegacomproject.dto.request;

import com.example.newsportalmegacomproject.enums.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FilterNewsCategoryRequest {

    private List<Category> categories;
}
