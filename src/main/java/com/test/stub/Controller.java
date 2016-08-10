package com.test.stub;

import com.google.common.base.Splitter;
import com.google.common.io.Files;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {

    @Autowired
    protected Config config;

    @RequestMapping(value = "/item", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Response test(@RequestParam(value = "id", required = false) String id) {

        Response r = new Response();

        if (!StringUtils.isEmpty(id)) {
            try {
                List<String> lines = Files.readLines(new File(config.getConfigDir() + "/" + id + ".txt"), Charset.forName("UTF-8"));
                if (lines != null && lines.size() > 0) {
                    for (String line : lines) {
                        List<String> data = Splitter.on(":").trimResults().splitToList(line);
                        if (data.size() > 1 && !StringUtils.isEmpty(data.get(1))) {
                            String key = data.get(0);
                            String value = data.get(1);
                            if ("item_code".equals(key)) {
                                r.getResponse().setItem_code(value);
                            } else if ("name".equals(key)) {
                                r.getResponse().setName(value);
                            } else if ("price".equals(key)) {
                                r.getResponse().setPrice(value);
                            } else if ("count".equals(key)) {
                                r.getResponse().setCount(value);
                            } else if ("average".equals(key)) {
                                r.getResponse().setAverage(value);
                            }
                        }
                    }
                    return r;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        r.getResponse().setMessage("ItemNotFound");
        return r;
    }
}
