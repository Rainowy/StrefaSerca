package pl.strefaserca.portal.controller;

import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SSL {
    public SSL() {
    }

    @GetMapping(
            value = {"/.well-known/acme-challenge/xmYR2day1UysCZ0kohcvRB-lu-7MI57f8GsyrM98MV4"},
            produces = {"application/octet-stream"}
    )
    @ResponseBody
    public byte[] getFile() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/xmYR2day1UysCZ0kohcvRB-lu-7MI57f8GsyrM98MV4.txt");
        return IOUtils.toByteArray(in);
    }
}
