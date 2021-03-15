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
            value = {"/.well-known/pki-validation/2CF6CE4CB27F75F213DE67C961635985.txt"},
            produces = {"application/octet-stream"}
    )
    @ResponseBody
    public byte[] getFile() throws IOException {
        InputStream in = this.getClass().getResourceAsStream("/2CF6CE4CB27F75F213DE67C961635985.txt");
        return IOUtils.toByteArray(in);
    }
}
