package com.wmdigital.barbearia.framework;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

import javax.validation.Valid;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;


public abstract class AbstractController <Entity> {

    private static final String SUCCESS_MESSAGE = "Sucesso";
    private static final String ERROR_MESSAGE = "Erro";

    private final String entityAlias;
    private final Supplier<Entity> factory;

    protected AbstractController(String entityAlias, Supplier<Entity> factory) {
        this.entityAlias = entityAlias;
        this.factory=factory;
    }

    protected abstract Entity createEmptyEntity();
   
    protected void preloadFormData(ModelMap modelMap) {
        // opcional nos filhos
    }
	protected abstract AbstractEntityService<Entity> getService();

    @GetMapping("/cadastro")
    public ModelAndView showCreateForm() {
        ModelAndView mav = new ModelAndView("private/" + entityAlias + "/cadastro/cadastro" + entityAlias);
        List<Entity> entities = getService().findAll();
        mav.addObject("acao", "add");
        mav.addObject(entityAlias + "List", entities);
        mav.addObject(entityAlias,  createEmptyEntity() );
        
        preloadFormData(mav.getModelMap());
        
        return mav;
    }
    
    @PostMapping("/add")
    @Transactional
    public ModelAndView creatEntity(@Valid @ModelAttribute Entity entity, BindingResult result, Model model, HttpServletRequest req, RedirectAttributes redirect, @RequestParam(name="fotoFile", required=false) MultipartFile fotoFile) {
    	
    	 ModelAndView mav = new ModelAndView("private/" + entityAlias + "/cadastro/cadastro" + entityAlias);
    	  
    	 if(result.hasErrors()) {
    		 
    		 mav.addObject("error", result.getFieldError().getDefaultMessage());
    		 mav.addObject("acao", "add");
    	     mav.addObject(entityAlias, entity);
    	     preloadFormData(mav.getModelMap());
    	    	
    	     return mav;
    		 
    	 } 
    	
    		 getService().save(entity);
    		 afterCreateHook(entity, fotoFile, req);
    		 redirect.addFlashAttribute("msg", SUCCESS_MESSAGE);
    		 return new ModelAndView("redirect:/" + entityAlias + "s");
    	
    }
    
    @GetMapping("/movimentacao")
    public ModelAndView listEntitys() {
    	
    	ModelAndView mav = new ModelAndView("private/" + entityAlias + "/movimentacao/list" + entityAlias);
    	
    	mav.addObject(entityAlias + "List", getService().findAll());
    	
    	return mav;
    }
    
    @GetMapping("/editar/{id}")
    public ModelAndView showEditForm(@PathVariable UUID id) {
    	
    	Entity entity = getService().findOne(id);
    	ModelAndView mav = new ModelAndView("private/" + entityAlias + "/cadastro/cadastro" + entityAlias);
   	 	
    	mav.addObject(entityAlias, entity);
    	mav.addObject("acao", "edicao");
    	preloadFormData(mav.getModelMap());
    	return mav;
    }
    
    @PostMapping("/edicao")
    @Transactional
    public ModelAndView upDateEntity(@Valid @ModelAttribute Entity entity, BindingResult result, Model model, HttpServletRequest req, RedirectAttributes redirect ,@RequestParam(name="fotoFile", required=false) MultipartFile fotoFile) {
    	
    	
    	ModelAndView mav = new ModelAndView("private/" + entityAlias + "/cadastro/cadastro" + entityAlias);
   	 
   	 if(result.hasErrors()) {
   		 
   		mav.addObject("error", result.getFieldError().getDefaultMessage());
   		mav.addObject("acao", "add");
   	   	mav.addObject(entityAlias, entity);
   	   	
   	   	return mav;
   		 
   	 } 
   		 
   		getService().save(entity);
   		afterUpDateHook(entity, fotoFile, req);
   		redirect.addFlashAttribute("msg", SUCCESS_MESSAGE);
   		 
   		 return new ModelAndView("redirect:/" + entityAlias + "s");
    }
    
    @PostMapping("/delete/{id}")
    public ModelAndView deleteEntity(@PathVariable UUID id, RedirectAttributes ra ) {
    	
    	try {
    		
    		Entity entity = getService().findOne(id);
    		
    		getService().delete(entity);
    		ra.addFlashAttribute("sucess", entityAlias + "removido");
			
		} catch (DataIntegrityViolationException e) {
			
			ra.addFlashAttribute("error", "nao e possivel excluir existe registros vinculados");
			
		}
    	
    	return  new ModelAndView("redirect:/" + entityAlias + "s");
    	
    }
    
    protected  void afterCreateHook(Entity entity, MultipartFile file, HttpServletRequest req) {
    	
    }
    
    protected  void afterUpDateHook(Entity entity, MultipartFile file, HttpServletRequest req) {
    	
    }
}