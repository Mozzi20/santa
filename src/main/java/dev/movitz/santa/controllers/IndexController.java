package dev.movitz.santa.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.movitz.santa.datatransferobjects.FAQDTO;
import dev.movitz.santa.services.FAQService;

@Controller
@RequestMapping("/")
public class IndexController {
	
	@Autowired
	private FAQService FAQService;
	
	@GetMapping
	public String index(Model model) {
		model.addAttribute("FAQS", FAQService.getAnswerdFAQs());
		model.addAttribute(new FAQDTO());
		return "index";
	}
	
	@PostMapping
	public String askFaq(
			@Valid FAQDTO FAQDTO,
			Errors errors,
			RedirectAttributes redirect
			) {
		if(errors.hasErrors()) {
			redirect.addFlashAttribute("errors", errors);
		} else {
			FAQService.createFAQ(FAQDTO);
		}
		return "redirect:/";
	}
	
}
