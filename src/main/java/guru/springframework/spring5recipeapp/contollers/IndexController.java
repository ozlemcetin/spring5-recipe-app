package guru.springframework.spring5recipeapp.contollers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jt on 6/1/17.
 */
@Slf4j
@Controller
public class IndexController {


    @RequestMapping({"", "/", "/index"})
    public String getIndexPage() {

        log.debug("IndexController > Returns Index Page.");
        return "index";
    }
}
