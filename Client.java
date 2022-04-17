package com.company;
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try{
            Socket soc=new Socket("34.131.143.93",3389);
            DataOutputStream dout=new DataOutputStream(soc.getOutputStream());
            DataInputStream in = new DataInputStream(soc.getInputStream());

            String msg=(String)in.readUTF();
            String filename = "5ab75ad0-715b-486a-91d3-fc3a40c70113.wav";
            System.out.println("Server: "+msg);
            dout.writeUTF(filename);

            String msg1=(String)in.readUTF();
            System.out.println("Label from Server: "+msg1);

            dout.flush();
            dout.close();
            soc.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}