package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.BasicAuthAcceptanceTest;
import support.test.UserFixture;

public class ApiAssigneeAcceptanceTest extends BasicAuthAcceptanceTest {

    @Test
    public void 담당자등록_로그인X_실패() {
        ResponseEntity<Void> responseEntity = template
                .postForEntity("/api/issues/1/assignees/1", new HttpEntity(new HttpHeaders()),Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 담당자등록_로그인O_본인X_실패X() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/assignees/1", new HttpEntity(new HttpHeaders()),Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void 담당자등록_본인O_로그인O_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate()
                .postForEntity("/api/issues/1/assignees/1", new HttpEntity(new HttpHeaders()),Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}