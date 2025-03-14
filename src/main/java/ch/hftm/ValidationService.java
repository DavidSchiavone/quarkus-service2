package ch.hftm;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.*;

@ApplicationScoped
public class ValidationService {

    @Inject
    AiService aiService;

    @Incoming("validate-blog-request")
    @Outgoing("validate-blog-response")
    public Uni<ValidationResponse> processValidation(Blog request) {
        System.out.println( "test1");
        return Uni.createFrom().item(() -> {
            boolean isValid = validateText(request.title(), request.content());
            return new ValidationResponse(request.blogId(), isValid);
        });
    }

    private boolean validateText(String title, String content) {
        return !title.contains("badword") && !content.contains("badword");
    }

    @Incoming("generate-blog-request")
    public Uni<Blog> generateBlog(GenerateBlogRequest blogRequest)
    {
        String blogContent = aiService.generateBlog(blogRequest.topic(), 20);
        return null;
    }
}
