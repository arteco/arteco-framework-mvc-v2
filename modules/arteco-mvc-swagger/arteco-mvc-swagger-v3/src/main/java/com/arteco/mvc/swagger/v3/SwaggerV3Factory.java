package com.arteco.mvc.swagger.v3;

import com.arteco.mvc.bootstrap.MimeType;
import com.arteco.mvc.bootstrap.WebApp;
import com.arteco.mvc.bootstrap.binding.Binding;
import com.arteco.mvc.bootstrap.router.WebRoute;
import com.arteco.mvc.bootstrap.utils.ExceptionUtils;
import com.arteco.mvc.swagger.SwaggerFactory;
import com.arteco.mvc.swagger.SwaggerFeature;
import com.arteco.mvc.swagger.SwaggerUtils;
import com.arteco.mvc.swagger.schema.SchemaDefinition;
import com.arteco.mvc.swagger.schema.SchemaManager;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SwaggerV3Factory implements SwaggerFactory {

    public static final String SCHEMA_PREFIX = "#/components/schemas/";

    private SchemaManager<SchemaDescriptor> schemaManager;
    private OpenAPI openAPI;

    public OpenAPI generateApiDoc(WebApp webApp) {
//        if (openAPI == null || webApp.isDevelopment()) {
        this.schemaManager = new SwaggerSchemaManager();
        openAPI = new OpenAPI();
        openAPI.setInfo(getInfo(webApp));
        for (WebRoute route : webApp.getRegister().getRoutes()) {
            PathItem pathItem = getPath(route);
            if (pathItem != null) {
                openAPI.path(route.getPath().getPath(), pathItem);
            }
        }
        openAPI.setComponents(getComponents());

//        }
        return openAPI;
    }

    @Override
    public String getVersion() {
        return "v3";
    }


    private PathItem getPath(WebRoute route) {
        PathItem pathItem = new PathItem();
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
            List<Parameter> parameters = null;
            RequestBody requestBody = null;
            for (Binding binding : bindings) {
                switch (binding.getScope()) {
                    case BODY:
                        requestBody = getRequestBody(binding);
                        break;
                    case PATH:
                    case HEADER:
                    case COOKIE:
                    case QUERY:
                        if (parameters == null) {
                            parameters = new ArrayList<>();
                        }
                        parameters.add(getParameter(binding));
                        break;
                    default:
                        throw ExceptionUtils.trigger("Scope is unknown");
                }
            }
            operation.setParameters(parameters);
            operation.setRequestBody(requestBody);
        }
        return pathItem;
    }

    private RequestBody getRequestBody(Binding binding) {
        RequestBody requestBody = new RequestBody();
        SchemaDefinition<SchemaDescriptor> schemaDefinition = schemaManager.getSchemaDefinition(binding.getType());
        requestBody.$ref(SCHEMA_PREFIX + schemaDefinition.getName());
        requestBody.setRequired(binding.isObligatory());
        return requestBody;
    }

    private Parameter getParameter(Binding binding) {
        Parameter param = new Parameter();
        param.setName(binding.getName());
        param.setIn(binding.getScope().name().toLowerCase());
        param.setRequired(binding.isObligatory());
        SchemaDefinition<SchemaDescriptor> schemaDefinition = schemaManager.getSchemaDefinition(binding.getType());
        param.schema(schemaDefinition.getDescriptor().create(SchemaScope.REFERENCE));
        return param;
    }


    private ApiResponses getResponses(SwaggerFeature feature) {
        Class<?> finalType = feature.getResponseType();

        Content content = new Content();
        MediaType mediaItem = new MediaType();

        SchemaDefinition<SchemaDescriptor> schemaDefinition = schemaManager.getSchemaDefinition(finalType);
        ObjectSchema schema = new ObjectSchema();
        schema.$ref(SCHEMA_PREFIX + schemaDefinition.getName());
        mediaItem.schema(schema);

        content.addMediaType(MimeType.JSON.getMimeType(), mediaItem);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setContent(content);
        apiResponse.setDescription("Result");

        ApiResponses responses = new ApiResponses();
        responses.setDefault(apiResponse);
        return responses;
    }


    private Components getComponents() {
        Components components = new Components();
        //types:
        Iterator<SchemaDefinition<SchemaDescriptor>> iterator = schemaManager.getAllSchemas();
        while (iterator.hasNext()) {
            SchemaDefinition<SchemaDescriptor> definition = iterator.next();
            components.addSchemas(definition.getName(), definition.getDescriptor().create(SchemaScope.DEFINITION));
        }
        //security:
//        components.addSecuritySchemes()
        return components;
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
