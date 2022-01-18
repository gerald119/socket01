package com.home;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client022 extends Frame implements ActionListener {

    static String[] list2 = {};
    // Ui ui2 = new Ui();

    static List selectOne = new List(6);
    static TextArea ta;
    static TextField tf;
    static BufferedWriter bw;
    static TextField tf2;
    JRadioButton radio1 = new JRadioButton();
    JRadioButton radio2 = new JRadioButton();
    JButton btnconn = new JButton();
    JLabel jLabel2 = new JLabel();

    JLabel jLabel3 = new JLabel();

    String name = "";

    // 화면 구성
    public Client022() {

        selectOne.setLocation(100, 100);

        selectOne.setSize(400, 400);
        selectOne.addActionListener(this);

        List selectMany = new List(6, true);
        selectMany.addActionListener(this);

        selectMany.setLocation(100, 100);

        selectMany.setSize(400, 400);

        selectMany.add("Student");

        selectMany.add("Teacher");

        selectMany.add("Driver");

        selectMany.add("Computer Programmer");

        selectMany.add("Sales Man");

        selectMany.add("Musician");

        selectMany.add("Director");

        this.setTitle("라이더 채팅!");
        this.setBackground(new Color(198, 214, 255));
        Panel p = new Panel();
        p.setLayout(new BorderLayout());
        Panel p1 = new Panel();
        p1.setLayout(new BorderLayout());
        Panel p2 = new Panel();
        p2.setLayout(new BorderLayout());
        Panel p3 = new Panel();
        p3.setLayout(new BorderLayout());
        Panel p4 = new Panel();
        p4.setLayout(new BorderLayout());
        Panel p5 = new Panel();
        p5.setLayout(new BorderLayout());
        Panel p6 = new Panel();
        p6.setLayout(new BorderLayout());

        radio1.setText("귓속말");
        radio2.setText("귓속말해제");
        btnconn.setText("가입");
        jLabel3.setText("회원가입");

        jLabel2.setText("이름");

        ta = new TextArea();
        tf = new TextField();
        tf2 = new TextField();

        p4.add(ta, BorderLayout.CENTER);
        p4.add(tf, BorderLayout.SOUTH);

        p5.add(btnconn, BorderLayout.NORTH);
        p5.add(radio1, BorderLayout.CENTER);
        p5.add(radio2, BorderLayout.SOUTH);

        p3.add(tf2, BorderLayout.SOUTH);
        p3.add(jLabel2, BorderLayout.CENTER);
        p3.add(jLabel3, BorderLayout.NORTH);

        p.add(p1, BorderLayout.EAST);
        p.add(p2, BorderLayout.WEST);
        p1.add(p3, BorderLayout.NORTH);
        p1.add(p4, BorderLayout.SOUTH);
        p2.add(p5, BorderLayout.NORTH);
        p2.add(p6, BorderLayout.SOUTH);

        tf.addActionListener(this);
        tf2.addActionListener(this);

        btnconn.addActionListener(this);
        radio1.addActionListener(this);
        radio2.addActionListener(this);

        p6.add(selectOne, BorderLayout.NORTH);
        p6.add(selectMany, BorderLayout.SOUTH);
        p6.setVisible(true);

        add(p);
        setBounds(600, 50, 800, 500);
        setVisible(true);

        Say say = new Say();
        try {
            say.start();

        } catch (Exception e) {
            System.out.println("saycatch err: " + e);
        }

    }

    // 액션
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getSource());
        if (e.getSource() == btnconn || e.getSource() == tf2) {
            name = tf2.getText();
            System.out.println(name + "지구정복준비중....");
            try {

                bw.write("i" + name); // 외계인등록
                bw.newLine();
                bw.flush();
                try {

                    AnotherFrame ano = new AnotherFrame();

                    ano.run();
                    // Say say = new Say();
                    // say.join();

                } catch (Exception e2) {
                    e2.printStackTrace();
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
        if (e.getSource() == selectOne) {
            System.out.println("개인톡 신호");
            Personal personal = new Personal();
            personal.run();
        }
        if (e.getSource() == tf) {
            String msg = tf.getText();
            try {

                bw.write(">" + name + "=" + msg); // 내보낼때
                bw.newLine();
                bw.flush();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    // 통신
    class Say extends Thread {

        Socket sock = null;
        InputStream is = null;
        OutputStream os = null;
        InputStreamReader isr = null;
        OutputStreamWriter osw = null;
        BufferedReader br = null;

        @Override
        public void run() {

            try {
                // sock = new Socket("192.168.50.230", 5000);
                sock = new Socket("192.168.0.18", 5000);
                // sock = new Socket("192.168.200.111", 5000);
                is = sock.getInputStream();
                os = sock.getOutputStream();
                isr = new InputStreamReader(is);
                osw = new OutputStreamWriter(os);
                br = new BufferedReader(isr);
                bw = new BufferedWriter(osw);

                while (true) {
                    String msg2 = br.readLine(); // 읽어올때
                    System.out.println(msg2);
                    String msg3 = msg2.substring(0, msg2.lastIndexOf(","));
                    System.out.println(msg3);
                    list2 = msg3.split(",");
                    String[] init = {};

                    selectOne.clear();
                    for (int i = 0; i < list2.length; i++) {
                        selectOne.add(list2[i]);

                    }
                    for (int i = 0; i < list2.length; i++) {
                        System.out.println(list2[i]);
                    }

                    msg2 = msg2.substring(msg2.lastIndexOf(">"));

                    ta.append(msg2 + "\n");
                    if (msg2.charAt(0) == '*') {
                        System.out.println("whil");
                    }

                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bw.close();
                    br.close();
                    osw.close();
                    isr.close();
                    os.close();
                    is.close();
                    sock.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    // 가입창
    public class AnotherFrame extends JFrame implements Runnable {

        @Override
        public void run() {
            setTitle("가입창 ");
            setSize(430, 110);
            setLocation(0, 120);
            getContentPane().setLayout(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JButton btnDispose = new JButton("가입");
            btnDispose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    dispose();
                }
            });
            btnDispose.setBounds(10, 10, 100, 50);
            getContentPane().add(btnDispose);

            JButton btnExit = new JButton("종료");
            btnExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    System.exit(0);
                }
            });
            btnExit.setBounds(120, 10, 100, 50);
            getContentPane().add(btnExit);

            setVisible(true);
        }
    }

    // 개인톡
    public class Personal extends JFrame implements Runnable {

        // public void addListener() {
        // txtarea.addActionListener(this);
        // }

        // public void actionPerformed(ActionEvent e) {
        // System.out.println("안녕하슈 personal ");
        // }

        @Override
        public void run() {

            setTitle("개인톡");
            setSize(430, 500);
            setLocation(300, 200);
            getContentPane().setLayout(null);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JButton btnDispose = new JButton("창닫기");
            btnDispose.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    dispose();
                }
            });
            btnDispose.setBounds(10, 10, 100, 50);
            getContentPane().add(btnDispose);

            JButton btnExit = new JButton("시스템 종료");
            btnExit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    System.exit(0);
                }
            });
            btnExit.setBounds(120, 10, 100, 50);
            getContentPane().add(btnExit);

            JTextArea txtarea = new JTextArea();
            txtarea.setBounds(10, 80, 400, 300);
            getContentPane().add(txtarea);

            JTextField txtsend = new JTextField();
            txtsend.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    String txt = txtsend.getText();
                    System.out.println(arg0.getSource());
                    try {

                        bw.write(">" + name + "=" + txt); // 내보낼때
                        bw.newLine();
                        bw.flush();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            txtsend.setBounds(10, 380, 400, 70);
            getContentPane().add(txtsend);

            setVisible(true);
        }

    }

    public static void main(String[] args) {

        Client022 me = new Client022();
    }
}

// static public void ui() {

// Frame f = new Frame();

// f.addWindowListener(new WindowAdapter() {
// @Override
// public void windowClosing(WindowEvent e) {
// f.dispose();
// }
// });
// f.setBounds(100, 100, 100, 100);
// f.setVisible(true);
// }

// static public class Ui extends Thread {

// @Override
// public void run() {
// Frame f = new Frame();
// f.setBounds(100, 100, 100, 100);
// f.setVisible(true);

// // super.run();
// }
// }

// this.setBounds(600, 50, 100, 100);
// this.setVisible(true);
// public class Thread1 implements Runnable{
// int index;
// public Thread1(int index)
// {
// this.index = index;
// }
// public void run()
// {

// }
// }
// static public class Pass extends Frame {

// }

// Ui ui2 = new Ui();
// try {

// ui2.start();
// this.join();
// } catch (Exception e) {
// System.out.println(e);
// }
// me.ui();

// lastindexOf(String a) = a 문자를 뒤에서부터 찾아 위치 값 숫자를 얻는다.

// subString(a, b) = a부터 b전까지의 위치의 문자열을 가져온다.

// String substring(int index) = 문자열 index위치부터 끝까지 문자열.

// split 문자열을 나누어 배열로 나누어 준다..

// string.substr(start,length)

// msg2.substring(0,msg2.lastIndexOf("*"))

// JPanel jp = new JPanel();
// jp.revalidate();
// jp.repaint();

// this.repaint();
// me.repaint();

// for (int i = 0; i < list2.length; i++) {
// selectOne.add(list2[i]);
// }

// selectOne.add("student");

// selectOne.add("Teacher");

// selectOne.add("Driver");

// selectOne.add("Computer Programmer");

// selectOne.add("Sales Man");

// selectOne.add("Musician");

// selectOne.add("Director");

// 생성자의 두번째 인자값을 true로 설정해서 List의 목록에서 여러 개를 선택할 수 있게 한다.
// public AnotherFrame() {