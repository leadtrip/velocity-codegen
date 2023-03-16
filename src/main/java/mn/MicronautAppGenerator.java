package mn;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class MicronautAppGenerator {

    static final String ROOT_DIR = "src/main/java/mn";
    static final String GENERATED_DIR = ROOT_DIR + "/generated";
    static final String MODELS_DIR = GENERATED_DIR + "/models";
    static final String ENDPOINTS_DIR = GENERATED_DIR + "/endpoints";
    static final String SERVICES_DIR = GENERATED_DIR + "/services";
    static final String TESTS_DIR = GENERATED_DIR + "/tests";
    static final String OTHER_DIR = GENERATED_DIR + "/other";
    static final String TEMPLATE_DIR = ROOT_DIR + "/templates";
    static final String DTO_TEMPLATE = TEMPLATE_DIR + "/dto_template.vm";
    static final String ENTITY_TEMPLATE = TEMPLATE_DIR + "/entity_template.vm";
    static final String TRANSFORMER_TEMPLATE = TEMPLATE_DIR + "/transformer_template.vm";
    static final String SERVICE_TEMPLATE = TEMPLATE_DIR + "/service_template.vm";
    static final String CONTROLLER_TEMPLATE = TEMPLATE_DIR + "/controller_template.vm";
    static final String REPOSITORY_TEMPLATE = TEMPLATE_DIR + "/repository_template.vm";
    static final String CONTROLLER_TEST_TEMPLATE = TEMPLATE_DIR + "/controller_test_template.vm";
    static final String REPOSITORY_FIXTURE_TEMPLATE = TEMPLATE_DIR + "/repository_fixture_template.vm";
    static final String POSTMAN_COLLECTION_TEMPLATE = TEMPLATE_DIR + "/postman_template.vm";
    static final String BASE_PACKAGE = "mn.generated";
    static final String MODELS_PACKAGE = BASE_PACKAGE + ".models";
    static final String ENDPOINTS_PACKAGE = BASE_PACKAGE + ".endpoints";
    static final String SERVICES_PACKAGE = BASE_PACKAGE + ".services";
    static final String TESTS_PACKAGE = BASE_PACKAGE + ".tests";

    static final String TEST_CONSTRUCTOR_TODO = "//TODO fill constructor with appropriate values for test";
    static final String ENTITY_NAME = "Employee";
    static final Map<String, String> ENTITY_FIELDS = new LinkedHashMap<>();
    static {
        ENTITY_FIELDS.put( "name", "String" );
        ENTITY_FIELDS.put( "accountId", "java.util.UUID" );
    }

    public static void main(String[] args) {
        new VelocityEngine().init();
        new MicronautAppGenerator();
    }

    public MicronautAppGenerator() {
        init();
        generate();
    }

    private VelocityContext context;

    void init() {
        context = new VelocityContext();
        context.put("basePkg", BASE_PACKAGE);
        context.put("modelsPkg", MODELS_PACKAGE);
        context.put("endpointsPkg", ENDPOINTS_PACKAGE);
        context.put("servicesPkg", SERVICES_PACKAGE);
        context.put("testsPkg", TESTS_PACKAGE);
        context.put("entityName", ENTITY_NAME);
        context.put("entityFields", ENTITY_FIELDS);
        context.put("testConstructorTodo", TEST_CONSTRUCTOR_TODO);
    }

    void generate() {
        generateModels();
        generateServices();
        generateController();
        generateTests();
        generatePostmanCollection();
    }

    private void generatePostmanCollection() {
        writeFile(context, POSTMAN_COLLECTION_TEMPLATE,OTHER_DIR + "/" + ENTITY_NAME + "Postman.json");
    }

    void generateModels() {
        generateDto();
        generateEntity();
    }
    
    void generateServices() {
        generateTransformer();
        generateRepository();
        generateService();
    }

    void generateTests() {
        generateRepositoryFixture();
        generateControllerTest();
    }

    private void generateRepositoryFixture() {
        writeFile(context, REPOSITORY_FIXTURE_TEMPLATE,TESTS_DIR + "/" + ENTITY_NAME + "RepositoryFixture.groovy");
    }

    private void generateControllerTest() {
        writeFile(context, CONTROLLER_TEST_TEMPLATE,TESTS_DIR + "/" + ENTITY_NAME + "ControllerSpec.groovy");
    }

    void generateController() {
        writeFile(context, CONTROLLER_TEMPLATE,ENDPOINTS_DIR + "/" + ENTITY_NAME + "Controller.java");
    }

    private void generateService() {
        writeFile(context, SERVICE_TEMPLATE,SERVICES_DIR + "/" + ENTITY_NAME + "Service.java");
    }

    private void generateRepository() {
        writeFile(context, REPOSITORY_TEMPLATE,SERVICES_DIR + "/" + ENTITY_NAME + "Repository.java");
    }

    private void generateTransformer() {
        writeFile(context, TRANSFORMER_TEMPLATE,SERVICES_DIR + "/" + ENTITY_NAME + "Transformer.java");
    }

    void generateDto() {
        writeFile(context, DTO_TEMPLATE,MODELS_DIR + "/" + ENTITY_NAME + "Dto.java");
    }

    void generateEntity() {
        writeFile(context, ENTITY_TEMPLATE, MODELS_DIR + "/" + ENTITY_NAME + "Entity.java");
    }

    void writeFile( VelocityContext context, String tempalteName, String fileName ) {
        try ( FileWriter writer = new FileWriter(fileName) ){
            Velocity.mergeTemplate(tempalteName, "UTF-8", context, writer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
