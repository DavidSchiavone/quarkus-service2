package ch.hftm;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;

@RegisterAiService
public interface AiService {

    @SystemMessage( "Du bist ein professionller Blog Autor")
    @UserMessage("Schreibe einen Blogpost zu folgendem Thema: {topic}. Er soll {lines} Zeilen lang sein")
    String generateBlog(String topic, int lines);
}
