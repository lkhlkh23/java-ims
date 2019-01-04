package codesquad.dto;

import codesquad.domain.Content;
import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;

import javax.persistence.Embedded;
import javax.validation.Valid;

public class IssueDto {

    @Embedded @Valid
    private Content content;

    private User writer;

    private Milestone milestone;

    public IssueDto() {

    }

    public IssueDto(Content content) {
        this.content = content;
    }

    public IssueDto(Content content, User writer) {
        this(content);
        this.writer = writer;
    }

    public IssueDto(Content content, User writer, Milestone milestone) {
        this(content, writer);
        this.milestone = milestone;
    }

    public Issue _toIssue() {
        return new Issue(this.content, this.writer);
    }

    public Issue _toIssue(User writer) {
        this.writer = writer;
        return new Issue(content, this.writer);
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Content getContent() {
        return content;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "content=" + content +
                ", writer=" + writer +
                '}';
    }
}
