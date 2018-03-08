package com.arteco.mvc.bootstrap.resource.backend;

import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceUtils;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClassPathBackend implements WebResourceBackEnd {


    @Override
    public WebResource getResource(String pathFile) {
        return new ClassPathFile(pathFile);
    }

    @Override
    public List<WebResource> listResources(String pathDir, String extension) {
        File file = getClassPathFile(pathDir);
        List<File> files = new ArrayList<>(FileUtils.listFiles(file, new String[]{extension}, true));
        Collections.sort(files);
        List<WebResource> result = new ArrayList<>();
        files.forEach(f -> result.add(new ClassPathFile(f)));
        return result;
    }

    private File getClassPathFile(String pathFile) {
        try {
            URL url = WebResourceUtils.class.getClassLoader().getResource(pathFile);
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw ExceptionUtils.manage(e);
        }
    }

    private class ClassPathFile implements WebResource {
        private final File file;

        ClassPathFile(String pathFile) {
            this.file = getClassPathFile(pathFile);
        }

        ClassPathFile(File pathFile) {
            this.file = pathFile;
        }


        @Override
        public InputStream getInputStream() {
            try {
                return new FileInputStream(file);
            } catch (FileNotFoundException e) {
                throw ExceptionUtils.manage(e);
            }
        }

        @Override
        public String getEncoding() {
            return "utf-8";
        }

        @Override
        public String getFilename() {
            return file.getName();
        }

        @Override
        public long getLastModified() {
            return file.lastModified();
        }
    }
}
