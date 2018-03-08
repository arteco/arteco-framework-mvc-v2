package com.arteco.mvc.swagger.v2;

import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.arteco.mvc.swagger.SwaggerFactory;
import com.arteco.mvc.swagger.SwaggerFeature;
import com.arteco.mvc.swagger.SwaggerUtils;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.schema.SchemaManager;
import io.swagger.models.*;
import io.swagger.models.parameters.*;

import java.util.*;

@SuppressWarnings("Duplicates")
public class SwaggerV2Factory implements SwaggerFactory {

    public static final String SCHEMA_PREFIX = "#/definitions/";

    private Swagger openAPI;
    private SchemaManager<ModelPropertyDescriptor> schemaManager;

    @Override
    public Object generateApiDoc(WebApp webApp) {
//        TODO; if (openAPI == null || webApp.isDevelopment()) {
        schemaManager = new SwaggerModelPropertyManager();
        openAPI = new Swagger();
        openAPI.setInfo(getInfo(webApp));
        for (WebRoute route : webApp.getRegister().getRoutes()) {
            Path pathItem = getPath(route);
            if (pathItem != null) {
                openAPI.path(route.getPath().getPath(), pathItem);
            }
        }
        Iterator<SchemaDefinition<ModelPropertyDescriptor>> iterator = schemaManager.getAllSchemas();
        while (iterator.hasNext()) {
            SchemaDefinition<ModelPropertyDescriptor> schemaDefinition = iterator.next();
            String modelName = schemaDefinition.getName();
            openAPI.addDefinition(modelName, schemaDefinition.getDescriptor().getModel());
        }
//        }
        return openAPI;
    }

    @Override
    public String getVersion() {
        return "v2";
    }

    private Path getPath(WebRoute route) {
        Path pathItem = new Path();
        Operation operation = new Operation();
        switch (route.getMethod()) {
            case GET:
                pathItem.get(operation);
                break;
            case POST:
                pathItem.post(operation);
                break;
            default:
                throw ExceptionUtils.trigger("No operation registered");
        }

        SwaggerFeature feature = (SwaggerFeature) route.getFeature(SwaggerFeature.class);
        if (feature == null) {
            return null;
        }
        operation.setSummary(feature.getSummary());
        operation.setDescription(feature.getDescription());
        operation.setTags(feature.getTags());
        operation.setResponses(getResponses(feature));

        List<Binding> bindings = SwaggerUtils.getBindings(route);
        if (bindings != null) {
            List<Parameter> parameters = new ArrayList<>();
            for (Binding binding : bindings) {
                Parameter parameter;
                switch (binding.getScope()) {
                    case BODY:
                        parameter = getBodyParameter(binding);
                        break;
                    case PATH:
                        parameter = getPathParameter(binding);
                        break;
                    case COOKIE:
                        parameter = getCookieParameter(binding);
                        break;
                    case HEADER:
                        parameter = getHeaderParameter(binding);
                        break;
                    case QUERY:
                        parameter = getQueryParameter(binding);
                        break;
                    default:
                        throw ExceptionUtils.trigger("Scope is unknown");
                }
                parameters.add(parameter);
            }
            if (parameters.size() > 0) {
                operation.setParameters(parameters);
            }
        }
        return pathItem;
    }

    private Map<String, Response> getResponses(SwaggerFeature feature) {
        Map<String, Response> responseMap = new HashMap<>();
        Class responseType = feature.getResponseType();
        SchemaDefinition<ModelPropertyDescriptor> schemaDefinition = schemaManager.getSchemaDefinition(responseType);
        Response response = new Response();
        response.schema(schemaDefinition.getDescriptor().getProperty());
        response.setDescription("Default Result");
        responseMap.put("default", response);
        return responseMap;
    }


    private QueryParameter getQueryParameter(Binding binding) {
        QueryParameter pathParameter = new QueryParameter();
        completeSerializableParameter(pathParameter, binding);
        return pathParameter;
    }

    private void completeSerializableParameter(AbstractSerializableParameter pathParameter, Binding binding) {
        SchemaDefinition<ModelPropertyDescriptor> schemaDefinition = schemaManager.getSchemaDefinition(binding.getType());
        pathParameter.setType(schemaDefinition.getDescriptor().getType());
        if (binding.getType().isCollection()) {
            pathParameter.items(schemaManager.getSchemaDefinition(binding.getType().getComponentType()).getDescriptor().getProperty());
        }
        pathParameter.setName(binding.getName());
        pathParameter.setDescription(binding.getDescription());
        pathParameter.setRequired(binding.isObligatory());
    }

    private HeaderParameter getHeaderParameter(Binding binding) {
        HeaderParameter pathParameter = new HeaderParameter();
        completeSerializableParameter(pathParameter, binding);
        return pathParameter;
    }

    private CookieParameter getCookieParameter(Binding binding) {
        CookieParameter pathParameter = new CookieParameter();
        completeSerializableParameter(pathParameter, binding);
        return pathParameter;
    }

    private PathParameter getPathParameter(Binding binding) {
        PathParameter pathParameter = new PathParameter();
        completeSerializableParameter(pathParameter, binding);
        return pathParameter;
    }


    private Parameter getBodyParameter(Binding binding) {
        BodyParameter parameter = new BodyParameter();
        RefModel schema = new RefModel();
        SchemaDefinition<ModelPropertyDescriptor> schemaDefinition = schemaManager.getSchemaDefinition(binding.getType());
        schema.set$ref(SCHEMA_PREFIX + schemaDefinition.getName());
        parameter.schema(schema);
        parameter.name(binding.getName());
        return parameter;
    }


    private Info getInfo(WebApp webApp) {
        Info info = new Info();
        info.setTitle(webApp.getInfo().getName());
        info.setDescription(webApp.getInfo().getDescription());
        info.setTermsOfService(webApp.getInfo().getTermsOfService());
        info.setContact(getContact(webApp));
        info.setLicense(getLicense(webApp));
        info.setVersion(webApp.getInfo().getVersion());
        return info;
    }

    private License getLicense(WebApp webApp) {
        License license = new License();
        license.setName(webApp.getInfo().getLicenseName());
        license.setUrl(webApp.getInfo().getLicenseUrl());
        return license;
    }

    private Contact getContact(WebApp webApp) {
        Contact contact = new Contact();
        contact.setName(webApp.getInfo().getAuthor());
        contact.setUrl(webApp.getInfo().getUrl());
        contact.setEmail(webApp.getInfo().getEmail());
        return contact;
    }
}
