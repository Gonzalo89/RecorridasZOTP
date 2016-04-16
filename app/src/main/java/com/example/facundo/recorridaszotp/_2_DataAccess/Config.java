package com.example.facundo.recorridaszotp._2_DataAccess;

import com.example.facundo.recorridaszotp._0_Infraestructure.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gonzalo on 13/04/16.
 */
public class Config {
    private static Config instance = null;
    private int rol;
    private Map<String, String> token;

    private Config () {
        this.token = new HashMap<String, String>();
        this.token.put(Utils.TOKEN_TYPE, Utils.BEARER);
        this.token.put(Utils.CLIENT,"");
        this.token.put(Utils.EXPIRY,"");
        this.token.put(Utils.UID,"");
        this.token.put(Utils.ACCESS_TOKEN,"");
    }

    static public Config getInstance() {
        if (instance == null) {
            instance = new Config();
        }
        return instance;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getExpiry() {
        return this.token.get(Utils.EXPIRY);
    }

    public void setExpiry(String expiry) {
        this.token.put(Utils.EXPIRY, expiry);
    }

    public String getUid() {
        return this.token.get(Utils.UID);
    }

    public void setUid(String uid) {
        this.token.put(Utils.UID,uid);
    }

    public String getClient() {
        return this.token.get(Utils.CLIENT);
    }

    public void setClient(String client) {
        this.token.put(Utils.CLIENT, client);
    }

    public String getAccessToken() {
        return this.token.get(Utils.ACCESS_TOKEN);
    }

    public void setAccessToken(String token) {
        this.token.put(Utils.ACCESS_TOKEN, token);
    }

    public String getTokenType() {
        return this.token.get(Utils.TOKEN_TYPE);
    }
}
