package za.co.wirecard.channel.backoffice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.services.PermissionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/permissions")

public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'PERMISSION_VIEW')")
    @GetMapping("")
    public ResponseEntity<?> getPermissions(HttpServletRequest httpServletRequest, HttpSession httpSession) {
        return ResponseEntity.ok(permissionService.getPermissions(httpServletRequest));
    }

}
