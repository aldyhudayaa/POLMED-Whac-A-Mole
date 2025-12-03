import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class WhacAMole extends JFrame {
    int boardwidth = 600;
    int boardheight =  650;
    JFrame frame = new JFrame("Polmed: Whac A Mole");
    musik Musik = new musik();

    JLabel txtLabel = new JLabel();
    JPanel txtPanel = new JPanel();
    JPanel panel = new JPanel();
    JButton[] tombol = new JButton[9];

    //game menu (JANGAN LUPA)
    JLabel txtMenu = new JLabel();
    JLabel txtNama = new JLabel();
    JPanel MenuPanel = new JPanel();
    JLabel icon = new JLabel();

    ImageIcon mole;
    ImageIcon polmed;
    ImageIcon background;
    ImageIcon icongamenya;

    JButton moleskrg;
    JButton polmedskrg;
    int score = 0;
    Timer setmoletimer;
    Timer setpolmedtimer;
    Random random = new Random();

    Image cursorimg = new ImageIcon(getClass().getResource("image/Cursor.png")).getImage();
    Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorimg, new Point(0,0), "Cursor beda");
    
    WhacAMole(){
        frame.setSize(boardwidth,boardheight);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        txtLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        txtLabel.setHorizontalAlignment(JLabel.CENTER);
        txtLabel.setOpaque(true);
        
        txtPanel.setLayout(new BorderLayout());
        txtPanel.add(txtLabel); 
        frame.add(txtPanel, BorderLayout.NORTH);

        panel.setLayout(new GridLayout(3,3));
        frame.add(panel);
        
        gamemenu();
        frame.setVisible(true);
    }

    private void loadinggambar(){
        Image moleimg = new ImageIcon(getClass().getResource("image/Mole.png")).getImage();
        mole = new ImageIcon(moleimg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
        Image polmedimg = new ImageIcon(getClass().getResource("image/Polmed.png")).getImage();
        polmed = new ImageIcon(polmedimg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
        Image backgroundimg = new ImageIcon(getClass().getResource("image/Background.png")).getImage();
        background = new ImageIcon(backgroundimg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
        Image iconimg = new ImageIcon(getClass().getResource("image/Icon.png")).getImage();
        icongamenya = new ImageIcon(iconimg.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH));
    }

    private void gamemenu(){
        loadinggambar();
        frame.getContentPane().removeAll();
        musikplay(0);
        
        String text = "POLMED: Whac A Mole";
        txtMenu = tulisanklasik(text);
        txtMenu.setFont(new Font("Monospaced", Font.BOLD, 40));
        txtMenu.setOpaque(true);
        txtMenu.setBackground(Color.BLACK);
        txtMenu.setForeground(Color.WHITE); 
        txtMenu.setHorizontalAlignment(JLabel.CENTER);
        frame.add(txtMenu, BorderLayout.NORTH);

        String textaldy = "Muhammad Aldy Hudaya 2405181048";
        txtMenu = tulisanklasik(textaldy);
        txtMenu.setFont(new Font("Monospaced", Font.BOLD, 20));
        txtMenu.setOpaque(false);
        txtMenu.setBackground(Color.BLACK);
        txtMenu.setForeground(Color.WHITE); 
        txtMenu.setHorizontalAlignment(JLabel.CENTER);
        frame.add(txtMenu, BorderLayout.SOUTH);
        
        MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.Y_AXIS));
        MenuPanel.setBackground(Color.BLACK);
        MenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        MenuPanel.removeAll();
        icon.setIcon(icongamenya);
        icon.setAlignmentY(Component.CENTER_ALIGNMENT);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton tombolmasuk = tombolretro("Masuk");
        JButton tombolkeluar = tombolretro("Keluar");
        tombolmasuk.addActionListener(e -> inisiasigame());
        tombolkeluar.addActionListener(e -> System.exit(0));

        //ini utk add
        MenuPanel.add(Box.createVerticalStrut(30)); 
        MenuPanel.add(icon);
        MenuPanel.add(Box.createVerticalStrut(80)); 
        MenuPanel.add(tombolmasuk);
        MenuPanel.add(Box.createVerticalStrut(20)); 
        MenuPanel.add(tombolkeluar);
        MenuPanel.add(Box.createVerticalStrut(30));

        frame.add(MenuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        
    }

    private JButton tombolretro(String txt){
        JButton btn = new JButton(txt){
        @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(Color.GRAY); 
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
                
                g2d.drawString(getText(), x +2, y+2); //(INGAT) utk ngubah shadow

                g2d.setColor(getForeground());
                g2d.drawString(getText(), x, y);
                g2d.dispose();
            }
        };
        btn.addMouseListener(new MouseAdapter() {
           @Override
            public void mouseEntered(MouseEvent e){
                btn.setText("> " + txt + " <");
                efek(2);
            };
            public void mouseExited(MouseEvent e){
                btn.setText(txt);
            };
        });
        btn.setFont(new Font("Monospaced", Font.BOLD, 24));
        btn.setForeground(Color.WHITE);
        btn.setBackground(Color.BLACK);
        btn.setFocusable(false);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        
        return btn;
        }

    private JLabel tulisanklasik(String txt){
        JLabel txtklasik = new JLabel(txt);
        txtklasik = new JLabel(txt) {
        @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());

                g2d.setColor(Color.GRAY); 
                g2d.setFont(getFont());
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
               
                g2d.drawString(getText(), x +3, y+3); //(INGAT) utk ngubah shadow

                g2d.setColor(getForeground());
                g2d.drawString(getText(), x, y);
                g2d.dispose();
            }
        };
        return txtklasik;
        
    }
    
    private void inisiasigame(){
        loadinggambar();
        stopsemua();
        musikplay(3);
        frame.getContentPane().removeAll();
        frame.setLayout(new BorderLayout());
        txtLabel.setFont(new Font("Monospaced", Font.BOLD, 50));
        txtLabel.setHorizontalAlignment(JLabel.CENTER);
        txtLabel.setOpaque(true);
        
        txtPanel.setLayout(new BorderLayout());
        txtPanel.add(txtLabel); 
        frame.add(txtPanel, BorderLayout.NORTH);

        panel.setLayout(new GridLayout(3,3));
        frame.add(panel);
        txtPanel.setLayout(new BorderLayout());
        txtPanel.add(txtLabel); 
        frame.add(txtPanel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(3,3));
        frame.add(panel);
        panel.setCursor(cursor);
        panel.removeAll();
        
        for (int i = 0; i <9; i++){
            JButton kotak = new JButton();
            tombol[i] = kotak;
            panel.add(kotak);
            kotak.setIcon(background); 

            kotak.setFocusable(false);
            kotak.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JButton kotak = (JButton) e.getSource();
                    if (kotak == moleskrg){
                        score += 10;
                        txtLabel.setText("Score: " + Integer.toString(score));
                        
                    } else if (kotak == polmedskrg){
                        stopsemua();
                        efek(4);
                        score = 0;
                        txtLabel.setText("Hahaha kalah bah");
                        setmoletimer.stop();
                        setpolmedtimer.stop();
                        kotak.setBackground(Color.GRAY);
                        kotak.setForeground(Color.GRAY);
                        for (int i = 0; i <9; i++){
                            tombol[i].setEnabled(false);
                        }
                        Timer balikmenu = new Timer(4000, ev -> mainlagi());
                        balikmenu.setRepeats(false);
                        balikmenu.start();
                    }
                }
            });
        }
        txtLabel.setText("Score: " + Integer.toString(score));
        
        setmoletimer = new Timer(700, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (moleskrg != null) {
                    moleskrg.setIcon(background);
                    moleskrg = null;
                } 
                int no = random.nextInt(9);
                JButton kotak = tombol[no];

                if (polmedskrg == kotak)return;

                moleskrg = kotak;
                moleskrg.setIcon(mole);
            } 
            
        });

        setpolmedtimer = new Timer(750, new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if (polmedskrg != null) {
                    polmedskrg.setIcon(background);
                    polmedskrg = null;
                } 
                int no = random.nextInt(9);
                JButton kotak = tombol[no];

                if (moleskrg == kotak)return;
                polmedskrg = kotak;
                polmedskrg.setIcon(polmed);
            } 
            
        });
        
        
        setpolmedtimer.start();
        setmoletimer.start();
        frame.revalidate();
        frame.repaint();
    }

    private void mainlagi(){
        loadinggambar();
        stopsemua();
        musikplay(1);
        frame.getContentPane().removeAll();
        
        String text = "Yakin mau main lagi?";
        txtMenu = tulisanklasik(text);
        txtMenu.setFont(new Font("Monospaced", Font.BOLD, 40));
        txtMenu.setOpaque(true);
        txtMenu.setBackground(Color.BLACK);
        txtMenu.setForeground(Color.WHITE); 
        txtMenu.setHorizontalAlignment(JLabel.CENTER);
        frame.add(txtMenu, BorderLayout.NORTH);

        MenuPanel.setLayout(new BoxLayout(MenuPanel, BoxLayout.Y_AXIS));
        MenuPanel.setBackground(Color.BLACK);
        MenuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        MenuPanel.removeAll();
        icon.setIcon(icongamenya);
        icon.setAlignmentY(Component.CENTER_ALIGNMENT);
        icon.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton tombolmasuk = tombolretro("Main Lagi?");
        JButton tombolkeluar = tombolretro("Keluar");
        tombolmasuk.addActionListener(e -> inisiasigame());
        tombolkeluar.addActionListener(e -> System.exit(0));

        //ini utk add
        MenuPanel.add(Box.createVerticalStrut(30)); 
        MenuPanel.add(icon);
        MenuPanel.add(Box.createVerticalStrut(80)); 
        MenuPanel.add(tombolmasuk);
        MenuPanel.add(Box.createVerticalStrut(20)); 
        MenuPanel.add(tombolkeluar);
        MenuPanel.add(Box.createVerticalStrut(30));

        frame.add(MenuPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        
    }

    public void musikplay(int i){
        Musik.setFile(i);
        Musik.play(i);
        Musik.loop(i);
    }

    public void musikstop(int i){
        Musik.stop(i);
    }

    public void efek(int i){
        Musik.setFile(i);
        Musik.play(i);
    }

    public void stopsemua(){
        Musik.stopAll();
    }
}