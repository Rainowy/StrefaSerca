package pl.strefaserca.portal.controller;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SSL {


    @GetMapping(
            value = {"/.well-known/acme-challenge/Yrv4JrVd-hNqhjAlTaK_yB-ngJDl7DIbxsVFRK7bBBg"},
            produces = {"application/octet-stream"}
    )
    @ResponseBody
    public byte[] getFile() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/Yrv4JrVd-hNqhjAlTaK_yB-ngJDl7DIbxsVFRK7bBBg.txt");
        return IOUtils.toByteArray(in);
    }
}
