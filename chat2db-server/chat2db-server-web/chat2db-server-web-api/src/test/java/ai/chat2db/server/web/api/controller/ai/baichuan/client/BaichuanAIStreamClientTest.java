package ai.chat2db.server.web.api.controller.ai.baichuan.client;

import ai.chat2db.server.web.api.controller.ai.azure.client.AzureOpenAiStreamClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import ai.chat2db.server.tools.common.exception.ParamBusinessException;
import okhttp3.OkHttpClient;
import okhttp3.sse.EventSourceListener;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import static org.junit.jupiter.api.Assertions.*;

class BaichuanAIStreamClientTest {

    @Test
    void builderSetApiKeyCorrectly() {
        // test if the ApiKey has been set correctly
        String testApiKey = "testApiKey";
        BaichuanAIStreamClient client = BaichuanAIStreamClient.builder()
                .apiKey(testApiKey)
                .build();
        assertEquals(testApiKey, client.getApiKey());
    }

    @Test
    void buildSetSecretKeyCorrectly() {
        // test if the SecretKey has been set correctly
        String testSecretKey = "testSecretKey";
        BaichuanAIStreamClient client = BaichuanAIStreamClient.builder()
                .secretKey(testSecretKey)
                .build();
        assertEquals(testSecretKey, client.getSecretKey());
    }

    @Test
    void buildSetApiHostCorrectly() {
        // test if the ApiHost has been set correctly
        String testApiHost = "testApiHost";
        BaichuanAIStreamClient client = BaichuanAIStreamClient.builder()
                .apiHost(testApiHost)
                .build();
        assertEquals(testApiHost, client.getApiHost());
    }

    @Test
    void buildSetModelCorrectly() {
        // test if the Model has been set correctly
        String testModel = "testModel";
        BaichuanAIStreamClient client = BaichuanAIStreamClient.builder()
                .model(testModel)
                .build();
        assertEquals(testModel, client.getModel());
    }

    @Test
    void buildSetEmbeddingModelCorrectly() {
        // test if the EmbeddingModel has been set correctly
        String testEmbeddingModel = "testEmbeddingModel";
        BaichuanAIStreamClient client = BaichuanAIStreamClient.builder()
                .embeddingModel(testEmbeddingModel)
                .build();
        assertEquals(testEmbeddingModel, client.getEmbeddingModel());
    }

    @Test
    void buildSetOkHttpClientCorrectly() {
        // test if the OkHttpClient has been set correctly
        OkHttpClient testOkHttpClient = new OkHttpClient();
        BaichuanAIStreamClient client = BaichuanAIStreamClient.builder()
                .okHttpClient(testOkHttpClient)
                .build();
        assertEquals(testOkHttpClient, client.getOkHttpClient());
    }

    @Test
    public void streamCompletionsThrowsExceptionOnEmptyChatMessages() {
        //test if the ParamBusinessException is thrown for empty chat messages
        BaichuanAIStreamClient client = BaichuanAIStreamClient.builder()
                .apiKey("testApiKey")
                .secretKey("testSecretKey")
                .apiHost("testApiHost")
                .model("testModel")
                .embeddingModel("testEmbdeeingModel")
                .build();

        // use an empty List<AzureChatMessage> for empty message
        assertThrows(ParamBusinessException.class, () -> {
            client.streamCompletions(Collections.emptyList(), mock(EventSourceListener.class));
        });
    }
}