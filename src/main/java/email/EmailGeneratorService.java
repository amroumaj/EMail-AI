package email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class EmailGeneratorService {
    @Value("${gemini.api.key}")
    private String geminiAPIKey;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    public String generateReply(EmailRequest emailRequest) {
        String prompt = buildPrompt(emailRequest);
        Map<String, Object> requestBody = Map.of(
            "contents", new Object[] {
                    Map.of("parts", new Object[] {
                            Map.of("text", prompt)
                    })
            }
        );
    };

    private String buildPrompt(EmailRequest emailRequest) {
        StringBuilder prompt = new StringBuilder();
        prompt.append("Generate an email for the following request and do no t generate a subject");

        if(emailRequest.getTone() != null && !emailRequest.getTone().isEmpty()) {
            prompt.append("use a ").append(emailRequest.getTone()).append(" tone.");
        }

        prompt.append("\n original email: \n").append(emailRequest.getEmailContent());

        return prompt.toString();
    };
}
