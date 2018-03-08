package com.arteco.mvc.closurejs;

import com.arteco.mvc.bootstrap.WebContext;
import com.arteco.mvc.bootstrap.resource.WebResource;
import com.arteco.mvc.bootstrap.resource.WebResourceLoader;
import com.arteco.mvc.bootstrap.response.CompilableResponse;
import com.arteco.mvc.bootstrap.response.Renders;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.google.javascript.jscomp.*;
import com.google.javascript.jscomp.Compiler;
import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.List;

public class ClosureJsResponse extends CompilableResponse<String> {

    private final Compiler compiler = new Compiler(new BlackHoleErrorManager());
    private final CompilerOptions options = new CompilerOptions();


    public ClosureJsResponse(WebResourceLoader resourceLoader) {
        super(resourceLoader);
        CompilationLevel.ADVANCED_OPTIMIZATIONS.setOptionsForCompilationLevel(options);
    }

    protected void compileResource(WebContext webContext, List<WebResource> resources) {
        try {
            List<SourceFile> sources = new ArrayList<>();
            List<SourceFile> externals = new ArrayList<>();
            for (WebResource resource : resources) {
                SourceFile sourceFile = SourceFile.fromCode(
                        resource.getFilename(),
                        IOUtils.toString(
                                resource.getInputStream(),
                                resource.getEncoding()));
                sources.add(sourceFile);
            }
            compiler.compile(externals, sources, options);
            String output = compiler.toSource();
            Renders.writeJs(webContext, output);
        } catch (Exception e) {
            throw ExceptionUtils.manage(e);
        }
    }


}
