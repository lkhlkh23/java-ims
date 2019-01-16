package codesquad.web;

import codesquad.ApplicationConfigurationProp;
import codesquad.domain.Attachment;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.AvatarService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import support.domain.ErrorMessage;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/user")
public class ApiUserController {

    @Resource(name = "userService")
    private UserService userService;

    @Autowired
    private AvatarService attachmentService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = getLogger(ApiUserController.class);

    @PostMapping()
    public ResponseEntity create(@Valid UserDto user, MultipartFile file) throws IOException {
        Attachment avatar = attachmentService.createAvatar(file);
        User savedUser = userService.add(user, avatar);

        return new ResponseEntity<User>(savedUser, createHeader("/api/user/" + savedUser.getId()), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public UserDto show(@LoginUser User loginUser, @PathVariable long id) {
        User user = userService.findById(loginUser, id);
        return user._toUserDto();
    }

    @PutMapping(consumes = {"multipart/form-data"}, value = "/{id}")
    public ResponseEntity update(@LoginUser User loginUser, @PathVariable long id, @Valid UserDto updatedUser,
                                       MultipartFile file, HttpSession httpSession) throws IOException {
        Attachment avatar = attachmentService.createAvatar(file);
        User user = userService.update(loginUser, id, updatedUser, avatar);
        HttpSessionUtils.updateUserSession(httpSession, updatedUser);

        return new ResponseEntity<User>(user, createHeader("/"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid UserDto userDto, HttpSession httpSession) {
        User user = userService.login(userDto.getUserId(), userDto.getPassword());
        HttpSessionUtils.setSession(httpSession, user);

        return new ResponseEntity(user, createHeader("/"), HttpStatus.ACCEPTED);
    }

    public HttpHeaders createHeader(String location) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(location));

        return httpHeaders;
    }
}
