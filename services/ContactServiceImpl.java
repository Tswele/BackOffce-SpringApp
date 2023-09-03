package za.co.wirecard.channel.backoffice.services;

import com.sun.xml.bind.v2.schemagen.xmlschema.Any;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateContactRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.UpdateContactRequestById;
import za.co.wirecard.channel.backoffice.exceptions.ContactException;
import za.co.wirecard.channel.backoffice.exceptions.EmailAlreadyInUseException;
import za.co.wirecard.channel.backoffice.models.Contact;

@Service

public class ContactServiceImpl implements ContactService {

    @Value("${api.contactmanagement.url}")
    private String contactManagementUrl;

    @Value("${api.contactmanagement.primaryContactUrl}")
    private String primaryContactUrl;

    private final RestTemplate restTemplate;
    private final UtilityService utilityService;

    private static final Logger logger = LogManager.getLogger(ContactService.class);

    public ContactServiceImpl(RestTemplateBuilder restTemplateBuilder, UtilityService utilityService) {
        this.restTemplate = restTemplateBuilder.build();
        this.utilityService = utilityService;
    }

    @Override
    public void createContact(PlatformCreateContactRequest contact) throws EmailAlreadyInUseException {
        String url = contactManagementUrl;
        logger.info("Add Contact User " + " URL " + " CONTACT " + contact);
        try {
            restTemplate.postForEntity(url, new HttpEntity<>(contact, utilityService.getHttpHeaders()), Any.class);
        } catch (RestClientResponseException e) {
            logger.info("CREATE_CONTACT_ERROR: " + e.getResponseBodyAsString());
            if (HttpStatus.valueOf(e.getRawStatusCode()) == HttpStatus.CONFLICT) {
                throw new EmailAlreadyInUseException("Email already in use", HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
            }
            throw new EmailAlreadyInUseException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void editContact(long contactId, UpdateContactRequestById contact) throws ContactException {
        String url = primaryContactUrl + "/" + contactId;
        logger.info("Edit Contact " + " URL " + url + " Contact " + contactId);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(contact, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            logger.info("EDIT_CONTACT_ERROR: " + e.getResponseBodyAsString());
            throw new ContactException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void deleteContact(long contactId) throws ContactException {
        String url = contactManagementUrl + "/" + contactId;
        logger.info("Delete Contact " + " URL " + url + " Contact " + contactId);
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(null, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            logger.info("DELETE_CONTACT_FAILED: " + e.getResponseBodyAsString());
            throw new ContactException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public Contact getContact(long merchantId) throws ContactException {
        String url = primaryContactUrl + "/" + merchantId;
        logger.info("Get Contact " + " URL " + url + " Contact " + merchantId);
        ResponseEntity<Contact> contact;
        try {
            contact = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), Contact.class);
        } catch (RestClientResponseException e) {
            throw new ContactException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return contact.getBody();
    }

    @Override
    public Page<Contact> getContacts(long merchantId) throws ContactException {
        String url = contactManagementUrl + "/contacts/" + merchantId;
        ParameterizedTypeReference<Page<Contact>> responseType = new ParameterizedTypeReference<Page<Contact>>() {
        };
        ResponseEntity<Page<Contact>> contacts;
        try {
            logger.info("Get all Contacts for Merchant " + " URL " + url + " Contact " + merchantId);
            contacts = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
        } catch (RestClientResponseException e) {
            throw new ContactException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Contacts Object that gets returned" + contacts.getBody());
        return contacts.getBody();
    }
}
