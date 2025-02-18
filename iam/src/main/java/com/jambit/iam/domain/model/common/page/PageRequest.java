package com.jambit.iam.domain.model.common.page;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jambit.iam.domain.model.common.validate.ValidatableModel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import java.util.Objects;

/**
 * Created by Tigran Melkonyan
 * Date: 2/18/25
 * Time: 8:06 PM
 */
@Getter
@NoArgsConstructor
public class PageRequest implements ValidatableModel {

    @Min(0)
    private int page;

    @Min(1)
    private int size;

    @JsonCreator
    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
    }

    @JsonIgnore
    public static PageRequest getPageRequestOrDefault(final PageRequest model) {
        if (Objects.isNull(model) || (model.getSize() < 1 || model.getPage() < 0)) {
            return new PageRequest(0, 50);
        }
        return new PageRequest(model.getPage() * model.getSize(), model.getSize());
    }
}
