package tests.apiTests;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class playwrightAPIAuth {



    @Test
    public void playwrightAPIAuth(){

        try(Playwright playwright = Playwright.create()){

            APIRequestContext request = playwright.request().newContext(
                    new APIRequest.NewContextOptions().setBaseURL("https://api.openweathermap.org")
                            .setExtraHTTPHeaders(Map.of("Authorization", "Bearer e6ccb66d5eb2fba55074f854ae195c56"))
            );
            long start = System.currentTimeMillis();
            APIResponse response =request.get("/data/2.5/weather", RequestOptions.create()
                    .setQueryParam("lat","44.34")
                    .setQueryParam("lon","10.99"));
            long end = System.currentTimeMillis();
            long duration = end - start;

            System.out.println(response.status());
            System.out.println(response.text());

        }

    }

}
