package codesquad.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Embeddable
public class Content {
    @Size(min = 5, max = 200)
    @Column(nullable = false)
    private String subject;

    @Size(min = 5, max = 1000)
    @Column(nullable = false)
    private String comment;

    public Content() {

    }

    public Content(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Content update(Content content) {
        this.subject = content.subject;
        this.comment = content.comment;
        return this;
    }

    @Override
    public String toString() {
        return "Content{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}