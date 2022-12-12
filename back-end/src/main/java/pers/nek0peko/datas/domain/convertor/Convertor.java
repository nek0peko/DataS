package pers.nek0peko.datas.domain.convertor;

import java.util.List;

/**
 * Convertor
 *
 * @author nek0peko
 * @date 2022/12/13
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
     * @param e DTO List
     * @return DO List
     */
    List<D> toDO(List<E> e);

    /**
     * 将DO转化为DTO
     * @param d DO
     * @return DTO
     */
    E toDTO(D d);

    /**
     * 批量将DO转化为DTO
     * @param d DO List
     * @return DTO List
     */
    List<E> toDTO(List<D> d);

}
