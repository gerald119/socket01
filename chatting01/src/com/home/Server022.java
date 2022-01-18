package com.home;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server022 extends Thread {
    static ArrayList<Socket> list = new ArrayList<>(); // 다중 접속을 위한
    static ArrayList<String> namelist = new ArrayList<>();
    Socket sock = null;
    String name = new String("방장>");
    static String name2 = "";
    static int cut = 0; // 현재인원
    static int cut2 = 0; // 방 입장순서
    Custemer ctm = new Custemer();
    // ArrayList<Custemer> list1 = new ArrayList();

    public void sayAll(String msg) throws IOException {
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        for (int i = 0; i < list.size(); i++) {
            Socket sock = list.get(i);
            System.out.println(list.get(i) + "say");
            os = sock.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);
            for (int j = 0; j < namelist.size(); j++) {

                bw.write(namelist.get(j) + ",");
            }
            bw.write(name + msg);

            bw.newLine();
            bw.flush();
        }
    }

    public void sayPersonal(String msg) throws IOException {
        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        Socket sock = list.get(1);
        os = sock.getOutputStream();
        osw = new OutputStreamWriter(os);
        bw = new BufferedWriter(osw);
        bw.write(name + msg);
        bw.newLine();
        bw.flush();
    }

    class Custemer extends Thread {
        String custemerName = name2;

        @Override
        public void run() {
            InputStream is = null;
            OutputStream os = null;
            InputStreamReader isr = null;
            OutputStreamWriter osw = null;
            BufferedReader br = null;
            BufferedWriter bw = null;

            try {
                is = sock.getInputStream();
                os = sock.getOutputStream();
                isr = new InputStreamReader(is);
                osw = new OutputStreamWriter(os);
                br = new BufferedReader(isr);
                bw = new BufferedWriter(osw);
                sayAll(name2 + "입장");
                while (true) {
                    System.out.println("Custemer");
                    String msg = br.readLine();
                    if (msg.equals("exit")) {
                        break;
                    }
                    if (msg.charAt(0) == 'i') {

                        System.out.println(name2);

                    }
                    System.out.println(msg);
                    sayAll(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Run IOE");
            } finally {
                try {
                    sayAll(name2 + "퇴장");
                    bw.close();
                    br.close();
                    osw.close();
                    isr.close();
                    os.close();
                    is.close();
                    sock.close();
                    System.out.println("123try finally");
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("123Run finally");
                }
            }
        }

    }

    @Override
    public void run() {

        InputStream is = null;
        OutputStream os = null;
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;
        BufferedWriter bw = null;

        try {
            is = sock.getInputStream();
            os = sock.getOutputStream();
            isr = new InputStreamReader(is);
            osw = new OutputStreamWriter(os);
            br = new BufferedReader(isr);
            bw = new BufferedWriter(osw);
            while (true) {
                System.out.println("mainThread");
                String msg = br.readLine();
                if (msg.equals("exit")) {
                    break;
                }
                if (msg.charAt(0) == 'i') {

                    name2 = msg.substring(1);
                    namelist.add(name2);
                    System.out.println("등록");

                    System.out.println(name2);
                    try {

                        ctm.start();
                        this.join();

                    } catch (Exception e) {
                        System.out.println("Custemer" + e);
                    }

                }
                sayAll("등록전" + cut + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Run IOE");
        } finally {
            try {
                bw.close();
                br.close();
                osw.close();
                isr.close();
                os.close();
                is.close();
                sock.close();
                System.out.println("try finally");
                System.out.println(cut--);

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Run finally");
            }
        }

    }

    public static void main(String[] args) {
        ServerSocket serve = null;

        try {
            serve = new ServerSocket(5000);
            while (true) {
                Server022 thr = new Server022();
                System.out.println("지구정복 준비중...");
                thr.sock = serve.accept();
                thr.start();

                System.out.println("지구정복 프로그램이 시작됨..");
                System.out.println(cut++);
                list.add(thr.sock);
                System.out.println(list + "main");

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOE");

        } finally {
            try {
                System.out.println("main server.close");
                serve.close();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("finally");

            }
        }
    }

}

// public class Custemer {
// String name = "gerald2>";

// }

// ArrayList<Custemer> list1 = new ArrayList();
