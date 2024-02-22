package ai.chat2db.server.web.api.controller.ai.openai.client;

import ai.chat2db.server.domain.api.model.Config;
import ai.chat2db.server.domain.api.service.ConfigService;
import ai.chat2db.server.tools.base.wrapper.result.DataResult;
import ai.chat2db.server.web.api.controller.ai.openai.client.TestableOpenAIClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestableOpenAIClientTest {

    @Mock
    private ConfigService mockConfigService;

    @Mock
    private Config mockConfig;

    private TestableOpenAIClient testableOpenAIClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testableOpenAIClient = new TestableOpenAIClient();
        testableOpenAIClient.initializeConfigService(mockConfigService);
    }


    @Test
    void testRefreshClientWithProxy() {
        Config proxyHostConfig = new Config();
        proxyHostConfig.setContent("127.0.0.1");
        DataResult<Config> proxyHostResult = DataResult.of(proxyHostConfig);

        Config proxyPortConfig = new Config();
        proxyPortConfig.setContent("8080");
        DataResult<Config> proxyPortResult = DataResult.of(proxyPortConfig);

        Config apiHostConfig = new Config();
        apiHostConfig.setContent("api.openai.com");
        DataResult<Config> apiHostResult = DataResult.of(apiHostConfig);

        Config apiKeyConfig = new Config();
        apiKeyConfig.setContent("mock-api-key");
        DataResult<Config> apiKeyResult = DataResult.of(apiKeyConfig);

        when(mockConfigService.find(eq(TestableOpenAIClient.PROXY_HOST))).thenReturn(proxyHostResult);
        when(mockConfigService.find(eq(TestableOpenAIClient.PROXY_PORT))).thenReturn(proxyPortResult);
        when(mockConfigService.find(eq(TestableOpenAIClient.OPENAI_HOST))).thenReturn(apiHostResult);
        when(mockConfigService.find(eq(TestableOpenAIClient.OPENAI_KEY))).thenReturn(apiKeyResult);

        testableOpenAIClient.refreshClient();

        assertNotNull(testableOpenAIClient.getClient(), "Client should be refreshed and not null");
    }


    @Test
    void testGetConfigReturnsDefaultValue() {
        when(mockConfigService.find(anyString())).thenReturn(DataResult.empty());
        String result = testableOpenAIClient.getConfig("nonExistingConfig", "defaultValue");
        assertEquals("defaultValue", result, "Should return default value when config is not found");
    }

    @Test
    void testGetIntegerConfig() {
        Config integerConfig = new Config();
        integerConfig.setContent("1234");
        DataResult<Config> integerConfigResult = DataResult.of(integerConfig);

        when(mockConfigService.find(anyString())).thenReturn(integerConfigResult);

        Integer result = testableOpenAIClient.getIntegerConfig("someIntegerConfig", null);
        assertEquals(1234, result, "Should return the correct integer value");
    }

    @Test
    void testMaskApiKey() {
        String masked = testableOpenAIClient.maskApiKey("1234567890abcdef");
        assertEquals("1234****90abcdef", masked, "API key should be masked correctly");
    }


}
