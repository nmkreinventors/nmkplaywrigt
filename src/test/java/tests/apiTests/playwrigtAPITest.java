package tests.apiTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.Set;

public class playwrigtAPITest {


    @Test
    public void testPlayWrigtAPI() throws Exception{

        try(Playwright playwright = Playwright.create()) {
            APIRequestContext request =playwright.request().newContext(
                    new APIRequest.NewContextOptions().setBaseURL("https://jsonplaceholder.typicode.com")
            );

            //get
//            APIResponse response =request.get("/posts", RequestOptions.create().setQueryParam("userId",1));
//            System.out.println("Status:"+response.status());
//            System.out.println("Body:"+response.text());

//            ObjectMapper mapper = new ObjectMapper();

            //Parsing JSON Object to a POJO Class

//            Post post = mapper.readValue(response.text(), Post.class);
//            System.out.println(post.id);
//            System.out.println(post.title);
//            System.out.println(post.body);
//            System.out.println(post.userId);

            //Parsing a JSON ARRAY to Array of Post Reference Variables
//            List<Post> posts = mapper.readValue(response.text(), mapper.getTypeFactory().constructCollectionType(List.class, Post.class));
//            System.out.println(posts.get(0).id);
//            System.out.println(posts.get(0).title);
//            System.out.println(posts.get(0).body);
//            System.out.println(posts.get(0).userId);
//            System.out.println(posts.size());

            //post, body, header
//            APIResponse response =request.post("/posts", RequestOptions.create()
//                    .setData("{\"title\": \"nmk\", \"body\": \"playwright\", \"userId\": 2}")
//                    .setHeader("Content-Type", "application/json"));
//            System.out.println("Status:"+response.status());
//            System.out.println("Body:"+response.text());

            //post with POJO as a Request body
            Post newpost = new Post();
            newpost.title = "nmk";
            newpost.body = "playwright";
            newpost.userId = 2;
            //String body = mapper.writeValueAsString(newpost);
            //APIResponse response =request.post("/posts", RequestOptions.create().setData(body));
            APIResponse response =request.post("/posts", RequestOptions.create().setData(""));
            System.out.println("Body:"+response.text());
            System.out.println("=============================================");

            ObjectMapper mapper = new ObjectMapper();

            // Load schema from src/test/resources/schema.json
            InputStream schemaStream = playwrigtAPITest.class.getClassLoader().getResourceAsStream("schema.json");
            JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);
            JsonSchema schema = factory.getSchema(schemaStream);

            Set<ValidationMessage> validationErrors = schema.validate(mapper.readTree(response.text()));
            if (validationErrors.isEmpty()) {
                System.out.println("Schema validation passed");
            } else {
                validationErrors.forEach(e -> System.out.println("Validation error: " + e.getMessage()));
            }
            System.out.println(mapper.readTree(response.text()));
//            Post post = mapper.readValue(response.text(), Post.class);
//            System.out.println(post.id);
//            System.out.println(post.title);
//            System.out.println(post.body);
//            System.out.println(post.userId);
//            System.out.println(response.status());



            //put
//            APIResponse response =request.put("/posts/1", RequestOptions.create()
//                    .setData("{\"id\": 1,\"title\":\"foo\",\"body\":\"bar\",\"userId\": 1}")
//                    .setHeader("Content-Type", "application/json"));
//            System.out.println("Status:"+response.status());
//            System.out.println("Body:"+response.text());

            //delete
//            APIResponse response = request.delete("/posts/1");
//            System.out.println("Status:"+response.status());

        }
    }
}
