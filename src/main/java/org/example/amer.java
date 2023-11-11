package org.example;



import java.awt.*;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;

public class amer {

    private JFrame frame;
    private JTextField txtResult;
    private JTextField txtAmount;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    amer window = new amer();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public amer() throws IOException{
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() throws IOException {
        String url_str = "https://open.er-api.com/v6/latest";
        dataCollector data = new dataCollector();
        JsonObject jsData = data.getJObject(url_str);
        ArrayList<String> curCode = data.getCode(jsData);
        double[] rate = data.getRate(jsData,curCode);
        String[] arCurData = curCode.toArray(new String[0]);


        frame = new JFrame();
        frame.getContentPane().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        frame.getContentPane().setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        frame.setBounds(100, 100, 304, 281);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new GridLayout(0,1,0,0));

        JLabel lbFrom = new JLabel("from");
        lbFrom.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbFrom.setHorizontalAlignment(SwingConstants.CENTER);
        frame.getContentPane().add(lbFrom);

        JComboBox boxFrom = new JComboBox();
        boxFrom.setModel(new DefaultComboBoxModel(arCurData));
        boxFrom.setMaximumSize(new Dimension(100, 20));
        frame.getContentPane().add(boxFrom);

        txtAmount = new JTextField();
        txtAmount.setHorizontalAlignment(SwingConstants.CENTER);

        txtAmount.setName("amount");
        txtAmount.setMaximumSize(new Dimension(100, 22));
        frame.getContentPane().add(txtAmount);
        txtAmount.setColumns(10);

        JLabel lblTo = new JLabel("to");
        lblTo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTo.setAlignmentX(0.5f);
        frame.getContentPane().add(lblTo);

        JComboBox boxTo = new JComboBox();
        boxTo.setMaximumSize(new Dimension(100, 20));
        boxTo.setModel(new DefaultComboBoxModel(arCurData));
        frame.getContentPane().add(boxTo);

        JButton btConvert = new JButton("convert");

        btConvert.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().add(btConvert);

        txtResult = new JTextField();
        txtResult.setHorizontalAlignment(SwingConstants.CENTER);
        txtResult.setAlignmentY(Component.CENTER_ALIGNMENT);
        txtResult.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtResult.setForeground(new Color(0, 0, 0));
        txtResult.setBackground(new Color(46, 139, 87));
        txtResult.setMaximumSize(new Dimension(100, 22));
        txtResult.setText("Result");
        txtResult.setEditable(false);
        frame.getContentPane().add(txtResult);
        txtResult.setColumns(10);


        btConvert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String from = (String) boxFrom.getSelectedItem();
                String to = (String) boxTo.getSelectedItem();

                double amount = Double.parseDouble(txtAmount.getText());
                double result = convertor(from,to,amount,rate,curCode);
                String re = String.format("%.2f",result);
                txtResult.setText(re);


            }
        });
    }

    public static double convertor(String from , String to ,double amount, double[] rate , ArrayList<String> arr){

        double a = rate[arr.indexOf(from)];

        return (amount/a)*rate[arr.indexOf(to)];

    }

}
