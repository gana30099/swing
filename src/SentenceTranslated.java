import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SentenceTranslated {

    public static final int HEIGHT = 10;
    private JScrollPane jScrollPane;
    private JFrame second;
    private String s1, s2;
    private boolean deleted = false;

    public SentenceTranslated(JScrollPane jScrollPane, JFrame second, String s1, String s2, int yy, int jj) {

        this.s1 = s1;
        this.s2 = s2;

        System.out.println(s1);
        System.out.println(s2);

        JLabel l = new JLabel(s1);
        l.setForeground(Color.RED);

        l.setBounds(40, yy, 360, HEIGHT);
        JLabel l2 = new JLabel(s2);
        l2.setBounds(40, jj, 360, HEIGHT);
        JButton delete = new JButton("X");
        delete.setFont(new Font("Arial", Font.PLAIN, 6));

        delete.setBounds(450, jj, 10, HEIGHT);

        JLabel j2 = new JLabel("--------------");
        j2.setBounds(40, yy, 360, HEIGHT);

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (deleted) {
                    jScrollPane.remove(j2);
                    delete.setText("V");
                    deleted = true;
                    l.setForeground(Color.RED);
                } else {

                    jScrollPane.add(j2);
                    delete.setText("X");
                    deleted = false;
                    l.setForeground(Color.BLACK);
                }
            }
        });
        jScrollPane.add(delete);
        jScrollPane.add(l);
        jScrollPane.add(l2);

        second.invalidate();
        second.validate();
        second.repaint();
    }

    public String getS1() {
        return this.s1;
    }

    public String getS2() {
        return this.s2;
    }

    public boolean getDeleted() {
        return deleted;
    }
}
