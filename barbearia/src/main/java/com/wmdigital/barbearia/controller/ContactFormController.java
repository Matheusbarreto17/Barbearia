package com.wmdigital.barbearia.controller;

import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.wmdigital.barbearia.entity.ContactForm;
import com.wmdigital.barbearia.form.AgendamentoForm;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.ContactFormService;

@Controller
@RequestMapping("/contactForms")
public class ContactFormController extends AbstractController<ContactForm> {
	
	private final ContactFormService contactFormService;

	protected ContactFormController(ContactFormService contactFormService) {
		super("contactForm", ContactForm  :: new);
		
		this.contactFormService = contactFormService;
	}

	@Override
	protected ContactForm createEmptyEntity() {
		
		ContactForm contactForm = new ContactForm();
		contactForm.setAtivo(true);
		
		return contactForm;
	}

	@Override
	protected AbstractEntityService<ContactForm> getService() {
		
		return contactFormService;
	}
	
	@GetMapping
	@Override
	public ModelAndView listEntitys() {

		return super.listEntitys();
	}
	
	@PostMapping("/save")
	public ModelAndView saveForm(@Valid ContactForm form, BindingResult br, RedirectAttributes ra, Model model) {
		
		if (br.hasErrors()) {
    		
			ra.addFlashAttribute("erro", "Dados invalidos no formulario");
			
			return new ModelAndView("index");
		}
		
		getService().save(form);
		
		contactFormService.mailConfirmation(form);
		
		System.out.println("Salvou o formulario");
		
		return  new ModelAndView("redirect:/index");
	}

}
