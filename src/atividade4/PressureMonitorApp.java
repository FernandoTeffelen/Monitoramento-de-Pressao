/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package atividade4;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Fernando T
 */

public class PressureMonitorApp extends JFrame implements ActionListener {
    private JLabel lblDate, lblTime, lblSystolic, lblDiastolic, lblStress;
    private JTextField txtDate, txtTime, txtSystolic, txtDiastolic;
    private JCheckBox chkStress;
    private JButton btnSave;
    private JTextArea txtAreaData;

    public PressureMonitorApp() {
        setTitle("Monitor de Pressão Arterial");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        lblDate = new JLabel("Data:");
        txtDate = new JTextField(10);
        txtDate.setEditable(false);
        lblTime = new JLabel("Hora:");
        txtTime = new JTextField(10);
        txtTime.setEditable(false);
        lblSystolic = new JLabel("Pressão Sistólica:");
        txtSystolic = new JTextField(5);
        lblDiastolic = new JLabel("Pressão Diastólica:");
        txtDiastolic = new JTextField(5);
        lblStress = new JLabel("Estresse:");
        chkStress = new JCheckBox();
        btnSave = new JButton("Salvar");
        btnSave.addActionListener(this);
        txtAreaData = new JTextArea(15, 40);
        txtAreaData.setEditable(false);

        add(lblDate);
        add(txtDate);
        add(lblTime);
        add(txtTime);
        add(lblSystolic);
        add(txtSystolic);
        add(lblDiastolic);
        add(txtDiastolic);
        add(lblStress);
        add(chkStress);
        add(btnSave);
        add(new JScrollPane(txtAreaData));

        setVisible(true);

        // Preenche data e hora atuais
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtDate.setText(now.format(dateFormatter));
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        txtTime.setText(now.format(timeFormatter));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSave) {
            saveData();
        }
    }

    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("pressures.txt", true))) {
            String date = txtDate.getText();
            String time = txtTime.getText();
            String systolic = txtSystolic.getText();
            String diastolic = txtDiastolic.getText();
            String stress = chkStress.isSelected() ? "Sim" : "Não";

            // Validar entrada (apenas para demonstração)
            if (systolic.isEmpty() || diastolic.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira valores para pressão sistólica e diastólica.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int systolicValue = Integer.parseInt(systolic);
            int diastolicValue = Integer.parseInt(diastolic);

            if (systolicValue <= 0 || diastolicValue <= 0) {
                JOptionPane.showMessageDialog(this, "Os valores de pressão devem ser maiores que zero.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            writer.write(date + "," + time + "," + systolic + "," + diastolic + "," + stress);
            writer.newLine();

            // Atualizar JTextArea
            txtAreaData.append("Data: " + date + ", Hora: " + time + ", Pressão Sistólica: " + systolic + ", Pressão Diastólica: " + diastolic + ", Estresse: " + stress + "\n");

            // Limpar campos após salvar
            txtSystolic.setText("");
            txtDiastolic.setText("");
            chkStress.setSelected(false);

            JOptionPane.showMessageDialog(this, "Dados salvos com sucesso.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor, insira valores numéricos válidos para pressão sistólica e diastólica.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(PressureMonitorApp::new);
    }
}

