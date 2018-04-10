package codesquad.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import codesquad.domain.*;
import org.springframework.stereotype.Service;

import codesquad.dto.IssueDto;

@Service
public class IssueService {

	@Resource(name = "issueRepository")
	IssueRepository issueRepository;

	public Issue findById(Long id) {
		return issueRepository.findOne(id);
	}

	public Iterable<Issue> findAll() {
		return issueRepository.findByDeleted(false);
	}
	
	public Iterable<Answer> findAllAnswer(long id){
		return issueRepository.findOne(id).getAnswers();
	}

	public void create(User loginUser, IssueDto issueDto) {
		Issue newIssue = new Issue(issueDto.getTitle(), issueDto.getContents());
		newIssue.writeBy(loginUser);
		issueRepository.save(newIssue);
	}

	@Transactional
	public void update(User loginUser, long id, IssueDto issueDto) {
		Issue oldIssue = findById(id);
		oldIssue.update(loginUser, issueDto);
	}
	
	@Transactional
	public void delete(User loginUser, long id) {
		findById(id).delete(loginUser);
	}

	public void addAnswer(long id, Answer answer) {
		Issue issue = findById(id);
		issue.setAnswer(answer);
		issueRepository.save(issue);
	}

	public void addAttachment(Attachment attachment, long id) {
		Issue issue = findById(id);
		issue.setAttachments(attachment);
		issueRepository.save(issue);
	}
}
