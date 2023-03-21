package pers.nek0peko.datas.dto.data.datasource;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pers.nek0peko.datas.dto.BaseDTO;

/**
 * DatasourceResultHolder
 *
 * @author nek0peko
 * @date 2023/03/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DatasourceResultHolder extends BaseDTO {

    private boolean success;

    private Object data;

    public static DatasourceResultHolder buildEmptyFailure() {
        final DatasourceResultHolder resultHolder = new DatasourceResultHolder();
        resultHolder.setSuccess(false);
        return resultHolder;
    }

    public static DatasourceResultHolder buildEmptySuccess() {
        final DatasourceResultHolder resultHolder = new DatasourceResultHolder();
        resultHolder.setSuccess(true);
        return resultHolder;
    }

    public static DatasourceResultHolder buildSuccessWithData(Object data) {
        final DatasourceResultHolder resultHolder = new DatasourceResultHolder();
        resultHolder.setData(data);
        resultHolder.setSuccess(true);
        return resultHolder;
    }

}
