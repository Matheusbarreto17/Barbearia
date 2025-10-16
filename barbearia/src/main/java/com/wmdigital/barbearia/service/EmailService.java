package com.wmdigital.barbearia.service;

import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService  {
	
	private final JavaMailSender javaMailSender;
	private final TemplateEngine templateEngine;
	
	@Value("${app.mail.from: no-reply@barbearia.local}")
	private String from;

	public EmailService(JavaMailSender javaMailSender, TemplateEngine templateEngine) {
		
		this.javaMailSender = javaMailSender;
		this.templateEngine = templateEngine;
	}
	
	public void sendTemplate(String to, String subject, String template, Map<String, Object> model) {
		
		try {
			Context ctx = new Context(new Locale("pt","PT"));
			ctx.setVariables(model);
			
			String html = templateEngine.process(template, ctx);
			MimeMessage msg = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(msg, "UTF-8");
			
			helper.setFrom(from);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(html, true);
			
			javaMailSender.send(msg);
			
			
		} catch (Exception e) {
			throw new RuntimeException("Falha ao enviar email" + e.getMessage(), e);
		}
		
	}

}
