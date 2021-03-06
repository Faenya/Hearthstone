package fr.univ.nantes.alma.controller;

import fr.univ.nantes.alma.Application;
import fr.univ.nantes.alma.engine.EngineException;
import fr.univ.nantes.alma.engine.GameMethods;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

  @Autowired
  private SimpMessagingTemplate template;

  /**
   * Get the Heros. 
   */
  @MessageMapping("/getHeros")
  public void getHeros() {
    this.template.convertAndSend("/heros", Application.engineBridge.getHeros());
  }
  
  /**
   * Create a Player with a username and a chosen Hero. 
   * @param data the data from the user
   */
  @MessageMapping("/createPlayer")
  public void createPlayer(String data) {
    String[] dataSplit = data.split("_");
    this.template.convertAndSend("/player/" + dataSplit[0],
        Application.engineBridge.createPlayer(Integer.parseInt(dataSplit[1]), dataSplit[0]));
  }

  /**
   * Create a Game or tell the Player to wait for another Player.
   * @param uuidPlayer the id of the Player
   */
  @MessageMapping("/createGame")
  public void createGame(String uuidPlayer) {
    GameMethods game = Application.engineBridge.createGame(UUID.fromString(uuidPlayer));
    if (game == null) {
      this.template.convertAndSend("/game/" + uuidPlayer, "En attente d'un deuxième joueur");
    } else {
      this.template.convertAndSend("/game/" + game.getCurrentPlayer().getUuid(), game);
      this.template.convertAndSend("/game/" + game.getCurrentPlayer().getUuid(), 
          "A vous de jouer !");
      this.template.convertAndSend("/game/" + game.getOtherPlayer().getUuid(), game);
    }
  }

  /**
   * End the current turn with the data from the client containing the id of the game 
   * and the id of the player ending the turn.
   * @param data the data from the client
   */
  @MessageMapping("/endTurn")
  public void endTurn(String data) {
    String[] dataSplit = data.split("_");
    try {
      GameMethods game = Application.engineBridge.endTurn(UUID.fromString(dataSplit[0]), 
          UUID.fromString(dataSplit[1]));

      if (game.getOtherPlayer().getUuid().toString().equals(dataSplit[1])) {
        this.template.convertAndSend("/game/" + dataSplit[0], game);
        this.template.convertAndSend("/game/" + game.getCurrentPlayer().getUuid(), 
            "A vous de jouer !");
      } else {
        this.template.convertAndSend("/game/" + game.getCurrentPlayer().getUuid(), 
            "A vous de jouer !");
      }
    } catch (EngineException e) {
      this.template.convertAndSend("/game/" + dataSplit[1], e.getMessage());
    }
  }

  /**
   * Play a card with the information sent by the client. 
   * @param data the data from the client
   */
  @MessageMapping("/playCard")
  public void playCard(String data) {
    String[] dataSplit = data.split("_");
    try {
      GameMethods game = Application.engineBridge.playCard(UUID.fromString(dataSplit[0]), 
          UUID.fromString(dataSplit[1]), Integer.parseInt(dataSplit[2]),
          UUID.fromString(dataSplit[3]), Integer.parseInt(dataSplit[4]));
      this.template.convertAndSend("/game/" + dataSplit[0], game);
      
      if (game.isGameOver()) {
        Application.engineBridge.endGame(game.getIdGame(), game.getCurrentPlayer().getUuid(), 
            game.getOtherPlayer().getUuid());
      }
    } catch (EngineException e) {
      this.template.convertAndSend("/game/" + dataSplit[1], e.getMessage());
    }
  }

  /**
   * Activate a Hero Power with the information sent by the client. 
   * @param data the data from the client
   */
  @MessageMapping("/heroPower")
  public void heroPower(String data) {
    String[] dataSplit = data.split("_");
    try {
      GameMethods game = Application.engineBridge.heroPower(UUID.fromString(dataSplit[0]), 
          UUID.fromString(dataSplit[1]), UUID.fromString(dataSplit[2]), 
          Integer.parseInt(dataSplit[3]));
      this.template.convertAndSend("/game/" + dataSplit[0], game);
      
      if (game.isGameOver()) {
        Application.engineBridge.endGame(game.getIdGame(), game.getCurrentPlayer().getUuid(), 
            game.getOtherPlayer().getUuid());
      }
    } catch (EngineException e) {
      this.template.convertAndSend("/game/" + dataSplit[1], e.getMessage());
    }
  }

  /**
   * Attack a card with the information sent by the client. 
   * @param data the data from the client
   */
  @MessageMapping("/attack")
  public void attack(String data) {
    String[] dataSplit = data.split("_");
    try {
      GameMethods game = Application.engineBridge.attack(UUID.fromString(dataSplit[0]), 
          UUID.fromString(dataSplit[1]), Integer.parseInt(dataSplit[2]), 
          UUID.fromString(dataSplit[3]), Integer.parseInt(dataSplit[4]));
      this.template.convertAndSend("/game/" + dataSplit[0], game);
      
      if (game.isGameOver()) {
        Application.engineBridge.endGame(game.getIdGame(), game.getCurrentPlayer().getUuid(),
            game.getOtherPlayer().getUuid());
      }
    } catch (EngineException e) {
      this.template.convertAndSend("/game/" + dataSplit[1], e.getMessage());
    }
  }
}
