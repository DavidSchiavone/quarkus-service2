package ch.hftm;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.*;

@ApplicationScoped
public class ValidationService {

    @Incoming("validate-blog-request")
    @Outgoing("validate-blog-response")
    public Uni<ValidationResponse> processValidation(ValidationRequest request) {
        return Uni.createFrom().item(() -> {
            boolean isValid = validateText(request.title(), request.content());
            return new ValidationResponse(request.blogId(), isValid);
        });
    }

    private boolean validateText(String title, String content) {
        return !title.contains("badword") && !content.contains("badword");
    }
}
