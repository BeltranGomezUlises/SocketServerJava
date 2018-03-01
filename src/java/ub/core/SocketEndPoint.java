package ub.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import ub.models.ModelSocketMessage;

/**
 * Endpoint class for web sockets
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com, at 16/01/2018
 */
@ServerEndpoint("/sockets")
public class SocketEndPoint {

    @OnOpen
    public void open(Session session) {        
        SocketHandler.addSession(session);
    }

    @OnClose
    public void close(Session session) {        
        SocketHandler.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void handleMessage(String message, Session session) {        
        try {
            ObjectMapper mapper = new ObjectMapper();
            ModelSocketMessage socketMessage = mapper.readValue(message, ModelSocketMessage.class);
            SocketHandler.proccessMessage(socketMessage, session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
