package pl.strefaserca.portal.service;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import pl.strefaserca.portal.email.OnNewsLetterRequestEvent;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NewsLetterService {

    private ApplicationEventPublisher eventPublisher;
    private HttpServletRequest request;
//    private Map<String, String> confirmationTokens;
//    private File newsLetter = new File("/home/tomek/Documents/StrefaHtml/newsletter.properties");

    public void sendConfirmationMail(String email) {
        String appUrl = request.getContextPath();
        eventPublisher.publishEvent(new OnNewsLetterRequestEvent(appUrl, request.getLocale(), email));
    }

    public void saveConfirmationToken(String mail, String token) {

//        Map<String, String> confirmationToken = new HashMap<>();
        Properties properties = new Properties();
        properties.setProperty(token,mail);

//        confirmationTokens.put(mail, token);
//        System.out.println(confirmationTokens);

//        try
//        {
//            Map<String, String> confirmationToken = confirmationTokens.entrySet().stream()
//                    .filter(map -> token.equals(map.getValue()))
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//            System.out.println("ZAPISUJEMY + " + confirmationToken);
//            FileOutputStream fos =
//                    new FileOutputStream(new File("/home/tomek/Documents/StrefaHtml/data.ser"));
//            ObjectOutputStream oos = new ObjectOutputStream(fos);
//            oos.writeObject(confirmationToken);
//            oos.close();
//            fos.close();
//            System.out.printf("Serialized HashMap data is saved in hashmap.ser");
//        }catch(IOException ioe)
//        {
//            ioe.printStackTrace();
//        }
//        Map<String, String> confirmationToken = confirmationTokens.entrySet().stream()
//                .filter(map -> token.equals(map.getValue()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));


//      confirmationTokens.entrySet().stream()
//                .filter(map -> token.equals(map.getValue()))
////                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//        .map(m -> )
        File file = new File("/home/tomek/Documents/StrefaHtml/newsletter.properties");

//        try (OutputStream output = new FileOutputStream("/home/tomek/Documents/StrefaHtml/dupa.properties")) {
        try (OutputStream output = new FileOutputStream(file,true)) {

            Properties prop = new Properties();
            prop.setProperty(token,mail);


            // save prop to project root folder
            prop.store(output, null);

            System.out.println(prop);

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public Map<String, String> getConfirmationToken(String token) {

        File file = new File("/home/tomek/Documents/StrefaHtml/newsletter.properties");

//        try (OutputStream output = new FileOutputStream("/home/tomek/Documents/StrefaHtml/dupa.properties")) {
        try (InputStream input = new FileInputStream(file)) {
            Properties prop = new Properties();
            // load a properties file
            prop.load(input);
            Optional<String> property = Optional.ofNullable(prop.getProperty(token));

            property.ifPresentOrElse(p -> System.out.println("jest taka " + p),() -> System.out.println("nie ma takiej"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        Properties properties = new Properties();
////        properties.putAll(confirmationToken);
//        for (Map.Entry<String,String> entry : confirmationToken.entrySet()) {
//            properties.put(entry.getKey(), entry.getValue());
//        }

//        try (OutputStream output = new FileOutputStream("/home/tomek/Documents/StrefaHtml/data.properties/dupa.properties")) {
//            Properties properties = new Properties();
////        properties.putAll(confirmationToken);
//            for (Map.Entry<String,String> entry : confirmationToken.entrySet()) {
//                properties.put(entry.getKey(), entry.getValue());
//            }
////            Properties prop = new Properties();
////
////            // set the properties value
////            prop.setProperty("db.url", "localhost");
////            prop.setProperty("db.user", "mkyong");
////            prop.setProperty("db.password", "password");
//
//            // save properties to project root folder
//            properties.store(output, null);
//
////            System.out.println(prop);
//
//        } catch (IOException io) {
//            io.printStackTrace();
//        }


//        System.out.println(properties);
//        try {
//            properties.store(new FileOutputStream("data.properties"),null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try(FileWriter output = new FileWriter("/home/tomek/Documents/StrefaHtml/data.properties")){
//            properties.store(output, "These are properties");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return null;
    }
}
