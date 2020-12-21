package com.proxy.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/")
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainController {
    private final OAuth2AuthorizedClientService clientService;

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("isDevMode", true);
        return "main";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/admin")
    public String main(Model model) {
        model.addAttribute("isDevMode", true);
        return "main";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/token")
    @ResponseBody
    public String getToken() {
        String accessToken = null;
        try {
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();
            OAuth2AuthenticationToken oauthToken =
                    (OAuth2AuthenticationToken) authentication;
            OAuth2AuthorizedClient client =
                    clientService.loadAuthorizedClient(
                            oauthToken.getAuthorizedClientRegistrationId(),
                            oauthToken.getName());

            accessToken = client.getAccessToken().getTokenValue();
        } catch (Exception e) {
            System.out.println("unAuthorized user");
        }
        return accessToken;
    }
}
