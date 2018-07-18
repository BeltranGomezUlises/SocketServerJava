/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ub.socketserver.models;

import java.util.Objects;

/**
 * Model to represent the identifier of a client connected to the socket
 *
 * @author Ulises Beltrán Gómez --- beltrangomezulises@gmail.com, at 24/01/2018
 */
public class ModelKey {

    private String clientId;
    private ClientType clientType;

    public ModelKey() {
    }

    public ModelKey(String clientId, ClientType clientType) {
        this.clientId = clientId;
        this.clientType = clientType;
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.clientId);
        hash = 17 * hash + Objects.hashCode(this.clientType);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ModelKey other = (ModelKey) obj;
        if (!Objects.equals(this.clientId, other.clientId)) {
            return false;
        }
        return this.clientType == other.clientType;
    }

    /**
     * Clasify the client type that connects to the socket
     */
    public static enum ClientType {
        /**
         * Client from a web browser
         */
        WEB,
        /**
         * Client from a cell phone
         */
        CELL,
        /**
         * Client from an atm
         */
        ATM
    }
}
