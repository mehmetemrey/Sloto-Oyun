package com.sansoyun;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SayisalLoto extends JFrame implements ActionListener {

    private final List<JButton> numberButtons = new ArrayList<>();
    private final JTextField txtNumbers = new JTextField(18);
    private final JTextField txtBet = new JTextField("10", 6);
    private final JLabel lblWinningNumbers = new JLabel("Kazanan Numaralar: -");
    private final JLabel lblSelectedNumbers = new JLabel("Seçilen Numaralar: -");
    private final JButton btnPlay = new JButton("Çekilişi Yap");

    public SayisalLoto() {
        super("Sayısal Loto");
        initUI();
    }

    private void initUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel topPanel = new JPanel(new GridLayout(1, 2, 8, 8));
        topPanel.add(lblWinningNumbers);
        topPanel.add(lblSelectedNumbers);

        JPanel gridPanel = new JPanel(new GridLayout(7, 7, 5, 5));
        for (int i = 1; i <= 49; i++) {
            JButton btn = new JButton(String.valueOf(i));
            numberButtons.add(btn);
            gridPanel.add(btn);
        }

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(new JLabel("Seçilen Sayılar:"));
        bottomPanel.add(txtNumbers);
        bottomPanel.add(new JLabel("Bahis:"));
        bottomPanel.add(txtBet);
        bottomPanel.add(btnPlay);
        btnPlay.addActionListener(this);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

        setSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SayisalLoto().setVisible(true));
    }
}
