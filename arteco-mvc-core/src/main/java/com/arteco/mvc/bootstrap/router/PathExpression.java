package com.arteco.mvc.bootstrap.router;

import com.arteco.mvc.bootstrap.utils.SeoUtils;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rarnau on 25/1/18.
 * Arteco Consulting SL.
 * info@arteco-consulting.com
 */
@Data
public class PathExpression {

    private final String path;
    private final Pattern pattern;
    private final String[] pathVariables;

    public PathExpression(String path) {
        this.path = path;
        path = path.length() > 1 ? SeoUtils.removeEndSlash(path) : path;
        path = StringUtils.replace(path, "/", "\\/");
        pathVariables = StringUtils.substringsBetween(path, "{", "}");
        if (pathVariables != null) {
            for (int i = 0; i < pathVariables.length; i++) {
                String pathVar = pathVariables[i];
                PathType type = null;
                if (pathVar.contains(":")) {
                    String[] tuple = pathVar.split(":");
                    pathVariables[i] = tuple[0];
                    type = PathType.valueOfTerm(tuple[1]);

                }
                String expvar = "{" + pathVar + "}";
                String term = PathType.typeDef.getRegexp();
                if (type != null) {
                    term = type.getRegexp();
                }
                path = StringUtils.replace(path, expvar, term);
            }
        }
        pattern = Pattern.compile("^" + path + "$");
    }

    public Map<String, String> match(String uri) {
        Matcher m = pattern.matcher(uri);
        if (m.find()) {
            Map<String, String> pathVariablesMap = new HashMap<>();
            if (pathVariables != null && pathVariables.length > 0) {
                int i = 1;
                for (String var : pathVariables) {
                    pathVariablesMap.put(var, m.group(i++));
                }
            }
            return pathVariablesMap;
        }
        return null;
    }


    @Override
    public String toString() {
        return "PathExpression{" +
                "path='" + path + '\'' +
                '}';
    }

    private enum PathType {
        typeDef(null, "([^\\/]+)"),
        typeInt("int", "([\\d]+)");

        private final String term;
        private String regexp;

        PathType(String term, String regexp) {
            this.term = term;
            this.regexp = regexp;
        }

        public static PathType valueOfTerm(String term) {
            for (PathType p : PathType.values()) {
                if (StringUtils.equalsIgnoreCase(term, p.getTerm())) {
                    return p;
                }
            }
            return null;
        }

        public String getTerm() {
            return term;
        }

        public String getRegexp() {
            return regexp;
        }
    }
}
