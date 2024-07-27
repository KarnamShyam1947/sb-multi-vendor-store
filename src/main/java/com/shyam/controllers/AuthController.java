package com.shyam.controllers;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shyam.dto.CustomerDTO;
import com.shyam.dto.VenderDTO;
import com.shyam.entities.UserEntity;
import com.shyam.services.EmailService;
import com.shyam.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final EmailService emailService;

    private final PasswordEncoder passwordEncoder;
    
    @GetMapping("/customer-register")
    public String customerRegister(@ModelAttribute("customerDto") CustomerDTO customer) {
        return "auth/customer-register";
    }
    
    @PostMapping("/customer-register")
    public String processCustomerRegister(@Valid @ModelAttribute("customerDto") CustomerDTO customer, BindingResult result) {

        if (result.hasErrors()) 
            return "auth/customer-register";

        else if (!customer.getPassword().equals(customer.getConformPassword())) {
            result.rejectValue("conformPassword", "error.conformPassword", "password and conform password fields must match");
            return "auth/customer-register";
        }

        else if(userService.getUserByEmail(customer.getEmail()) != null){
            result.rejectValue("email", "error.email", "email already in use");
            return "auth/customer-register";
        }

        userService.insertCustomer(customer);
        return "redirect:/sign-in?success";
    }
    
    @GetMapping("/register-vender")
    public String venderRegister(@ModelAttribute("venderDto") VenderDTO vender) {
        return "auth/vender-register";
    }
    
    @PostMapping("/register-vender")
    public String processVenderRegister(@Valid @ModelAttribute("venderDto") VenderDTO vender, BindingResult result) {
        if (result.hasErrors()) 
        return "auth/vender-register";

        else if (!vender.getPassword().equals(vender.getConformPassword())) {
            result.rejectValue("conformPassword", "error.conformPassword", "password and conform password fields must match");
            return "auth/vender-register";
        }

        else if(userService.getUserByEmail(vender.getEmail()) != null){
            result.rejectValue("email", "error.email", "email already in use");
            return "auth/vender-register";
        }

        else if(userService.getByCompanyName(vender.getCompanyName()) != null) {
            result.rejectValue("companyName", "error.companyName", "This company already registered");
            return "auth/vender-register";
        }
        
        userService.insertVender(vender);
        return "redirect:/sign-in";
    }

    @GetMapping("/sign-in")
    public String signIn() {
        return "auth/sign-in";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword() {
        return "auth/forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam(name = "email") String email, Model model, HttpSession session) {
        
        if ("".equals(email)) {
            model.addAttribute("error", "email is required");
            return "auth/forgot-password";
        }
        
        UserEntity user = userService.getUserByEmail(email);
        if(user == null) {
            model.addAttribute("error", "email is not register");
            return "auth/forgot-password";
        }
    
        user.setUniqueToken(UUID.randomUUID().toString());
        user.setExpireTime(LocalDateTime.now().plusMinutes(1));

        UserEntity updateUser = userService.updateUser(user);

        emailService.sendForgotPasswordEmail(updateUser);
        session.setAttribute("msg", "email sent. please check your inbox");
        return "redirect:/sign-in";
    }

    @GetMapping("/set-password/{token}")
    public String setPassword(@PathVariable String token, HttpSession session) {
        UserEntity user = userService.validateToken(token);

        if(user == null) {
            session.setAttribute("msg", "invalid user validation token");
            return "redirect:/sign-in";
        }

        else if (LocalDateTime.now().isAfter(user.getExpireTime())) {
            session.setAttribute("msg", "user token expired. make request for new one");
            return "redirect:/sign-in";
        }

        session.setAttribute("user", user);
        return "redirect:/process-set-password";
    }

    @GetMapping("/process-set-password")
    public String processSetPassword(HttpSession session) {
        if (session.getAttribute("user") == null) {
            session.setAttribute("msg", "400 : Bad Request");
            return "redirect:/sign-in";
        }
        return "auth/set-password";
    }
    
    @PostMapping("/process-set-password")
    public String processSetPasswordPost(@RequestParam("pass1") String pass1,
                                         @RequestParam("pass2") String pass2,
                                         HttpSession session,
                                         Model model) {

        if (!pass1.equals(pass2)) {
            model.addAttribute("error", "ok");
            return "auth/set-password";
        }
        
        UserEntity user = (UserEntity) session.getAttribute("user");
        session.removeAttribute("user");

        user.setPassword(passwordEncoder.encode(pass1));
        user.setUniqueToken(null);
        user.setExpireTime(null);
        userService.updateUser(user);

        session.setAttribute("msg", "password updated");
        return "redirect:/sign-in";
    }

}
