package pers.nek0peko.datas.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import pers.nek0peko.datas.dto.data.SystemErrorEnum;
import pers.nek0peko.datas.exception.BusinessException;

import javax.annotation.Nonnull;

/**
 * ApplicationContextHelper
 *
 * @author nek0peko
 * @date 2022/12/12
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public void setApplicationContext(@Nonnull ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> targetClz) {
        T beanInstance = null;
        // 优先按type查
        try {
            beanInstance = applicationContext.getBean(targetClz);
        } catch (Exception e) {
            throw new BusinessException(SystemErrorEnum.S_SERVER_ERROR);
        }
        // 按name查
        if (beanInstance == null) {
            String simpleName = targetClz.getSimpleName();
            // 首字母小写
            simpleName = Character.toLowerCase(simpleName.charAt(0)) + simpleName.substring(1);
            beanInstance = (T) applicationContext.getBean(simpleName);
        }
        if (beanInstance == null) {
            throw new RuntimeException("Component " + targetClz + " can not be found in Spring Container");
        }
        return beanInstance;
    }

    public static Object getBean(String className) {
        return ApplicationContextHelper.applicationContext.getBean(className);
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return ApplicationContextHelper.applicationContext.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType, Object... params) {
        return ApplicationContextHelper.applicationContext.getBean(requiredType, params);
    }

}