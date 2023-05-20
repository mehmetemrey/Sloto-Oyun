package com.sansoyun;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SayisalLoto extends JFrame implements ActionListener {

    private final Color colorDefault = new Color(240, 240, 240);
    private final Color colorSelected = new Color(100, 149, 237);

    private final List<JButton> numberButtons = new ArrayList<>();
    private final Set<Integer> selectedNumbers = new HashSet<>();
    private final JTextField txtNumbers = new JTextField(18);
    private final JTextField txtBet = new JTextField("10", 6);

    private final JLabel lblWinningNumbers = new JLabel("Kazanan Numaralar: -");
    private final JLabel lblSelectedNumbers = new JLabel("Seçilen Numaralar: -");

    private final JButton btnPlay = new JButton("Çekilişi Yap");
    private final JButton btnRandom = new JButton("Rastgele Seç");
    private final JButton btnClear = new JButton("Temizle");

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
            final int num = i;
            JButton btn = new JButton(String.valueOf(num));
            btn.setBackground(colorDefault);
            btn.addActionListener(e -> toggleNumber(num, btn));
            numberButtons.add(btn);
            gridPanel.add(btn);
        }

        JPanel bottomContainer = new JPanel(new BorderLayout(5, 5));
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Seçilen Sayılar:"));
        inputPanel.add(txtNumbers);
        inputPanel.add(new JLabel("Bahis (TL):"));
        inputPanel.add(txtBet);

        JPanel actionPanel = new JPanel(new FlowLayout());
        btnRandom.addActionListener(this);
        btnClear.addActionListener(this);
        btnPlay.addActionListener(this);
        actionPanel.add(btnRandom);
        actionPanel.add(btnClear);
        actionPanel.add(btnPlay);

        bottomContainer.add(inputPanel, BorderLayout.NORTH);
        bottomContainer.add(actionPanel, BorderLayout.SOUTH);

        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(gridPanel, BorderLayout.CENTER);
        getContentPane().add(bottomContainer, BorderLayout.SOUTH);

        setSize(new Dimension(850, 600));
        setLocationRelativeTo(null);
    }

    private void toggleNumber(int number, JButton btn) {
        if (selectedNumbers.contains(number)) {
            selectedNumbers.remove(number);
            btn.setBackground(colorDefault);
        } else {
            if (selectedNumbers.size() >= 6) {
                JOptionPane.showMessageDialog(this, "En fazla 6 numara seçebilirsiniz.");
                return;
            }
            selectedNumbers.add(number);
            btn.setBackground(colorSelected);
        }
        updateInputText();
    }

    private void updateInputText() {
        List<Integer> list = new ArrayList<>(selectedNumbers);
        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
            if (i < list.size() - 1) sb.append(" ");
        }
        txtNumbers.setText(sb.toString());
    }

    private boolean parseInput() {
        String input = txtNumbers.getText().trim();
        if (input.isEmpty()) return false;
        String[] parts = input.split("[,\\s-]+");
        Set<Integer> parsed = new HashSet<>();
        for (String part : parts) {
            if (part.isEmpty()) continue;
            try {
                int val = Integer.parseInt(part);
                if (val < 1 || val > 49) return false;
                parsed.add(val);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        if (parsed.size() != 6) {
            JOptionPane.showMessageDialog(this, "Lütfen 6 farklı numara seçin.");
            return false;
        }
        selectedNumbers.clear();
        selectedNumbers.addAll(parsed);
        updateInputText();
        return true;
    }

    private void selectRandom() {
        selectedNumbers.clear();
        Random rnd = new Random();
        while (selectedNumbers.size() < 6) {
            selectedNumbers.add(rnd.nextInt(49) + 1);
        }
        updateInputText();
    }

    private void clear() {
        selectedNumbers.clear();
        txtNumbers.setText("");
        lblWinningNumbers.setText("Kazanan Numaralar: -");
        lblSelectedNumbers.setText("Seçilen Numaralar: -");
    }

    private void play() {
        if (!parseInput()) return;
        Set<Integer> winningSet = new HashSet<>();
        Random rnd = new Random();
        while (winningSet.size() < 6) {
            winningSet.add(rnd.nextInt(49) + 1);
        }
        List<Integer> winningList = new ArrayList<>(winningSet);
        Collections.sort(winningList);
        lblWinningNumbers.setText("Kazanan Numaralar: " + winningList);
        lblSelectedNumbers.setText("Seçilen Numaralar: " + selectedNumbers);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnPlay) play();
        else if (e.getSource() == btnRandom) selectRandom();
        else if (e.getSource() == btnClear) clear();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SayisalLoto().setVisible(true));
    }
}
