package pl.strefaserca.portal.service;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CardioService {

    /** List of available services */
    private List<String> services = new ArrayList<>(Arrays.asList(
            "Echokardiografia",
            "Holter_EKG",
            "Holter_RR",
            "Test_wysiłkowy",
            "Koronarografia",
            "Kontrola_stymulatora"
    ));

    public String nextService(String fileName) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).equals(fileName) && i != services.size() - 1) {
                return services.get(i + 1);
            }
        }
        return services.get(0);
    }

    public String prevService(String fileName) {
        for (int i = 0; i < services.size(); i++) {
            if (services.get(i).equals(fileName) && i != 0) {
                return services.get(i - 1);
            }
        }
        return services.get(services.size() - 1);
    }
}
