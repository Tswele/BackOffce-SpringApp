package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.BackOfficeDefaultGroup;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateGroupRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformUpdateGroupRequest;
import za.co.wirecard.channel.backoffice.models.Permission;
import za.co.wirecard.channel.backoffice.services.GroupService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/groups")
public class GroupController {

    private final GroupService groupService;

    private static final Logger logger = LogManager.getLogger(GroupController.class);

    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERGROUPS_VIEW') || @sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_VIEW')")
    @GetMapping("")
    public ResponseEntity<?> getGroups(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int limit, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(groupService.getGroups(page, limit, httpServletRequest));
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERGROUPS_VIEW') || @sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_VIEW')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllGroups(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(groupService.getAllGroups(httpServletRequest));
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERGROUPS_VIEW') || @sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERS_VIEW')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getGroup(@PathVariable long id, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(groupService.getGroup(id));
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERGROUPS_CREATE')")
    @PostMapping("")
    public ResponseEntity<?> addGroup(@RequestBody PlatformCreateGroupRequest group, HttpServletRequest httpServletRequest) {
        groupService.createGroup(group, httpServletRequest);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("/default-group")
//    public ResponseEntity<?> createMerchantDefaultAdminGroup(@RequestBody BackOfficeDefaultGroup group, HttpServletRequest httpServletRequest) {
//        ArrayList<Permission> permissions = new ArrayList<>();
//        logger.info("permissions ID's: " + permissions);
//        BackOfficeDefaultGroup platformGroup = new BackOfficeDefaultGroup(group.getMerchantId(), group.getCreatedBy(), group.getDescription());
//        platformGroup.setCode(group.getName());
//        platformGroup.setName(group.getCode());
//        return ResponseEntity.ok(groupService.createMerchantDefaultAdminGroup(group, httpServletRequest));
//    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERGROUPS_UPDATE')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateGroup(@PathVariable long id, @RequestBody PlatformUpdateGroupRequest group, HttpServletRequest httpServletRequest) {
        groupService.updateGroup(id, group, httpServletRequest);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'USERGROUPS_CREATE')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGroup(@PathVariable long id, HttpServletRequest httpServletRequest) {
        groupService.deleteGroup(id);
        return ResponseEntity.ok().build();
    }
}
