package ch.hftm.blog;

import ch.hftm.blog.messaging.Blog;
import ch.hftm.blog.messaging.SEOInfo;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
@RegisterAiService
public interface AiService {

    @SystemMessage( "Du bist ein professionller Blog Autor")
    @UserMessage("""
    Schreibe einen Blogpost zu folgendem Thema: {topic}.s
    Er soll {lines} Zeilen lang sein.
    Gib mir den Titel und Inhalt separat aus.
    """)
    Blog generateBlog(String topic, int lines);

    @SystemMessage("Du bist ein professionller Blog Autor")
    @UserMessage("""
            Schreibe mir eine kurze Zusammenfassung für folgenden Blog,
            welche sich für eine Übersicht eignet (maximal 2 Zeilen).
            Titel: {title}, Content: {content}.
            Gib mir nur den Inhalt der Zusammenfassung zurück.
            """)
    String createSummary(String title, String content);

    @SystemMessage("Du bist ein SEO optimierer")
    @UserMessage("""
            Erstelle mir für folgenden Blog einen SEO-optimierten title und meta-description
            Titel: {title}, Content: {content}
            Gib mir title und metadescription separat zurück
            """)
    SEOInfo generateSEO(String title, String content);


    @SystemMessage("Du bist ein professionller Blog Autor")
    @UserMessage("""
            Generiere mir maximal 5 Tags für den folgenden Blog
            Titel: {title}, Content: {content}.
            Gib mir nur die Tags ohne Aufzählungszeichen zurück.
            """)
    List<String> generateTags(String title, String content);

    @SystemMessage("Du bist ein professionller Blog Autor")
    @UserMessage("""
            Sag mir ob dieser Blog inhaltlich okay ist um gepostet zu werden.
            Titel: {title}, Content: {content}.
            Gib mir die Antwort als boolean zurück.
            """)
    boolean validateBlog(String title, String content);
}
