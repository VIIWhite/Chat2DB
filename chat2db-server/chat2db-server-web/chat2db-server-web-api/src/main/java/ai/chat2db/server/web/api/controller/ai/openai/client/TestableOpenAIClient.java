
package ai.chat2db.server.web.api.controller.ai.openai.client;

import ai.chat2db.server.domain.api.model.Config;
import ai.chat2db.server.domain.api.service.ConfigService;
import com.unfbx.chatgpt.OpenAiStreamClient;
import com.unfbx.chatgpt.constant.OpenAIConst;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.Collections;
import java.util.List;

/**
 * @author jipengfei
 * @version : OpenAIClient.java
 */
@Slf4j
public class TestableOpenAIClient {

    public static final String OPENAI_KEY = "chatgpt.apiKey";

    /**
     * OPENAI API host
     */
    public static final String OPENAI_HOST = "chatgpt.apiHost";

    /**
     * proxy IP
     */
    public static final String PROXY_HOST = "chatgpt.proxy.host";

    /**
     * proxy port
     */
    public static final String PROXY_PORT = "chatgpt.proxy.port";

    private static OpenAiStreamClient openAiStreamClient;
    private ConfigService configService;

    public OpenAiStreamClient getClient() {
        if (openAiStreamClient == null){
            refreshClient();
        }
        return openAiStreamClient;
    }

    public void refreshClient() {
        String apiHost = getConfig(OPENAI_HOST, OpenAIConst.OPENAI_HOST);
        String apiKey = getConfig(OPENAI_KEY, null);

        Proxy proxy = null;
        String proxyHost = getConfig(PROXY_HOST, null);
        Integer proxyPort = getIntegerConfig(PROXY_PORT, null);
        if (proxyHost != null && proxyPort != null) {
            proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
        }

        OkHttpClient okHttpClient = proxy != null ?
                new OkHttpClient.Builder().proxy(proxy).build() :
                new OkHttpClient.Builder().build();

        List<String> apiKeys = apiKey != null ? Collections.singletonList(apiKey) : Collections.emptyList();

        openAiStreamClient = createClient(apiHost, apiKeys, okHttpClient);
    }

    public OpenAiStreamClient createClient(String apiHost, List<String> apiKeys, OkHttpClient okHttpClient) {
        return OpenAiStreamClient.builder()
                .apiHost(apiHost)
                .apiKey(apiKeys)
                .okHttpClient(okHttpClient)
                .build();
    }

    public void initializeConfigService(ConfigService configService) {
        this.configService = configService;
    }

    public String getConfig(String key, String defaultValue) {
        Config config = configService.find(key).getData();
        return config != null ? config.getContent() : defaultValue;
    }

    public Integer getIntegerConfig(String key, Integer defaultValue) {
        String value = getConfig(key, null);
        return value != null ? Integer.parseInt(value) : defaultValue;
    }


    public String maskApiKey(String input) {
        if (input == null) {
            return input;
        }

        StringBuilder maskedString = new StringBuilder(input);
        for (int i = input.length() / 4; i < input.length() / 2; i++) {
            maskedString.setCharAt(i, '*');
        }
        return maskedString.toString();
    }

}
