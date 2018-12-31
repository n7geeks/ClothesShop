package com.bondif.clothesshop.core;

import com.bondif.clothesshop.models.Account;

import java.io.*;
import java.net.Socket;

public class BankSocket {
    private OutputStream outputStream;
    private InputStream inputStream;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String host = "127.0.0.1";
    private int port = 8888;
    private Socket me;
    private Account account;

    public BankSocket(Account account) {
        this.account = account;
        try {
            me = new Socket(host, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int sendPayment() {
        try {
            outputStream = me.getOutputStream();
            inputStream = me.getInputStream();
            out = new ObjectOutputStream(outputStream);
            in = new ObjectInputStream(inputStream);

            out.writeObject(account);
            out.flush();

            int rs = 0;
            try {
                rs = (Integer)in.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            System.out.println("received code : " + rs);
            return rs;
        } catch (IOException e) {
            e.printStackTrace();
            return 500;
        } finally {
            try {
                me.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
