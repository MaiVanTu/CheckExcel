package jp.co.run.tool.gui;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.UIManager;

import jp.co.run.tool.application.Application;
import jp.co.run.tool.common.CommonProperties;
import jp.co.run.tool.common.CommonUtils;

public class ToolGUI {

    private JFrame frmToolinternal;
    private JTextField txtFtoolcheckexceldataCopy;
    private JComboBox cbxFont;
    private JComboBox cbxFormat;
    private JComboBox cbxSize;
    private JCheckBox chckbxVietnamese;
    private JCheckBox chckbxFont;
    private JCheckBox chckbxFormat;
    private JCheckBox chckbxSize;
    private JCheckBox chckbxSheet;
    private JCheckBox chckbxCell;
    private JProgressBar progressBar;
    private JFileChooser chooser;
    private String choosertitle;
    /** The list of possible font sizes. */
    private static final Integer[] SIZES =
            {8, 9, 10, 11, 12, 13, 14, 16, 18, 20, 24, 26, 28, 32, 36, 40, 48, 56, 64, 72};
    /** The list of possible fonts. */
    private static final String[] FONTS = GraphicsEnvironment.getLocalGraphicsEnvironment()
            .getAvailableFontFamilyNames();
    /** The list of possible format date. */
    private static final String[] FORMAT_DATE = {"yyyy/mm/dd", "yyyy/dd/mm", "dd/mm/yyyy"};

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    ToolGUI window = new ToolGUI();
                    window.frmToolinternal.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ToolGUI() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frmToolinternal = new JFrame();
        frmToolinternal.setResizable(false);
        frmToolinternal.setTitle("ToolInternal");
        // Change the icon image
        URL url = ToolGUI.class.getResource("/logo.png");
        ImageIcon img = new ImageIcon(url);
        frmToolinternal.setIconImage(img.getImage());
        frmToolinternal.setBounds(100, 100, 535, 334);
        frmToolinternal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmToolinternal.getContentPane().setLayout(null);

        txtFtoolcheckexceldataCopy = new JTextField();
        txtFtoolcheckexceldataCopy.setBounds(60, 68, 409, 26);
        frmToolinternal.getContentPane().add(txtFtoolcheckexceldataCopy);
        txtFtoolcheckexceldataCopy.setColumns(10);

        JButton btnChooseFile = new JButton("Choose File");
        btnChooseFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File(txtFtoolcheckexceldataCopy.getText()));
                chooser.setDialogTitle(choosertitle);
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //
                // disable the "All files" option.
                //
                chooser.setAcceptAllFileFilterUsed(false);
                //
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                  System.out.println("getCurrentDirectory(): "
                     +  chooser.getCurrentDirectory());
                  System.out.println("getSelectedFile() : "
                     +  chooser.getSelectedFile());
                  txtFtoolcheckexceldataCopy.setText(chooser.getSelectedFile().toString());
                  }
                else {
                  System.out.println("No Selection ");
                  }
            }
        });
        btnChooseFile.setBounds(479, 67, 30, 28);
        frmToolinternal.getContentPane().add(btnChooseFile);

        JLabel lblCheckFormatExcel = new JLabel("Check Format Excel File");
        lblCheckFormatExcel.setFont(new Font("Yu Gothic UI Light", Font.BOLD, 17));
        lblCheckFormatExcel.setBounds(119, 21, 202, 29);
        frmToolinternal.getContentPane().add(lblCheckFormatExcel);

        JButton btnStart = new JButton("Start");
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        progressBar.setVisible(true);
                        try {
                            progressBar.setValue(10);
                            // Get all item on screen
                            String path = txtFtoolcheckexceldataCopy.getText();
                            String font = cbxFont.getSelectedItem().toString();
                            String format = cbxFormat.getSelectedItem().toString();
                            int size = Integer.parseInt(cbxSize.getSelectedItem().toString());
                            boolean isVietnameseChecked = chckbxVietnamese.isSelected();
                            boolean isFontChecked = chckbxFont.isSelected();
                            boolean isFormatChecked = chckbxFont.isSelected();
                            boolean isSizeChecked = chckbxSize.isSelected();
                            boolean isSheetChecked = chckbxSheet.isSelected();
                            boolean isCellChecked = chckbxCell.isSelected();

                            // Set cell properties
                            CommonProperties.setFontCell(font);
                            CommonProperties.setFormatCell(format);
                            CommonProperties.setFontSizeCell(size);
                            CommonProperties.setVietnameseChecked(isVietnameseChecked);
                            CommonProperties.setFontChecked(isFontChecked);
                            CommonProperties.setFormatChecked(isFormatChecked);
                            CommonProperties.setSizeChecked(isSizeChecked);
                            CommonProperties.setSheetChecked(isSheetChecked);
                            CommonProperties.setCellChecked(isCellChecked);
                            System.out.println("Path: " + path + "\nFont: " + CommonProperties.getFontCell() + "\nFormat: " + CommonProperties.getFormatCell() + "\nSize: " + CommonProperties.getFontSizeCell());

                            progressBar.setValue(30);
                            // Check font, format, size and character Vietnamese
                            if (CommonUtils.isDirectory(path)) {
                                File folder = new File(path);
                                if (CommonUtils.isReady(folder)) {
                                    progressBar.setValue(60);
                                    Application.getData(path);
                                    progressBar.setValue(100);
                                    // Confirm before open result
                                    int dialogResult = JOptionPane.showConfirmDialog (frmToolinternal, "Would You Like To Open Result File?", "Confirmation Message", JOptionPane.YES_NO_OPTION);
                                    if(dialogResult == JOptionPane.YES_OPTION){
                                        Desktop.getDesktop().open(new File("..\\"));
                                    }

                                    //========== Feature fixes file excel ===========
//                                    int dialogRepairDocument = JOptionPane.showConfirmDialog (frmToolinternal, "Would You Like To Repair Document", "Confirmation Message", JOptionPane.YES_NO_OPTION);
//                                    if(dialogRepairDocument == JOptionPane.YES_OPTION){
//                                        Application.repairDocument(path);
//                                        Desktop.getDesktop().open(new File("..\\"));
//                                    }

                                } else {
                                    JOptionPane.showMessageDialog(frmToolinternal,
                                            "Please close all file in folder and restart tool !!!",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(frmToolinternal,
                                        "Directory isn't exist!!!",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (ParseException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            JOptionPane.showMessageDialog(frmToolinternal,
                                    "Can't auto open result. Please open manually !!!",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
//                            Thread.sleep(2000); // just for the sake of example
                        progressBar.setVisible(false);
                    }

                }).start();
//                try {
//                    // Get all item on screen
//                    String path = txtFtoolcheckexceldataCopy.getText();
//                    String font = cbxFont.getSelectedItem().toString();
//                    String format = cbxFormat.getSelectedItem().toString();
//                    int size = Integer.parseInt(cbxSize.getSelectedItem().toString());
//
//                    // Set cell properties
//                    CommonProperties.setFontCell(font);
//                    CommonProperties.setFormatCell(format);
//                    CommonProperties.setFontSizeCell(size);
//                    System.out.println("Path: " + path + "\nFont: " + CommonProperties.getFontCell() + "\nFormat: " + CommonProperties.getFormatCell() + "\nSize: " + CommonProperties.getFontSizeCell());
//
//                    // Check font, format, size and character Vietnamese
//                    if (CommonUtils.isDirectory(path)) {
//                        File folder = new File(path);
//                        if (CommonUtils.isReady(folder)) {
//                            Application.getData(path);
//                            Desktop.getDesktop().open(new File("..\\"));
//                        } else {
//                            JOptionPane.showMessageDialog(frmToolinternal,
//                                    "Please close all file in folder and restart tool !!!",
//                                    "Error",
//                                    JOptionPane.ERROR_MESSAGE);
//                        }
//                    } else {
//                        JOptionPane.showMessageDialog(frmToolinternal,
//                                "Directory isn't exist!!!",
//                                "Error",
//                                JOptionPane.ERROR_MESSAGE);
//                    }
//                } catch (ParseException e1) {
//                    // TODO Auto-generated catch block
//                    e1.printStackTrace();
//                } catch (Exception e1) {
//                    // TODO Auto-generated catch block
//                    JOptionPane.showMessageDialog(frmToolinternal,
//                            "Can't auto open result. Please open manually !!!",
//                            "Error",
//                            JOptionPane.ERROR_MESSAGE);
//                }
            }
        });
        btnStart.setBounds(16, 250, 109, 32);
        frmToolinternal.getContentPane().add(btnStart);

        JButton btnResult = new JButton("Result ");
        btnResult.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().open(new File("..\\"));
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    JOptionPane.showMessageDialog(frmToolinternal,
                            "Can't auto open result. Please open manually !!!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnResult.setBounds(139, 250, 109, 32);
        frmToolinternal.getContentPane().add(btnResult);

        cbxFont = new JComboBox(FONTS);
        cbxFont.setSelectedIndex(CommonUtils.findIndex(FONTS, "Times New Roman"));
        cbxFont.setBounds(60, 105, 261, 26);
        frmToolinternal.getContentPane().add(cbxFont);

        JLabel lblFont = new JLabel("Font:");
        lblFont.setBounds(24, 111, 30, 14);
        frmToolinternal.getContentPane().add(lblFont);

        JLabel lblFolder = new JLabel("Folder:");
        lblFolder.setBounds(16, 74, 34, 14);
        frmToolinternal.getContentPane().add(lblFolder);

        cbxSize = new JComboBox(SIZES);
        cbxSize.setSelectedIndex(4);
        cbxSize.setBounds(60, 177, 40, 26);
        frmToolinternal.getContentPane().add(cbxSize);

        JLabel lblSize = new JLabel("Size:");
        lblSize.setBounds(27, 183, 26, 14);
        frmToolinternal.getContentPane().add(lblSize);

        cbxFormat = new JComboBox(FORMAT_DATE);
        cbxFormat.setBounds(60, 142, 261, 26);
        frmToolinternal.getContentPane().add(cbxFormat);

        JLabel lblFormat = new JLabel("Format:");
        lblFormat.setBounds(12, 148, 40, 14);
        frmToolinternal.getContentPane().add(lblFormat);

        progressBar = new JProgressBar();
        progressBar.setBounds(260, 250, 249, 32);
        progressBar.setVisible(false);
        frmToolinternal.getContentPane().add(progressBar);

        chckbxVietnamese = new JCheckBox("");
        chckbxVietnamese.setSelected(true);
        chckbxVietnamese.setBounds(125, 214, 21, 23);
        frmToolinternal.getContentPane().add(chckbxVietnamese);

        JLabel lblVietnameseChecked = new JLabel("Vietnamese Checked:");
        lblVietnameseChecked.setBounds(16, 217, 103, 14);
        frmToolinternal.getContentPane().add(lblVietnameseChecked);

        JLabel lblFormatChecked = new JLabel("Format Checked:");
        lblFormatChecked.setBounds(360, 148, 82, 14);
        frmToolinternal.getContentPane().add(lblFormatChecked);

        chckbxFormat = new JCheckBox("");
        chckbxFormat.setSelected(true);
        chckbxFormat.setBounds(448, 145, 21, 23);
        frmToolinternal.getContentPane().add(chckbxFormat);

        JLabel lblFontChecked = new JLabel("Font Checked:");
        lblFontChecked.setBounds(372, 111, 70, 14);
        frmToolinternal.getContentPane().add(lblFontChecked);

        chckbxFont = new JCheckBox("");
        chckbxFont.setSelected(true);
        chckbxFont.setBounds(448, 108, 21, 23);
        frmToolinternal.getContentPane().add(chckbxFont);

        JLabel lblSizeChecked = new JLabel("Size Checked:");
        lblSizeChecked.setBounds(375, 183, 67, 14);
        frmToolinternal.getContentPane().add(lblSizeChecked);

        chckbxSize = new JCheckBox("");
        chckbxSize.setSelected(true);
        chckbxSize.setBounds(448, 180, 21, 23);
        frmToolinternal.getContentPane().add(chckbxSize);

        JLabel lblActiveSheeetChecked = new JLabel("Active Sheet Checked:");
        lblActiveSheeetChecked.setBounds(188, 218, 109, 14);
        frmToolinternal.getContentPane().add(lblActiveSheeetChecked);

        chckbxSheet = new JCheckBox("");
        chckbxSheet.setSelected(true);
        chckbxSheet.setBounds(300, 214, 21, 23);
        frmToolinternal.getContentPane().add(chckbxSheet);

        JLabel lblActiveCellChecked = new JLabel("Active Cell Checked:");
        lblActiveCellChecked.setBounds(344, 217, 98, 14);
        frmToolinternal.getContentPane().add(lblActiveCellChecked);

        chckbxCell = new JCheckBox("");
        chckbxCell.setSelected(true);
        chckbxCell.setBounds(448, 214, 21, 23);
        frmToolinternal.getContentPane().add(chckbxCell);
    }
}
