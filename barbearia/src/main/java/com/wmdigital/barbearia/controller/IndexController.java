package com.wmdigital.barbearia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wmdigital.barbearia.entity.Barbeiro;
import com.wmdigital.barbearia.entity.Produto;
import com.wmdigital.barbearia.entity.Servico;
import com.wmdigital.barbearia.framework.AbstractController;
import com.wmdigital.barbearia.framework.AbstractEntityService;
import com.wmdigital.barbearia.service.BarbeiroService;
import com.wmdigital.barbearia.service.ProdutoService;
import com.wmdigital.barbearia.service.ServicoService;


@Controller
public class IndexController extends AbstractController<Object> {
	
	
	@Autowired
	private BarbeiroService barbeiroService;
	
	@Autowired
	private ServicoService servicoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	
	
	@Autowired
	private  PasswordEncoder encoder;	
	

	protected IndexController() {
		super("home", Object::new);
		
	}
	
	
	
    @GetMapping({"/", "/index"})
    public ModelAndView index() {
    	
    	System.out.println(encoder.encode("123"));
    	   	 
   	    ModelAndView mav = new ModelAndView("index");
   	    
   	   List<Barbeiro> barbeiros = barbeiroService.findAll(); 
   	   mav.addObject("barbeiros", barbeiros);
   	   
   	   List<Servico> servicos = servicoService.findAll();
   	   mav.addObject("servicos", servicos);
   	   
   	   List<Produto> produtos = produtoService.findAll();
   	   mav.addObject("produtos", produtos);
   	   
        return mav;
    }
    
    
    @GetMapping({"/login"})
    public ModelAndView login() {
    	
    	   	 
   	    ModelAndView mav = new ModelAndView("login");
//   	    mav.addObject("modelos", modelos);
   	  
        return mav;
    }
    
    
    @GetMapping({"/dashboard"})
    public ModelAndView dashboard() {
    	
    	   	 
   	    ModelAndView mav = new ModelAndView("private/dashboard");
//   	    mav.addObject("modelos", modelos);
   	  
        return mav;
    }

	@Override
	protected Object createEmptyEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AbstractEntityService<Object> getService() {
		// TODO Auto-generated method stub
		return null;
	}

}
