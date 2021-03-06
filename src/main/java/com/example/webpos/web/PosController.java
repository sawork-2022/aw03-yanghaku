package com.example.webpos.web;

import com.example.webpos.biz.PosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PosController {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }


    private String refreshModel(Model model){
        model.addAttribute("products", posService.products());
        model.addAttribute("cart", posService.getCart());
        model.addAttribute("total", posService.total());
        return "index";
    }

    @GetMapping("/")
    public String pos(Model model) {
        return refreshModel(model);
    }

    @GetMapping("/add")
    public String add(Model model, @RequestParam("id") String productId, @RequestParam(value = "amount", required = false) Integer amount) {
        if (amount == null) amount = 1;
        posService.add(productId, amount);
        return refreshModel(model);
    }

    @GetMapping("/modify")
    public String modify(Model model, @RequestParam("id") String productId, @RequestParam(value = "amount") Integer amount) {
        posService.modify(productId, amount);
        return refreshModel(model);
    }

    @GetMapping("/del")
    public String add(Model model, @RequestParam("id") String productId) {
        posService.delete(productId);
        return refreshModel(model);
    }

    @GetMapping("/empty")
    public String empty(Model model) {
        posService.emptyCart();
        return refreshModel(model);
    }
}
