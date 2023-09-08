package com.k1rard.inventory.inventory.app.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryResponseRest extends ResponseRest {
    private CategoryResponse categoryResponse = new CategoryResponse();
}
