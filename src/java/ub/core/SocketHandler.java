package ub.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.websocket.Session;
import ub.models.ModelKey;
import ub.models.ModelResponse;
import ub.models.ModelSocketMessage;
import static ub.models.ModelSocketMessage.NotificationType.*;
import ub.models.ModelStatusAliveClient;

/**
 * Handler class for werver side sockets
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com, at 16/01/2018
 */
public class SocketHandler {

    /**
     * alive sessions
     */
    private static final HashMap<Session, ModelKey> SESSIONS = new HashMap<>();

    /**
     * adds a session to the static set
     *
     * @param session socket session
     */
    public static void addSession(Session session) {
        //it's necesary to put the session in the map, to find it later in the registry of the cliente id and client type
        SESSIONS.put(session, null);
    }

    /**
     * removes a session from de static set
     *
     * @param session
     */
    public static void removeSession(Session session) {
        SESSIONS.remove(session);
    }

    /**
     * Sets the clientId to the actual socket session
     *
     * @param mySession socket session requesting
     * @param modelKey comercial client id and client type
     */
    private static void setSessionClientId(Session mySession, ModelKey modelKey) {
        SESSIONS.put(mySession, modelKey);
    }

    /**
     * retrieve alive sessions
     *
     * @return list of alive socket sessions
     */
    public static Set<Session> getSessions() {
        return SESSIONS.keySet();
    }

    /**
     * broadcast a message to all alive session except to the sender session
     *
     * @param socketMessage
     */
    public static void sendMessageToAll(ModelSocketMessage socketMessage) {
        final ObjectMapper objectMapper = new ObjectMapper();
        SESSIONS.forEach((sessions, clientId) -> {
            try {
                ModelResponse modelResponse = new ModelResponse(NOTIFICATION_ALL, socketMessage.getData());
                sessions.getBasicRemote().sendText(objectMapper.writeValueAsString(modelResponse));
            } catch (IOException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * send the message to all clients in the client list that are alive
     *
     * @param socketMessage model socket message
     */
    private static void sendMessage(ModelSocketMessage socketMessage) {
        final ObjectMapper mapper = new ObjectMapper();
        SESSIONS.forEach((sessions, modelKey) -> {
            try {
                if (socketMessage.getClientsToNotify().contains(modelKey)) {
                    ModelResponse modelResponse = new ModelResponse(NOTIFICATION, socketMessage.getData());
                    sessions.getBasicRemote().sendText(mapper.writeValueAsString(modelResponse));
                }
            } catch (IOException ex) {
                Logger.getLogger(SocketHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * retrieve the alive sessions
     *
     * @param mySession actual session requesting for clientes
     * @throws JsonProcessingException if mapper cants process mapping
     * @throws IOException if sendText method goes something wrong
     */
    private static void sendStatus(Session mySession) throws JsonProcessingException, IOException {
        final ObjectMapper mapper = new ObjectMapper();

        List<ModelStatusAliveClient> alives = new ArrayList<>();
        SESSIONS.forEach((session, modelKey) -> alives.add(new ModelStatusAliveClient(session.getId(), modelKey.getClientId(), modelKey.getClientType())));

        ModelResponse modelResponse = new ModelResponse(GET_STATUS, alives);
        mySession.getBasicRemote().sendText(mapper.writeValueAsString(modelResponse));
    }

    /**
     * serialize de message for getting the content and pass it to the rigth way
     *
     * @param socketMessage message from client
     * @param session socket session
     * @throws java.io.IOException
     */
    public static void proccessMessage(ModelSocketMessage socketMessage, Session session) throws IOException {
        switch (socketMessage.getNotificationType()) {
            case REGISTER:
                setSessionClientId(session, socketMessage.getKey());
                break;
            case NOTIFICATION:
                sendMessage(socketMessage);
                break;
            case NOTIFICATION_ALL:
                sendMessageToAll(socketMessage);
                break;
            case GET_STATUS:
                sendStatus(session);
                break;
            default:
                throw new AssertionError();
        }
    }

}
