package pers.nek0peko.datas.dto.data.chart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pers.nek0peko.datas.common.constant.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支持的图表类型
 *
 * @author nek0peko
 * @date 2023/04/20
 */
@Getter
@AllArgsConstructor
public enum ChartTypeEnum {

    /**
     * 支持的图表类型名称
     */
    BAR("bar", "柱状图"),
    LINE("line", "折线图"),
    PIE("pie", "饼图"),
    ;

    private final String type;

    private final String name;

    public static boolean isSupportedType(List<String> types) {
        for (final String type : types) {
            if (!isSupportedType(type)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isSupportedType(String type) {
        for (final ChartTypeEnum supportedType : ChartTypeEnum.values()) {
            if (supportedType.type.equals(type)) {
                return true;
            }
        }
        return false;
    }

    public static List<Map<String, String>> listType() {
        final List<Map<String, String>> types = new ArrayList<>();
        for (final ChartTypeEnum supportedType : ChartTypeEnum.values()) {
            final Map<String, String> map = new HashMap<>(Constants.TWO);
            map.put("type", supportedType.getType());
            map.put("name", supportedType.getName());
            types.add(map);
        }
        return types;
    }

}
