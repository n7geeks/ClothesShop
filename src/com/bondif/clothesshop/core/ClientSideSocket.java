package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Account;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ClientSideSocket {
    private OutputStream out;
    private InputStream in;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8888;
    private Socket me;

    public ClientSideSocket() {
        try {
            me = new Socket(HOST, PORT);
            out = me.getOutputStream();
            in = me.getInputStream();
            out.write(20);
            ObjectInputStream objectIs = new ObjectInputStream(in);
            Account account = null;
            try {
                account = (Account) objectIs.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println(account);
            objectIs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
