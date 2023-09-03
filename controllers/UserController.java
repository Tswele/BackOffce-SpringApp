package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.ResetPassword;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateUserRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformUpdateUserRequest;
import za.co.wirecard.channel.backoffice.models.User;
import za.co.wirecard.channel.backoffice.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/users")
public class UserController {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger(UserService.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_VIEW')")
    @GetMapping("")
    public ResponseEntity<?> getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int limit, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.getUsers(page, limit, httpServletRequest));
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getUsers(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.getUser(id, httpServletRequest));
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_CREATE')")
    @PostMapping("")
    public ResponseEntity<?> addUser(@RequestBody User user, HttpServletRequest httpServletRequest) {
        userService.createUser(user, httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable long id, @RequestBody User user, HttpServletRequest httpServletRequest) {
        userService.updateUser(id, user, httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_CREATE')")
    @GetMapping("/activate/{id}")
    public ResponseEntity<?> resendActivation(@PathVariable long id, HttpServletRequest httpServletRequest) {
        userService.resendActivation(id, httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_CREATE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id, HttpServletRequest httpServletRequest) {
        userService.deleteUser(id, httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody @Valid ResetPassword resetPassword, HttpServletRequest httpServletRequest) {
        userService.resetPassword(resetPassword, httpServletRequest);
        return ResponseEntity.ok().build();
    }

}
