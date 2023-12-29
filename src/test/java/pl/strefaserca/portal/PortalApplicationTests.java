package pl.strefaserca.portal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.slf4j.event.LoggingEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.NestedServletException;
import pl.strefaserca.portal.controller.ArticleController;
import pl.strefaserca.portal.controller.HomeController;
import pl.strefaserca.portal.email.OnContactQuestionEvent;
import pl.strefaserca.portal.service.ArticleService;
import pl.strefaserca.portal.service.CaptchaService;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(controllers = ArticleController.class)
class PortalApplicationTests {

//    @LocalServerPort
//    private int port;


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired


    @MockBean
    private ArticleService articleService;

//	@MockBean
//	CaptchaService captchaService;
//
//	@MockBean
//	ContactService contactService;

//
//	@Autowired
//    private TestRestTemplate restTemplate;


    @SneakyThrows
    @Test
    public void ifRedirectsToHtmlReturns200() {

        //Test checks if URL redirects to html.
        mockMvc.perform(get("/home/login")
//			.contentType("application/json"))
                .contentType("text/html"))
                .andExpect(status().isOk());    //isOk = 200
        mockMvc.perform(get("/home/certificates")
                .contentType("text/html"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/home/services")
                .contentType("text/html"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/home/contact")
                .contentType("text/html"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/home/blog")
                .contentType("text/html"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/home/about")
                .contentType("text/html"))
                .andExpect(status().isOk());
        //Above tests doesnt contain testimonials becouse this MdelAndView adds method result
    }


    //	Test sprawdza
    @Test
    void whenNullCaptchaTokenReturn400() throws Exception {
        OnContactQuestionEvent event = new OnContactQuestionEvent("fake@gmail.com");

        String token = null;

        mockMvc.perform(post("/home/askQuestion")
                .contentType("application/json")
                .param("email", "kasia@wp.pl")
                .param("name", "kasia")
                .param("phone", "660537686")
                .param("textarea", "hello Kasiaczek")
                .param("token", token)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isBadRequest());

		System.out.println("EVENT " + event);
    }

    @Test
    void whenCaptchaTokenDuplicatedReturnException() {

        String duplicatedToken = "03AGdBq24bEFLKG8W9EDV2BwqHuQMbuFCGF_btK4mGXCMQ4W-p3sgHAp0G7lRuYvfVDbi26UBBbjVa7JSo6LtWBwTqKEl372Pw7EyotmutJT8VojZe8GgDuvo9vGwcwkmChP-eLtfeZvlSrkS2pGbmev0DeOp4Tn6WxWSHcIQzKJgNn4LScz_YriW3PvciNojxkbgp3hdeORVNHUBStB5h3WvhSBGjUwpsj5F5OJ8OXy_-en7CdDadCy8FTxRiRPH1UDIJM5ZR-tzWr_95N8U50Yds-peeNX9AZrkr5kKpYZMG2D7gOAKLeaHSBpqhR85qn1syerRPxUSAEVJw5dT4GZethj7kxPbnch9G5lGQnCx9hG080hKWhuR3c1HKCNxEzTT_I0LxCslMYYF5PERjjzgz-2Y_-w-R5NvKzCe3Z_vRVBITayMssWeC7K-mkUGkx3XQf0XTdkeH";

        NestedServletException nestedServletException = assertThrows(NestedServletException.class, () -> trySerialization(duplicatedToken));

        String expectedMessage = "TimeoutOrDuplicate";
        String actualMessage = nestedServletException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void whenCaptchaTokenNotValidReturnException() {

        String invalidToken = "12345678910";

        NestedServletException nestedServletException = assertThrows(NestedServletException.class, () -> trySerialization(invalidToken));

        System.out.println(nestedServletException);

        String expectedMessage = "InvalidResponse";
        String actualMessage = nestedServletException.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

//    @Test
//	void whenValidInput_thenMapsToBusinessModel() throws Exception {
//
//		OnContactQuestionEvent event = new OnContactQuestionEvent("fake@gmail.com");
//
//		String duplicatedToken = "03AGdBq24bEFLKG8W9EDV2BwqHuQMbuFCGF_btK4mGXCMQ4W-p3sgHAp0G7lRuYvfVDbi26UBBbjVa7JSo6LtWBwTqKEl372Pw7EyotmutJT8VojZe8GgDuvo9vGwcwkmChP-eLtfeZvlSrkS2pGbmev0DeOp4Tn6WxWSHcIQzKJgNn4LScz_YriW3PvciNojxkbgp3hdeORVNHUBStB5h3WvhSBGjUwpsj5F5OJ8OXy_-en7CdDadCy8FTxRiRPH1UDIJM5ZR-tzWr_95N8U50Yds-peeNX9AZrkr5kKpYZMG2D7gOAKLeaHSBpqhR85qn1syerRPxUSAEVJw5dT4GZethj7kxPbnch9G5lGQnCx9hG080hKWhuR3c1HKCNxEzTT_I0LxCslMYYF5PERjjzgz-2Y_-w-R5NvKzCe3Z_vRVBITayMssWeC7K-mkUGkx3XQf0XTdkeH";
//
//		mockMvc.perform(post("/home/askQuestion")
//
//				.contentType("application/json")
//				.param("email", "kasia@wp.pl")
//				.param("name", "kasia")
//				.param("phone", "660537686")
//				.param("textarea", "hello Kasiaczek")
//				.param("token", duplicatedToken)
//				.content(objectMapper.writeValueAsString(event)))
//				.andExpect(status().isBadRequest());
//
//		System.out.println(event.toString());

//	}



    private ResultActions trySerialization(String token) throws Exception {

        OnContactQuestionEvent event = new OnContactQuestionEvent("fake@gmail.com");

        ResultActions resultActions = mockMvc.perform(post("/home/askQuestion")

                .contentType("application/json")
                .param("email", "kasia@wp.pl")
                .param("name", "kasia")
                .param("phone", "660537686")
                .param("textarea", "hello Kasiaczek")
                .param("token", token)
                .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk());

        return resultActions;
    }
}