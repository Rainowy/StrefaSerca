package pl.strefaserca.portal.service;

import org.springframework.stereotype.Service;
import pl.strefaserca.portal.model.dto.TestimonialDto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class TestimonialService {

    private List<TestimonialDto> testimonials = Arrays.asList(
            new TestimonialDto("Jestem po wszczepieniu stymulatora serca, u Pani doktor byłem już 6 raz. Przy każdej wizycie mam przeprowadzone szczegółowe badania oraz dostaję instrukcję do dalszego leczenia. Jestem bardzo zadowolony i polecam Panią Doktor.", "- Józef, 76 lat", "16 GRUDNIA 2019"),
            new TestimonialDto("Będąc osiemdziesięciolatkiem od roku leczę się u Pani dr Katarzyny. Przeprowadzone ostatnio szczegółowe badania wykazały poprawę mojego zdrowia, co świadczy o skuteczności leczenia. Ponadto chciałbym dodać, że życzliwy i empatyczny stosunek do pacjentów stwarza miłą atmosferze w czasie wizyty.", "- Pacjent", "10 Grudnia 2019"),
            new TestimonialDto("Test wykonano w bardzo przyjemnej atmosferze. Pani kardiolog wytłumaczyła i odpowiedziała na moje pytania rzeczowo i na temat. Życzę każdemu takiej wizyty.", "- Pacjent", "8 Września 2019"),
            new TestimonialDto("Pani doktor zrobiła na mnie dobre wrażenie swoją osobą i spokojnym usposobieniem. Bardzo profesjonalnie wykonuje test wysiłkowy i udziela cennych porad , mnie skutecznie przekonała do poddania się zabiegowi koronarografii. Gorąco polecam.", "- Pacjent", "10 Sierpnia 2019"),
            new TestimonialDto("Starannie przeprowadzony wywiad, rzetelne badania, doskonała diagnoza, wyczerpujące wyjaśnienia, świetny kontakt z pacjentem, ponadnormatywne zaangażowanie w organizację badań dodatkowych. Kompetentna i warta polecenia.", "- Pacjent", "11 Października 2018"),
            new TestimonialDto("Pani doktor dokładnie mnie przebadała gdy przyjechałem do niej na wizytę z problemem kołatania serca. Przeprowadziła badanie echo sondą, zebrała dokładne informacje na temat dolegliwości, z uwagą odniosła się do wszystkich przedstawionych informacji. Pani doktor ma bardzo dobry kontakt z pacjentem, i dużą wiedzę medyczną.", "- Pacjent", "28 Marca 2017"),
            new TestimonialDto("Bardzo dziękuję Pani doktor Czarneckiej za pomoc - zlecenie właściwego badania i trafne odczytanie EKG ( choroba Brugadów ). Ta młoda sympatyczna lekarka nie \"olała\" pacjenta, młodego człowieka i jego dolegliwości, podjęła właściwą decyzję - szpital. Już był w Miejskim Szpitalu i czeka go wizyta w Ochojcu. Życzę Pani Doktor aby po wielu latach pracy w tym zawodzie była nadal dokładną i czujną lekarka, być może to, że chciała pomyśleć nad moim synem uratuje mu zdrowie i życie. Dziękuję ;)", "- Lucyna W.", ""),
            new TestimonialDto("Mogę powiedzieć tylko to że dzięki Pani Doktor dowiedziałam się co mi jest, teraz jestem już pod opieką onkologa, bardzo dziękuje Pani Doktor, wspaniały człowiek jako lekarz, najlepszy jakiego mogłam spotkać na swej drodze, jeszcze raz dziękuje Pani Doktor.", "- Bożena", "25 Lipca 2017"));

    public List<TestimonialDto> allTestimonials() {
        return testimonials;
    }

    public List<TestimonialDto> rotatedTestimonials() {
        Collections.rotate(testimonials,2);
        List<TestimonialDto> shortened = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            shortened.add(testimonials.get(i));
        }
        return shortened;
    }
}
