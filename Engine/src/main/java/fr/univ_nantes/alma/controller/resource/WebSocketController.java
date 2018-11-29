package fr.univ_nantes.alma.controller.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import fr.univ_nantes.alma.Application;

@Controller
public class WebSocketController {
	
	@Autowired
	private SimpMessagingTemplate template;
	
	@MessageMapping("/getHeros")
    public void getHeros(String message) {
    	this.template.convertAndSend("/greeting", Application.engine.getHeros());
    }

    @MessageMapping("/createPlayer")
    public void createPlayer() {
    	this.template.convertAndSend("/greeting", Application.engine.createPlayer(1, "Bob"));
    }
    
    @MessageMapping("/createGame")
    public void createGame(String message) {
    	//this.template.convertAndSend("/greeting", Application.engine.createGame(1, "Bob"));
    }
    
    @MessageMapping("/endTurn")
    public void endTurn(String message) {
    	this.template.convertAndSend("/greeting", Application.engine.createPlayer(1, "Bob"));
    }
    
    @MessageMapping("/playCard")
    public void playCard(String message) {
    	this.template.convertAndSend("/greeting", Application.engine.createPlayer(1, "Bob"));
    }
    
    @MessageMapping("/heroPower")
    public void heroPower(String message) {
    	this.template.convertAndSend("/greeting", Application.engine.createPlayer(1, "Bob"));
    }
    
    @MessageMapping("/attack")
    public void attack(String message) {
    	this.template.convertAndSend("/greeting", Application.engine.createPlayer(1, "Bob"));
    }
}
