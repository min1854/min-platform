package com.old.generator;

import cn.hutool.setting.yaml.YamlUtil;
import com.old.common.util.JsonUtil;
import com.old.generator.domain.bo.ColumnsBo;
import com.old.generator.domain.bo.GenerateTablesBo;
import com.old.generator.domain.bo.GeneratorBo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeneratorCode extends SpringBootTests {

    @Test
    public void generator() throws IOException {
        try (
                InputStreamReader reader = new InputStreamReader(this.getClass().getClassLoader()
                        .getResourceAsStream("gen.yml"));
                InputStreamReader tablesReader = new InputStreamReader(this.getClass().getClassLoader()
                        .getResourceAsStream("tables.yml"));
        ) {
            var bo = YamlUtil.load(reader).toBean(GeneratorBo.class);
            var tablesBo = YamlUtil.load(tablesReader).toBean(GenerateTablesBo.class);
            System.out.println("读取出配置 GeneratorBo：" + JsonUtil.toJson(bo));
            System.out.println("读取出配置 GenerateTablesBo：" + JsonUtil.toJson(tablesBo));

            var genTables = tablesBo.getTables()
                    .stream()
                    .map(genTableBo -> {
                        Map<String, ColumnsBo> fieldMap = genTableBo.getFields();
                        List<Map<String, ColumnsBo>> fields = genTableBo.getYmlFields();
                        if (fields == null) {
                            return genTableBo;
                        }
                        for (Map<String, ColumnsBo> fieldBoMap : fields) {
                            fieldMap.putAll(fieldBoMap);
                        }
                        return genTableBo;
                    }).collect(Collectors.toSet());

            bo.setTables(genTables);


            System.out.println("转换后的配置：" + JsonUtil.toJson(bo));
            generatorService.generator(bo);
        }
    }

}
