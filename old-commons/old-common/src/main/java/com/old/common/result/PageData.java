package com.old.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageData<E> {
    private Long pageNum;
    private Long pageSize;
    private Long total;
    private Collection<E> rows;
}
