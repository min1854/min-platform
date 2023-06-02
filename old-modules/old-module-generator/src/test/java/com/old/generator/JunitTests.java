package com.old.generator;

import cn.hutool.core.lang.Dict;
import cn.hutool.setting.yaml.YamlUtil;
import com.old.generator.domain.GenConfigTestBo;
import com.old.generator.domain.bo.GenerateTablesBo;
import com.old.generator.domain.bo.GeneratorBo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;

public class JunitTests {
    @Test
    public void test() {
        System.out.println("123");
    }

    @Test
    public void toTestBo() throws IOException {
        String path = "gen-example.yml";
        System.out.println(toBo(path, GenConfigTestBo.class));
        System.out.println(toBo(path, GeneratorBo.class));
        System.out.println(toBo("tables.yml", GenerateTablesBo.class));
    }


    private <T> T toBo(String path, Class<T> boClass) throws IOException {
        try (
                InputStreamReader reader = new InputStreamReader(this.getClass().getClassLoader()
                        .getResourceAsStream(path));
        ) {
            Dict load = YamlUtil.load(reader);
            System.out.println(load.toString());

            return load.toBean(boClass);
        }
    }

}
