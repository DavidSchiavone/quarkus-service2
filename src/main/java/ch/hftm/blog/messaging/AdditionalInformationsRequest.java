package ch.hftm.blog.messaging;

public record AdditionalInformationsRequest(Long blogId, String title, String content) {
}
