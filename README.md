# arteco-mvc-framework

# TODO

## Pending

1. Restart app on dev mode
1. Vaadin integration (responsive)

### Last modified on

1. Closurejs packaging
1. Scss as CSS processor
1. Static resources (with last modified)

## Implemented

1. Last modified functionality
1. Security module, acl and user provider
1. Swagger spec generator in generate-sources phase
1. Fluent API
1. Testable
1. Sql Transactional with HikariCP
1. Database migrations with Flyway 
1. Generic supplier methods
1. Arbitrary module inclusion
1. Stack-able by complex components from bottom to up
1. I18n (locale resolver, messages interpolator)
1. Flexible process pipeline (validation, transformation, transaction, routing, security, exception)
1. Jade as HTML processor


## Optional functionality

1. Optional Rest client generator with swagger-codegen  
1. Optional Sql library -> QueryDSL-jdbc
1. Self contained and executable jar



# Websites

## Configure DataSource (flyway)



## Create executable jar

Append shade plugin to your web site maven project. Ensure that the main class is set

    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-shade-plugin</artifactId>
        <executions>
            <execution>
                <goals>
                    <goal>shade</goal>
                </goals>
                <configuration>
                    <shadedArtifactAttached>true</shadedArtifactAttached>
                     <finalName>${project.artifactId}-${project.version}-bundle</finalName>
                    <transformers>
                        <transformer implementation=
                                             "org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                            <mainClass>com.arteco.staticsite.Main</mainClass>
                        </transformer>
                    </transformers>
                </configuration>
            </execution>
        </executions>
    </plugin>
    
## Query-dsl jdbc

    <plugin>
        <groupId>com.querydsl</groupId>
        <artifactId>querydsl-maven-plugin</artifactId>
        <version>${querydsl.version}</version>
        <executions>
            <execution>
                <goals>
                    <goal>export</goal>
                </goals>
            </execution>
        </executions>
        <configuration>
            <jdbcUser>root</jdbcUser>
            <jdbcDriver>com.mysql.jdbc.Driver</jdbcDriver>
            <jdbcUrl>jdbc:mysql://localhost/myapp</jdbcUrl>
            <packageName>com.arteco.mvc.sample.domain</packageName>
            <sourceFolder>${project.basedir}/target/generated-sources/java</sourceFolder>
            <targetFolder>${project.basedir}/target/generated-sources/java</targetFolder>
            <exportBeans>true</exportBeans>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>5.1.44</version>
            </dependency>
        </dependencies>
    </plugin> 
    
