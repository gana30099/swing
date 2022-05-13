import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Main extends JFrame {

    private String language_selected;
    private boolean bbb = false;
    List<SentenceTranslated> list;
    Socket socket;
    public Main() throws IOException {



        super("titre de l'application");

        Main main = this;


        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        };
        addWindowListener(l);

        //JButton bouton = new JButton("Save");
        JButton bouton2 = new JButton("Charger");
        JButton bouton3 = new JButton("Check");
        JButton bouton = new JButton("Shuffle");
        JButton bouton4 = new JButton("restart");
        JButton bouton5 = new JButton("clear panel");

        JTextField jtf = new JTextField(20);


        JPanel panneau = new JPanel();
        panneau.setLayout(
                new GridLayout(0, 1));
       // panneau.add(bouton);

        JPanel menu = new JPanel();
      //  menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));

        menu.add(bouton2);
        menu.add(bouton3);
        menu.add(bouton);
        menu.add(bouton4);
        menu.add(bouton5);
        menu.add(jtf);

        JMenuBar mb=new JMenuBar();
        //mb.setLayout(new BoxLayout(mb, BoxLayout.X_AXIS));
        JMenu menu2=new JMenu("Menu");
        JMenuItem menu3 = new JMenuItem("Connect");
        JMenuItem menu4 = new JMenuItem("Extract");

        //menu2.setLayout(new BoxLayout(menu2, BoxLayout.X_AXIS));

        JMenuItem i1=new JMenuItem("Shuffle");
        //JMenuItem i2=new JMenuItem("Charger");
        JMenuItem i3=new JMenuItem("Check");
        JMenuItem i4=new JMenuItem("Restart");
        JMenuItem i5=new JMenuItem("Clear Panel");
        JMenuItem i6=new JMenuItem("Detacher Clavier");
        JMenu i7=new JMenu("Langue");
        i7.add(new JMenuItem("grec"));
        i7.add(new JMenuItem("russe"));
        //i7.add(new JMenuItem("allemand"));


        menu2.add(i1);

        menu2.add(i3);
        menu2.add(i4);

        menu2.add(i5);menu2.add(i6);menu2.add(i7);


        mb.add(menu2);;
        mb.add(menu3);
        mb.add(menu4);

        this.setJMenuBar(mb);

        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
        JScrollPane jcb = new JScrollPane(jp,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panneau.add(jcb);

        Map<Integer, BufferedImage> map = new HashMap<>();

        BufferedImage myPicture = ImageIO.read(new File("C:\\Users\\gaeta\\Desktop\\greek-keyboard.png"));
        BufferedImage myPicture2 = ImageIO.read(new File("C:\\Users\\gaeta\\Desktop\\russian-keyboard.png"));


        map.put(1, myPicture);
        map.put(2, myPicture2);


        Image tmp = myPicture.getScaledInstance(800, 300, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(800, 300, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();


        final JLabel[] picLabel = {new JLabel(new ImageIcon(dimg))};
        panneau.add(picLabel[0]);

        JFileChooser fc = new JFileChooser();
        //la réponse quand un bouton est cliqué

        ArrayList<String> al = new ArrayList<>();
        ArrayList<JTextField> alTex = new ArrayList<>();

        for (int i = 0; i<i7.getItemCount(); i++) {
            int finalI = i;
            i7.getItem(i).addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    //Image tmp = map.get(finalI + 1).getScaledInstance(800, 300, Image.SCALE_SMOOTH);
                    //BufferedImage dimg = new BufferedImage(800, 300, BufferedImage.TYPE_INT_ARGB);

                    //Graphics2D g2d = dimg.createGraphics();
                    //g2d.drawImage(tmp, 0, 0, null);
                    //g2d.dispose();

                    panneau.remove(picLabel[0]);
                    main.invalidate();
                    main.validate();
                    main.repaint();
                   //picLabel[0] = new JLabel(new ImageIcon(dimg));
                    //panneau.add(picLabel[0]);

                    main.invalidate();
                    main.validate();
                    main.repaint();
                }
            });
        }

        menu4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("extract");

                JComboBox<String> jcb = new JComboBox<>();
                jcb.addItem("lacoccinelle.net");
                jcb.setBounds(40, 20, 100, 20);

                JComboBox<String> jcbLanguages = new JComboBox<>();

                // todo JMenu above already contain different Languages
                jcbLanguages.addItem("English");
                jcbLanguages.addItem("Russian");
                jcbLanguages.addItem("Dutch");

                JTextField jtf = new JTextField("");
                jtf.setBounds(150, 20, 200, 20);

                JButton extract = new JButton("Extract");
                extract.setBounds(400, 700, 100, 20);

                JButton upload = new JButton("Upload");
                upload.setBounds(290, 700, 100, 20);

                JButton cancel = new JButton("Cancel");
                cancel.setBounds(180, 700, 100, 20);

                /*if (bbb) {

                } else {
                    upload.setVisible(false);
                }*/

                upload.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        populate(list);
                    }
                });

                JFrame embendedJFrame = new JFrame();


                //To be robust against null values (still without casting) you may consider a third option:
                extract.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String x = String.valueOf(jcb.getSelectedItem());
                        String y = String.valueOf(jtf.getText());
                        URL url = null;
                        Document doc = null;
                        try {
                            doc = Jsoup.connect("https://www." + x + y).get();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                        int yy = 60;

                        JLabel delete = new JLabel("delete : ");
                        delete.setBounds(480, 20, 80, 20);

                        /* add the JScrollPane to the contentPane */
                        JPanel contentPane = new JPanel();
                        JPanel panel1 = new JPanel();
                        panel1.add(new JLabel("hi"));
                        JScrollPane jScrollPane1 = new JScrollPane(panel1);
                        //panel1.revalidate();
                        //panel1.repaint();
                        JScrollPane jScrollPane = new JScrollPane() {
                            @Override
                            public Dimension getPreferredSize() {
                                return new Dimension(500, 800-40-20);
                            }
                        };



                        /* maybe add a panel */
                        //jScrollPane.setPreferredSize(new Dimension(500, 800-40-20));
                        jScrollPane.setBounds(40, 40, 500, 800-40-20);

                        System.out.println(doc.title());
                        list = new ArrayList<>();
                        Elements newsHeadlines = doc.select("p strong");
                        Elements newsHeadlines2 = doc.select("p em");

                        /* Vérifions que le nombre de lignes est égale dans le lyrics */
                        if (newsHeadlines.toArray().length != newsHeadlines2.toArray().length) {
                            System.out.println("nombre de lignes pas égale : Veuillez changer de chanson");
                            return;
                        }
                        for (int j=0; j<newsHeadlines.toArray().length; j++) {
                            Element headline = newsHeadlines.get(j);
                            Element headline2 = newsHeadlines2.get(j);

                            int jj = yy + newsHeadlines.size() * SentenceTranslated.HEIGHT;

                            String s1 = headline.text();
                            String s2 = headline2.text();
                            list.add(new SentenceTranslated(jScrollPane, embendedJFrame, s1, s2, yy, jj));

                            yy += SentenceTranslated.HEIGHT;
                        }

                        //contentPane.add(jScrollPane);
                        embendedJFrame.add(jScrollPane);
                        embendedJFrame.add(delete);

                        //embendedJframe.add(upload);

                        embendedJFrame.invalidate();
                        embendedJFrame.validate();
                        embendedJFrame.repaint();

                        main.invalidate();
                        main.validate();
                        main.repaint();
                    }
                });


                embendedJFrame.setSize(600, 800);
                embendedJFrame.setResizable(false);

                embendedJFrame.add(jcb);
                embendedJFrame.add(extract);
                embendedJFrame.add(upload);
                embendedJFrame.add(cancel);
                embendedJFrame.add(jtf);
                embendedJFrame.setLayout(null);
                embendedJFrame.setVisible(true);

                embendedJFrame.invalidate();
                embendedJFrame.validate();
                embendedJFrame.repaint();
            }
        });

        menu3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("connect");
                JFrame embendedJframe = new JFrame("Connect");
                embendedJframe.setSize(600,300);
                // The following line close all the app
                // embendedJframe.setDefaultCloseOperation(EXIT_ON_CLOSE);
                embendedJframe.setResizable(false);
                JLabel lip = new JLabel("ip :");
                JLabel lport = new JLabel("port :");
                lip.setBounds(10, 20, 20, 20);
                lport.setBounds(10, 60, 30, 20);
                JTextField ip = new JTextField(20);
                //ip.setFont(new Font("Arial", Font.PLAIN, 20));
                ip.setBounds(60, 20, 300, 20);
                JTextField port = new JTextField(5);
                port.setBounds(60, 60, 100, 20);


                JButton connect = new JButton("Connect");
                connect.setBounds(400, 200, 100, 20);

                JLabel connectionSuccess = new JLabel("Connection successfull");
                connectionSuccess.setVisible(false);
                connectionSuccess.setBounds(60, 160, 150, 40);
                connectionSuccess.setForeground(Color.GREEN);

                JLabel loadSuccess = new JLabel("Data successfull added");
                loadSuccess.setVisible(false);
                loadSuccess.setBounds(60, 190, 150, 40);
                loadSuccess.setForeground(Color.BLUE);

                connect.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        //connectionSuccess.setVisible(true);


                        URI uri = URI.create("http://" + ip.getText() + ":" + port.getText());
                        IO.Options options = IO.Options.builder()
                                .setForceNew(true)
                                .build();

                        socket = IO.socket(uri, options);
                        socket.connect();

                        socket.on(Socket.EVENT_CONNECT_ERROR, new Emitter.Listener() {
                            @Override
                            public void call(Object... args) {
                                System.out.println("connection error " + args.toString());
                                socket.connect();
                            }
                        });

                        socket.once(Socket.EVENT_CONNECT, new Emitter.Listener() {
                            @Override
                            public void call(Object... objects) {
                                System.out.println("connect!!!");
                                connectionSuccess.setVisible(true);
                                bbb = true;

                            }
                        });

                    }
                });

                embendedJframe.add(ip);
                embendedJframe.add(port);
                embendedJframe.add(lip);
                embendedJframe.add(lport);
                //embendedJframe.add(jcb);
                embendedJframe.add(connect);
                embendedJframe.add(connectionSuccess);
                embendedJframe.setLayout(null);
                embendedJframe.setVisible(true);

                main.invalidate();
                main.validate();
                main.repaint();

            }
        });

        i5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jp.removeAll();
                main.invalidate();
                main.validate();
                main.repaint();
            }
        });

        i4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //alTex.removeAll(alTex);
                for (int i=0; i<al.size(); i++) {
                    alTex.get(i).setText("");
                    main.invalidate();
                    main.validate();
                    main.repaint();
                }
            }
        });

        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Integer> alS = new ArrayList();
                for (int i=0; i<al.size(); i++) {
                    alS.add(i);
                }
                jcb.revalidate();
                jcb.repaint();
                Collections.shuffle(alS);
            }
        });

        i6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame f = new JFrame();

                f.add(picLabel[0]);
                f.setVisible(true);

                f.setSize(800, 300);



                panneau.remove(picLabel[0]);

                main.invalidate();
                main.validate();
                main.repaint();
            }
        });


        i3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Iterator<String> it = al.iterator();
                int i = 0;
                while (it.hasNext()) {
                    String correct = it.next();
                    String one = alTex.get(i).getText();
                    if (one.equals(correct)) {
                        System.out.println("ok");
                    } else {
                        alTex.get(i).setForeground(Color.RED);
                        alTex.get(i).setText( one + " !CORRECTION! " + correct);
                    }
                    i++;
                }
            }
        });
/*
        i2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {




                int retour = fc.showOpenDialog(m);
                if (retour == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //afficher le nom du fichier
                    BufferedReader reader = null;
                    try {
                        reader = new BufferedReader(new FileReader(new File(file.getAbsolutePath())));
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                    String line = null;
                    while (true) {
                        try {
                            if (!((line = reader.readLine()) != null)) {
                                break;

                            } else {
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        String[] tokens = line.split(",");
                        String firstName = tokens[0].trim();
                        String name = tokens[1].trim();
                        al.add(name);


                        jp.add(new JLabel(firstName));
                        JTextField t = new JTextField("");
                        t.setColumns(20);

                        alTex.add(t);

                        jp.add(t);


                    }
                    try {
                        reader.close();
                        m.invalidate();
                        m.validate();
                        m.repaint();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.out.println("Nom du fichier : "+file.getName()+"\n");
                    //afficher le chemin absolu du fichier
                    jtf.setText("Chemin absolu : "+file.getAbsolutePath()+"\n");
                } else {
                    System.out.println("L'ouverture est annulée\n");
                }
            }
        });
*/
        setContentPane(panneau);
        setSize(800,800);

        setVisible(true);
    }

    // todo populate after load
    private void populate(List<SentenceTranslated> list) {
        Main main1 = this;
        list.toArray().toString();
        JSONObject jo_Populate1 = null;
        try {
            JSONArray ja_populate = new JSONArray();

            for (int i=0; i<list.toArray().length; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("mother", list.get(i).getS1());
                jsonObject.put("other", list.get(i).getS2());
                ja_populate.put(jsonObject);
            }

            jo_Populate1 = new JSONObject();

            jo_Populate1.put("sentences", ja_populate);

            jo_Populate1.put("language", "English");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        socket.emit("add_sentences", jo_Populate1, new Ack() {

            @Override
            public void call(Object... args) {
                JOptionPane.showMessageDialog(main1, "Nombre de phrases ajoutées : " + list.toArray().length);
            }
        });
    }

    public static void main(String [] args){
        try {
            JFrame frame = new Main();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}