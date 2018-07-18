/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clariti.socketserver.models;

import clariti.socketserver.models.ModelSocketMessage.NotificationType;



/**
 * Model to encapsulate de behaivor of the actions in the socket
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com, at 25/01/2018
 */
public class ModelResponse {

    private NotificationType action;
    private Object data;

    public ModelResponse() {
    }

    public ModelResponse(NotificationType action, Object data) {
        this.action = action;
        this.data = data;
    }

    public NotificationType getAction() {
        return action;
    }

    public void setAction(NotificationType action) {
        this.action = action;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
