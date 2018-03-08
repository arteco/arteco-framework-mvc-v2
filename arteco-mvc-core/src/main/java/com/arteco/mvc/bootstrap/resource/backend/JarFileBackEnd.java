package com.arteco.mvc.bootstrap.resource.backend;

import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import org.apache.commons.vfs2.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JarFileBackEnd implements WebResourceBackEnd {
    private static FileSystemManager fsManager;

    static {
        try {
            fsManager = VFS.getManager();
        } catch (FileSystemException e) {
            throw ExceptionUtils.manage(e);
        }

    }

    private FileObject jarFile;

    public JarFileBackEnd(String classPath) {
        try {
            File file = new File(classPath);
            jarFile = fsManager.resolveFile("jar:" + file.getAbsolutePath());
        } catch (FileSystemException e) {
            throw ExceptionUtils.manage(e);
        }
    }

    public static void main(String[] args) {
        List<WebResource> files = new JarFileBackEnd("web-examples/front-sample/target/front-sample-2.0.0-bundle.jar")
                .listResources("static", "scss");

        System.out.println(files.get(0).getFilename());
    }

    @Override
    public WebResource getResource(String pathFile) {
        try {
            FileObject file;
            file = jarFile.resolveFile(pathFile);
            return new VfsFileObject(file);
        } catch (FileSystemException e) {
            throw ExceptionUtils.manage(e);
        }
    }

    @Override
    public List<WebResource> listResources(String pathDir, String extension) {
        try {
            FileObject[] files = jarFile.findFiles(new FileExtensionSelector(extension));
            Arrays.sort(files);
            List<WebResource> result = new ArrayList<>();
            for (FileObject fo : files) {
                result.add(new VfsFileObject(fo));
            }
            return result;
        } catch (FileSystemException e) {
            throw ExceptionUtils.manage(e);
        }
    }

    private class VfsFileObject implements WebResource {
        private final FileObject file;

        VfsFileObject(FileObject file) {
            this.file = file;
        }

        @Override
        public InputStream getInputStream() {
            try {
                return file.getContent().getInputStream();
            } catch (FileSystemException e) {
                throw ExceptionUtils.manage(e);
            }
        }

        @Override
        public String getEncoding() {
            return "utf-8";
        }

        @Override
        public String getFilename() {
            return file.getName().getBaseName();
        }

        @Override
        public long getLastModified() {
            try {
                return file.getContent().getLastModifiedTime();
            } catch (FileSystemException e) {
                throw ExceptionUtils.manage(e);
            }
        }
    }
}
