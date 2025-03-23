package ch.hftm.blog;

import ch.hftm.blog.messaging.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.reactive.messaging.*;

import java.util.List;

@ApplicationScoped
public class ValidationService {

    @Inject
    AiService aiService;

    @Inject
    @Channel("generate-blog-response")
    Emitter<GenerateBlogResponse> generateBlogEmitter;


    @Channel("additional-information-response")
    Emitter<AdditionalInformationResponse> additionalInformationEmitter;

    @Incoming("additional-information-request")
    public void processValidation(AdditionalInformationsRequest request) {
        AdditionalInformation additionalInformation = createAdditionalInformation(request.title(), request.content());
        additionalInformationEmitter.send(new AdditionalInformationResponse(request.blogId(), additionalInformation));
    }

    private AdditionalInformation createAdditionalInformation(String title, String content) {
        boolean valid = aiService.validateBlog(title, content);
        String summary = null;
        SEOInfo seoInfo = null;
        List<String> tags = List.of();

        if (valid) {
            summary = aiService.createSummary(title, content);
            seoInfo = aiService.generateSEO(title, content);
            tags = aiService.generateTags(title, content).stream()
                    .map(String::trim)
                    .toList();
        }

        return new AdditionalInformation(valid, summary, seoInfo, tags);
    }

    @Incoming("generate-blog-request")
    public void generateBlog(GenerateBlogRequest blogRequest)
    {
        Blog blog = aiService.generateBlog(blogRequest.topic(), blogRequest.keywords(), 10);
        AdditionalInformation additionalInformation = createAdditionalInformation(blog.title(), blog.content());

        generateBlogEmitter.send(new GenerateBlogResponse(blog, additionalInformation));
    }
}
