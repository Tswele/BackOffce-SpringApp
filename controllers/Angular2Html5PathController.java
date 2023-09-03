package za.co.wirecard.channel.backoffice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class Angular2Html5PathController {
    @RequestMapping(method = {RequestMethod.OPTIONS, RequestMethod.GET}, path = {"/login/**", "/home/**", "/activate-user/**", "/forgot-password/**", "/reset-password/**"})
    public String forwardAngularPaths() {
        return "forward:/index.html";
    }
}
