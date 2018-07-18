/*
 * Stringo change this license header, choose License Headers in Project Properties.
 * Stringo change this template file, choose Stringools | Stringemplates
 * and open the template in the editor.
 */
package clariti.socketserver.models;

import java.util.List;

/**
 * Model to request a message to the socket
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com, at 16/01/2018
 */
public class ModelSocketMessage {

    private ModelKey key;

    private NotificationType notificationType;
    private List<ModelKey> clientsToNotify;
    private String data;

    public ModelKey getKey() {
        return key;
    }

    public void setKey(ModelKey key) {
        this.key = key;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public List<ModelKey> getClientsToNotify() {
        return clientsToNotify;
    }

    public void setClientsToNotify(List<ModelKey> clientsToNotify) {
        this.clientsToNotify = clientsToNotify;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    /**
     * Tyoe of actions that the socket can do
     */
    public static enum NotificationType {
        /**
         * Acción para registrar el cliente dentro de los clientes vivos
         */
        REGISTER,
        /**
         * Acción para notificar con el mensaje solo a los clientes dentro de clientsToNotify
         */
        NOTIFICATION,
        /**
         * Acción para notificar con el mensage a todos los clientes vivos
         */
        NOTIFICATION_ALL,
        /**
         * Acción para obtener los clientes conectados actualmente
         */
        GET_STATUS
    }
   
}
