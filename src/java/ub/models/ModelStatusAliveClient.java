/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ub.models;

import ub.models.ModelKey.ClientType;

/**
 * Model to represent the alive session of a client in the socket
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com, at 17/01/2018
 */
public class ModelStatusAliveClient {

    private String sessionId;
    private String clientId;
    private ClientType clientType;

    public ModelStatusAliveClient() {
    }

    public ModelStatusAliveClient(String sessionId, String clientId, ClientType clientType) {
        this.sessionId = sessionId;
        this.clientId = clientId;
        this.clientType = clientType;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }

}
