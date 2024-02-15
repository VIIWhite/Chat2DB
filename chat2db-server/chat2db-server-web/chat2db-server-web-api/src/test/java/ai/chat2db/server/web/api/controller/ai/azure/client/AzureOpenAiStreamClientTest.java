package ai.chat2db.server.web.api.controller.ai.azure.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.chat2db.server.tools.common.exception.ParamBusinessException;
import okhttp3.OkHttpClient;
import okhttp3.sse.EventSourceListener;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import java.util.Collections;

public class AzureOpenAiStreamClientTest {

    @Test
    public void builderSetsApiKeyCorrectly() {
        // test if the ApiKey has been set correctly
        String testApiKey = "testApiKey";
        AzureOpenAiStreamClient client = AzureOpenAiStreamClient.builder()
                .apiKey(testApiKey)
                .build();
        assertEquals(testApiKey, client.getApiKey());
    }

    @Test
    public void builderSetsEndpointCorrectly() {
        // test if the endpoint has been set correctly
        String testEndpoint = "http://test.endpoint.com";
        AzureOpenAiStreamClient client = AzureOpenAiStreamClient.builder()
                .endpoint(testEndpoint)
                .build();
        assertEquals(testEndpoint, client.getEndpoint());
    }

    @Test
    public void builderSetsDeployIdCorrectly() {
        // test if the deploy id has been set correctly
        String testDeployId = "deployId";
        AzureOpenAiStreamClient client = AzureOpenAiStreamClient.builder()
                .deployId(testDeployId)
                .build();
        assertEquals(testDeployId, client.getDeployId());
    }

    @Test
    public void builderSetsOkHttpClientCorrectly() {
        // test if the OkHttpClient has been set correctly
        OkHttpClient testOkHttpClient = new OkHttpClient();
        AzureOpenAiStreamClient client = AzureOpenAiStreamClient.builder()
                .okHttpClient(testOkHttpClient)
                .build();
        assertEquals(testOkHttpClient, client.getOkHttpClient());
    }




    @Test
    public void streamCompletionsThrowsExceptionOnEmptyChatMessages() {
        //test if the ParamBusinessException is thrown for empty chat messages
        AzureOpenAiStreamClient client = AzureOpenAiStreamClient.builder()
                .apiKey("testApiKey")
                .endpoint("http://test.endpoint.com")
                .deployId("deployId")
                .build();

        // use an empty List<AzureChatMessage> for empty message
        assertThrows(ParamBusinessException.class, () -> {
            client.streamCompletions(Collections.emptyList(), mock(EventSourceListener.class));
        });
    }




}
