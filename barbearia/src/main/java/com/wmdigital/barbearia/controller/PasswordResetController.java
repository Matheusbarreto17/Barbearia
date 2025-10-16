package com.wmdigital.barbearia.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.service.PasswordResetService;
import com.wmdigital.barbearia.service.UsuarioService;


@Controller
public class PasswordResetController {
	
	private final PasswordResetService passwordResetService;
	private final UsuarioService usuarioService;
		
	public PasswordResetController(PasswordResetService passwordResetService, UsuarioService usuarioService) {
		
		this.passwordResetService = passwordResetService;
		this.usuarioService = usuarioService;
	}
	
	
	 	@PostMapping({"/recuperar-senha"})
	    public ModelAndView passwordreset(@RequestParam("username") String username, Model model) {
	    	    	   	 
	   	    ModelAndView mav = new ModelAndView("recuperar-senha");
	   	    
	   	    try {
				
	   	    	passwordResetService.solicitarReset(username);
	   	    	model.addAttribute("ok", true);
	   	    	
	   	    	
			} catch (Exception e) {
				model.addAttribute("erro", e.getMessage());
			}
	   	  
	        return mav;
	    }
	 
		
	 	@GetMapping({"/recuperar-senha"})
	    public ModelAndView passwordrecover() {
	    	    	   	 
	   	    ModelAndView mav = new ModelAndView("recuperar-senha");
	   	  
	        return mav;
	    }
	 	
	 	@GetMapping("/resetar-senha")
	 	public ModelAndView formReset(@RequestParam("token") String token, Model model) {
	 		
	 		ModelAndView mav = new ModelAndView("resetar-senha");
	 		model.addAttribute("token", token);
		   	  
	 		
	 		return mav;
	 	}
	 	
		@PostMapping("/resetar-senha")
	 	public ModelAndView formReset(@RequestParam String token, @RequestParam String senha, @RequestParam String confirma, Model model) {
	 		
	 		ModelAndView mav = new ModelAndView("resetar-senha");
	 		
	 		if (!senha.equals(confirma)) {
	 		
	 		model.addAttribute("erro", "senha nao sao iguais");
	 		model.addAttribute("token", token);
	 		
	 		return mav;
	 		}
	 		
	 		try {
	 			
	 			passwordResetService.redefinirSenha(token, senha, usuarioService);
	 			
	 			return new ModelAndView("redirect:/login?reset-ok");
	 			
			} catch (Exception e) {
				
				model.addAttribute("erro", e.getMessage());
		 		model.addAttribute("token", token);
		 		
		 		return new ModelAndView("resetar-senha");
			}
	 		
	 	}
}
