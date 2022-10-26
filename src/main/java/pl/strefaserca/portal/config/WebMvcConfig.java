package pl.strefaserca.portal.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class WebMvcConfig {

    /**
     * thymeleaf config
     */
    @Bean
    public SpringResourceTemplateResolver externalTemplateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("file:/home/tomek/Documents/StrefaHtml/");
//        templateResolver.setPrefix("https://strefa-bucket.s3.eu-central-1.amazonaws.com/");
        file:https://strefa-bucket.s3.eu-central-1.amazonaws.com/
//        templateResolver.setPrefix("file:/home/kasiazen/Documents/StrefaHtml/");
//        templateResolver.setPrefix("file:/volume1/web/StrefaHtml/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(0);
        templateResolver.setCheckExistence(true);
        templateResolver.setCacheable(true);   //set to false to be able to see changes to thymeleaf immediately without restarting app

        return templateResolver;
    }

    /**
     * validation
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource
                = new ReloadableResourceBundleMessageSource();

        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * override default  async executor with threadPoolTaskExecutor
     */
    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor() {
        return new ThreadPoolTaskExecutor();
    }

}
