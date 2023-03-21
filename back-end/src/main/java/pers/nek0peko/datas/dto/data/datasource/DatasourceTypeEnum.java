package pers.nek0peko.datas.dto.data.datasource;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 支持的数据源类型
 *
 * @author nek0peko
 * @date 2022/12/13
 */
@Getter
public enum DatasourceTypeEnum {

    /**
     * 支持的数据源类型名称
     */
    MYSQL("MySQL", false),
    ;

    private final String type;

    private final boolean supportSchema;

    DatasourceTypeEnum(String type, boolean supportSchema) {
        this.type = type;
        this.supportSchema = supportSchema;
    }

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
