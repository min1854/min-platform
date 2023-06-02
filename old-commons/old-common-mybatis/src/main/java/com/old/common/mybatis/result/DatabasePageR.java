package com.old.common.mybatis.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.old.common.result.PageData;
import com.old.common.result.R;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabasePageR {


    public static <T> R<PageData<T>> of(IPage<T> page) {
        return R.ok(new PageData<>(page.getCurrent(),
                page.getSize(), page.getTotal(), page.getRecords()));
    }

    public static <T, R> com.old.common.result.R<PageData<R>> of(IPage<T> page, Function<T, R> mapper) {
        List<R> list = new ArrayList<>((int) page.getSize());
        for (T record : page.getRecords()) {
            list.add(mapper.apply(record));
        }
        PageData<R> pageData = new PageData<>(page.getCurrent(),
                page.getSize(), page.getTotal(), list);
        return com.old.common.result.R.ok(pageData);
    }

    public static <T> R<PageData<T>> of(IPage<T> page, Consumer<T> consumer) {
        page.getRecords().forEach(consumer);
        return R.ok(new PageData<>(page.getCurrent(),
                page.getSize(), page.getTotal(), page.getRecords()));
    }


    public static <T> PageData<T> create(IPage<T> page) {
        return new PageData<>(page.getCurrent(),
                page.getSize(), page.getTotal(), page.getRecords());
    }


    public static <T, R> PageData<R> create(IPage<T> page, Function<T, R> mapper) {
        List<R> list = new ArrayList<>((int) page.getSize());
        for (T record : page.getRecords()) {
            list.add(mapper.apply(record));
        }
        return new PageData<>(page.getCurrent(),
                page.getSize(), page.getTotal(), list);
    }

    public static <T> PageData<T> create(IPage<T> page, Consumer<T> consumer) {
        page.getRecords().forEach(consumer);
        return new PageData<>(page.getCurrent(),
                page.getSize(), page.getTotal(), page.getRecords());
    }


}
