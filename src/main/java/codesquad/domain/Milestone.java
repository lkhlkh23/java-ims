package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static support.web.TimeFormat.NORMAL;
import static support.web.TimeFormat.getFormat;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Embedded
    private Issues issues = new Issues();

    public Milestone() {
    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getId() {
        return super.getId();
    }

    public String getSubject() {
        return subject;
    }

    public String getStartDate() {
        return startDate.format(DateTimeFormatter.ofPattern(getFormat(NORMAL)));
    }

    public String getEndDate() {
        return endDate.format(DateTimeFormatter.ofPattern(getFormat(NORMAL)));
    }

    public int getOpenIssueCount() {
        return issues.getOpenCount();
    }

    public int getCloseIssueCount() {
        return issues.getCloseCount();
    }

    public Milestone addIssue(Issue issue) {
        issues.add(issue);
        return this;
    }
}
