package pers.nek0peko.datas.domain.convertor;

import java.util.List;

/**
 * Convertor
 *
 * @author nek0peko
 * @date 2022/12/12
 */
public interface Convertor<E, D> {

    /**
     * 将DTO转化为DO
     * @param e DTO
     * @return DO
     */
    D toDO(E e);

    /**
     * 批量将DTO转化为DO
     * @param e DTO
     * @return DO
     */
    List<D> toDO(List<E> e);

}
