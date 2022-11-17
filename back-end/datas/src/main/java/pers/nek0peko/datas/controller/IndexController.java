package pers.nek0peko.datas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pers.nek0peko.datas.common.constant.Constants;

/**
 * IndexController
 *
 * @author nek0peko
 * @date 2022/11/17
 */
@Controller
@RequestMapping
public class IndexController {

    @GetMapping("/doc")
    public String doc() {
        return "redirect:http://" + Constants.DOMAIN + "/doc.html";
    }

}
