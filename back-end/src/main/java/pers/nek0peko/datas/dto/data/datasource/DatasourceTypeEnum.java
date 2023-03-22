package pers.nek0peko.datas.dto.data.datasource;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持的数据源类型
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Getter
@AllArgsConstructor
public enum DatasourceTypeEnum {

    /**
     * 支持的数据源类型名称
     */
    MYSQL("MySQL", false),
    ;

    private final String type;

    private final boolean supportSchema;

    public static boolean isSupportedType(String type) {
        for (final DatasourceTypeEnum supportedType : DatasourceTypeEnum.values()) {
            if (supportedType.type.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> listType() {
        final List<String> types = new ArrayList<>();
        for (final DatasourceTypeEnum supportedType : DatasourceTypeEnum.values()) {
            types.add(supportedType.getType());
        }
        return types;
    }

}
