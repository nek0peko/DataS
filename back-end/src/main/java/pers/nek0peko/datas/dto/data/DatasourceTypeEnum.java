package pers.nek0peko.datas.dto.data;

import lombok.Getter;

/**
 * 支持的数据源类型
 *
 * @author nek0peko
 * @date 2022/11/17
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

}
