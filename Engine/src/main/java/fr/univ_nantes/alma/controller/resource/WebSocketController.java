package fr.univ_nantes.alma.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import fr.univ_nantes.alma.Application;

@Controller
public class WebSocketController {
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	WebSocketController(SimpMessagingTemplate template) {
		this.template = template;
	}

    @MessageMapping("/game")
    public void onReceivemessage(String message) {
    	System.out.println(message);
    	this.template.convertAndSend("/greeting", Application.engine.createPlayer(1, "Bob"));
    }
}
