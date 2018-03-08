package com.arteco.mvc.sass;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceUtils;
import com.arteco.mvc.bootstrap.resource.loader.TreeDirectoryResourceLoader;
import com.arteco.mvc.bootstrap.response.CompilableResponse;
import com.arteco.mvc.bootstrap.response.Renders;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.cathive.sass.SassContext;
import com.cathive.sass.SassFileContext;
import com.cathive.sass.SassOptions;
import com.cathive.sass.SassOutputStyle;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SassResponse extends CompilableResponse<String> {

    private final String mainSassFileName;

    public SassResponse(String pathDir, String mainSassFileName) {
        super(new TreeDirectoryResourceLoader(pathDir, "scss"));
        this.mainSassFileName = mainSassFileName;
    }

    public static SassResponse sass(String workDir, String entryFile) {
        return new SassResponse(workDir, entryFile);
    }

    @Override
    protected void compileResource(WebContext webContext, List<WebResource> resources) {
        try {

            File tmpDir = FileUtils.getTempDirectory();
            File baseDir = new File(tmpDir.getAbsolutePath() + File.separator + "mvc");
            FileUtils.forceMkdir(baseDir);
            FileUtils.cleanDirectory(baseDir);

            for (WebResource resource : resources) {
                WebResourceUtils.writeResource(resource, baseDir);
            }

            Path srcRoot = Paths.get(baseDir.toURI());
            SassContext ctx = SassFileContext.create(srcRoot.resolve(mainSassFileName));
            SassOptions options = ctx.getOptions();
            options.setIncludePath(srcRoot);
            options.setOutputStyle(SassOutputStyle.COMPRESSED);
            ByteArrayOutputStream ous = new ByteArrayOutputStream();
            ctx.compile(ous);
            Renders.writeCss(webContext, new String(ous.toByteArray()));
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }
}
