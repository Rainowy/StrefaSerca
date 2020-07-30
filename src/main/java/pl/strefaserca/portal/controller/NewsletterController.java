package pl.strefaserca.portal.controller;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.strefaserca.portal.service.NewsLetterService;

import java.util.Locale;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/newsletter")
public class NewsletterController {

    private NewsLetterService newsLetterService;
    private MessageSource messages;

    @PostMapping("/request")
    public @ResponseBody
    void requestNewsletter(@RequestParam("newsletter") String newsLetter) {
        if (!newsLetter.isEmpty()) {
            newsLetterService.sendConfirmationMail(newsLetter);
        }
    }

    @GetMapping("/confirm")
    public String confirmNewsletterRequest
            (WebRequest request, @RequestParam("token") String token, RedirectAttributes redirectAttributes) {

        Locale locale = request.getLocale();
        String newsLetterConfirmed = messages.getMessage("auth.message.newsLetterConfirmed", null, locale);
        String invalidEmail = messages.getMessage("auth.message.invalidEmail", null, locale);

        Optional<String> confirmationToken = newsLetterService.getConfirmationToken(token);
        confirmationToken.ifPresentOrElse(p -> redirectAttributes.addFlashAttribute("message", newsLetterConfirmed),
                () -> redirectAttributes.addFlashAttribute("message", invalidEmail));

        return "redirect:/newsletter/message";
    }

    @GetMapping("/message")
    public ModelAndView registrationMessage(@ModelAttribute("message") String message) {
        return new ModelAndView("newsletter", "message", message);
    }
}
