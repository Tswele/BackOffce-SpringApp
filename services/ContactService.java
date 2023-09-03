package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateContactRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.UpdateContactRequestById;
import za.co.wirecard.channel.backoffice.exceptions.ContactException;
import za.co.wirecard.channel.backoffice.exceptions.EmailAlreadyInUseException;
import za.co.wirecard.channel.backoffice.models.Contact;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface ContactService {
    void createContact(PlatformCreateContactRequest contact) throws EmailAlreadyInUseException;

    //void editContact(long contactId, UpdateContactRequestById contact, HttpServletRequest request);

    void editContact(long contactId, UpdateContactRequestById contact) throws ContactException;

    //void deleteContact(long contactId, HttpServletRequest request);

   // Contact getContact(long merchantId, HttpServletRequest request);

    //List<Contact> getContacts(long merchantId, HttpServletRequest request);

    void deleteContact(long contactId) throws ContactException;

    Contact getContact(long merchantId) throws ContactException;

    Page<Contact> getContacts(long merchantId) throws ContactException;
}
