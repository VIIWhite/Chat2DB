package ai.chat2db.server.web.api.controller.ai.openai.client;

import com.unfbx.chatgpt.OpenAiStreamClient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class OpenAIClientTest {

    @Test
    public void maskApiKey_ShouldCorrectlyMaskTheKey() {
        String apiKey = "1234567890abcdef";
        String expectedMaskedKey = "1234****90abcdef";

        String maskedKey = OpenAIClient.maskApiKey(apiKey);

        assertEquals(expectedMaskedKey, maskedKey);
    }

    @Test
    public void maskApiKey_ShouldReturnNullForNullInput() {
        String apiKey = null;

        String maskedKey = OpenAIClient.maskApiKey(apiKey);

        assertEquals(apiKey, maskedKey, "Masking a null API key should return null.");
    }

    @Test
    public void maskApiKey_ShouldCorrectlyMaskShortKey() {
        // See if api key can be masked correctly if its length less than 8 characters
        String shortApiKey = "1234567";
        String expectedMaskedKey = "1**4567";

        String maskedKey = OpenAIClient.maskApiKey(shortApiKey);

        assertEquals(expectedMaskedKey, maskedKey);
    }

    @Test
    public void getInstance_ThrowsNullPointerException_IfApplicationContextIsNull() {
        // Calling getInstance when OPEN_AI_STREAM_CLIENT is null. Expect a NullPointerException
        assertThrows(NullPointerException.class, () -> {
            OpenAiStreamClient instance = OpenAIClient.getInstance();
        });
    }
}
