package com.jambit.iam.domain.model.common.search;

import com.jambit.iam.domain.entity.common.base.ModelStatus;
import com.jambit.iam.domain.model.common.page.PageRequest;
import com.jambit.iam.domain.model.common.sort.SortOption;
import com.jambit.iam.domain.model.common.validate.ValidatableModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 7:50 PM
 */
@Getter
@Setter
public class SearchProperties implements ValidatableModel {

    @Size(max = 100, message = "Search text cannot be longer than 100 characters")
    private String searchText;

    @NotNull(message = "status required")
    private ModelStatus status;

    private SortOption sort;

    @Valid
    private PageRequest pageRequest;

}
