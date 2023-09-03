package za.co.wirecard.channel.backoffice.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateContactRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.UpdateContactRequestById;
import za.co.wirecard.channel.backoffice.services.ContactService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/clients/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping("")
    public ResponseEntity<?> createContact(@RequestBody PlatformCreateContactRequest contact) {
        contactService.createContact(contact);
        return ResponseEntity.ok().build();
    }
    @PutMapping("/{contactId}")
    public ResponseEntity<?> editContact(@PathVariable long contactId, @RequestBody UpdateContactRequestById contact, HttpServletRequest request) {
        contactService.editContact(contactId, contact);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{contactId}")
    public ResponseEntity<?> deleteContact(@PathVariable long contactId, HttpServletRequest request) {
        contactService.deleteContact(contactId);
        return ResponseEntity.ok().build();
    }
    // get primary contact of merchant details
    @GetMapping("/primary-contact/{merchantId}")
    public ResponseEntity<?> getContact(@PathVariable long merchantId, HttpServletRequest request) {
        return ResponseEntity.ok(contactService.getContact(merchantId));
    }
    @GetMapping("/{merchantId}")
    public ResponseEntity<?> getContacts(@PathVariable long merchantId, HttpServletRequest request) {
        contactService.getContacts(merchantId);
        return ResponseEntity.ok().build();
    }
}
