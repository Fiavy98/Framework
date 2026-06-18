package ControllerPerso;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ControllerScanner {

    public static List<String> scan(String webInfPath) throws Exception {
        List<String> controllers = new ArrayList<>();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        File classesDir = new File(webInfPath + "/classes");
        if (classesDir.exists()) {
            scanDirectory(classesDir, classesDir, classLoader, controllers);
        }

        File libDir = new File(webInfPath + "/lib");
        if (libDir.exists()) {
            for (File jar : libDir.listFiles()) {
                if (jar.getName().endsWith(".jar")) {
                    URL jarUrl = new URL("jar:file:" + jar.getAbsolutePath() + "!/");
                    java.net.URLClassLoader jarLoader =
                        new java.net.URLClassLoader(new URL[]{jarUrl}, classLoader);
                    scanJar(jar, jarLoader, controllers);
                    jarLoader.close();
                }
            }
        }

        return controllers;
    }

    private static void scanDirectory(File root, File dir,
                                      ClassLoader loader,
                                      List<String> result) {
        if (!dir.exists() || !dir.isDirectory()) return;

        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                scanDirectory(root, file, loader, result);
            } else if (file.getName().endsWith(".class")) {
                String className = root.toURI()
                        .relativize(file.toURI())
                        .getPath()
                        .replace("/", ".")
                        .replace(".class", "");
                tryLoad(className, loader, result);
            }
        }
    }

    private static void scanJar(File jar, ClassLoader loader,
                                 List<String> result) {
        try (java.util.jar.JarFile jarFile = new java.util.jar.JarFile(jar)) {
            java.util.Enumeration<java.util.jar.JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.endsWith(".class")) {
                    String className = name.replace("/", ".").replace(".class", "");
                    tryLoad(className, loader, result);
                }
            }
        } catch (Exception ignored) {}
    }
    
    private static void tryLoad(String className, ClassLoader loader,
                                 List<String> result) {
        try {
            Class<?> clazz = loader.loadClass(className);
            if (clazz.isAnnotationPresent(Controller.class)) {
                result.add(className);
            }
        } catch (Throwable ignored) {}
    }
}
