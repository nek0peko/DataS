package pers.nek0peko.datas.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * MyClassLoader
 *
 * @author nek0peko
 * @date 2023/04/17
 */
public class MyClassLoader extends URLClassLoader {

    public MyClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            Class<?> c = findLoadedClass(name);
            if (isLoaded(c, resolve)) {
                return c;
            }

            try {
                c = findClass(name);
                if (isLoaded(c, resolve)) {
                    return c;
                }
            } catch (ClassNotFoundException ignored) {
            }

            if (getParent() != null) {
                try {
                    c = super.loadClass(name, resolve);
                    if (isLoaded(c, resolve)) {
                        return c;
                    }
                } catch (ClassNotFoundException ignored) {
                }
            }

            try {
                c = findSystemClass(name);
                if (isLoaded(c, resolve)) {
                    return c;
                }
            } catch (ClassNotFoundException ignored) {
            }

            throw new ClassNotFoundException(name);
        }
    }

    public void addFile(File f) throws IOException {
        addFile(f.toURI().toURL());
    }

    public void addFile(URL u) throws IOException {
        try {
            this.addURL(u);
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IOException("Could not add URL to system classloader");
        }
    }

    private boolean isLoaded(Class<?> c, boolean resolve) {
        if (c != null) {
            if (resolve) {
                resolveClass(c);
            }
            return true;
        }
        return false;
    }

}
