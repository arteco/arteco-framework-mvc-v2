package com.arteco.mvc.bootstrap.resource;

import com.arteco.mvc.bootstrap.resource.backend.ClassPathBackend;
import com.arteco.mvc.bootstrap.resource.backend.JarFileBackEnd;
import com.arteco.mvc.bootstrap.resource.backend.WebResourceBackEnd;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

/**
 * Created by rarnau on 1/2/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
public class WebResourceUtils {
    private static WebResourceBackEnd backEnd;

    static {
        try {
            String classPath = System.getProperty("java.class.path");
            boolean intoJar = StringUtils.countMatches(classPath, ".jar") <= 1;
            backEnd = intoJar ? new JarFileBackEnd(classPath) : new ClassPathBackend();
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }

    public static WebResource getResource(String pathFile) {
        return backEnd.getResource(pathFile);
    }

    public static List<WebResource> listResources(String pathDir, String extension) {
        return backEnd.listResources(pathDir, extension);
    }

    public static void writeResource(WebResource resource, File baseDir) {
        try {
            File dest = new File(baseDir.getAbsolutePath() + File.separator + resource.getFilename());
            FileUtils.copyInputStreamToFile(resource.getInputStream(), dest);
            dest.setLastModified(resource.getLastModified());
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }

    }
}
