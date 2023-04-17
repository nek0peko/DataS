package pers.nek0peko.datas.util;

import lombok.NoArgsConstructor;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * BaseClassLoaderProvider
 *
 * @author nek0peko
 * @date 2023/04/17
 */
@NoArgsConstructor
public abstract class BaseClassLoaderProvider {

//    private static final String PATH = "/opt/bi/drivers";

    protected static MyClassLoader myClassLoader;

    @PostConstruct
    public void init() throws Exception {
        // 仅用于Windows开发环境测试
        final String path = System.getProperty("user.dir") + "\\back-end\\drivers";

        final URL[] urls = new URL[]{(new File(path)).toURI().toURL()};
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        myClassLoader = new MyClassLoader(urls, classLoader);

        final File[] files = new File(path).listFiles();
        if (Objects.nonNull(files)) {
            for (final File file : files) {
                if (file.getName().endsWith(".jar")) {
                    try {
                        myClassLoader.addFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
