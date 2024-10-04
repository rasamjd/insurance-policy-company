/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package java2.pkg2;

import javax.swing.JOptionPane;

/**
 *
 * @author LENOVO
 */
public class UpdateUI extends javax.swing.JFrame {

    UserUI userUI;
    InsurancePolicy policy;

    /** Creates new form UpdateUI */
    public UpdateUI(UserUI userUI, InsurancePolicy policy) {
        this.userUI = userUI;
        this.policy = policy;
        initComponents();
    }

    public void run() {
        this.setVisible(true);

        holderNameInUpdateTextField.setText(policy.getPolicyHolderName());
        modelInUpdateTextField.setText(policy.getCar().getModel());
        mYearInUpdateTextField.setText("" + policy.getCar().getManufacturingYear());
        priceInUpdateTextField.setText("" + policy.getCar().getPrice());
        typeInUpdateComboBox.setSelectedItem(policy.getCar().getType().toString());
        yearInUpdateTextField.setText("" + policy.getExpiryDate().getYear());
        monthInUpdateTextField.setText("" + policy.getExpiryDate().getMonth());
        dayInUpdateTextField.setText("" + policy.getExpiryDate().getDay());
        numOfClaimsInUpdateTextField.setText("" + policy.getNumberOfClaims());
        commentsInUpdateTextArea.setText("");
        levelInUpdateTextField.setText("0");
        driverAgeInUpdateTextField.setText("0");

        for (CarType type: CarType.values()) {
            typeInUpdateComboBox.addItem(type.toString());
        }
        typeInUpdateComboBox.setSelectedItem(policy.getCar().getType());

        updateTitleLabel.setText("Update Policy: " + policy.getId());

        if (policy instanceof ThirdPartyPolicy) {
            policyTypeLabel.setText("Third Party Policy");
            commentsInUpdateTextArea.setText(((ThirdPartyPolicy)policy).getComments());
            driverAgeInUpdateTextField.setVisible(false);
            driverAgeInUpdateLabel.setVisible(false);
            levelInUpdateTextField.setVisible(false);
            levelInUpdateLabel.setVisible(false);            
        } else {
            policyTypeLabel.setText("Comprehensive Policy");
            driverAgeInUpdateTextField.setText("" + ((ComprehensivePolicy)policy).getDriverAge());
            levelInUpdateTextField.setText("" + ((ComprehensivePolicy)policy).getLevel());
            commentsInUpdateTextArea.setVisible(false);
            commentsInUpdateLabel.setVisible(false);
        }
    }

    public String validatePolicyData() {
        String out = "";
        if (Double.parseDouble(mYearInUpdateTextField.getText()) < 1950 || Double.parseDouble(mYearInUpdateTextField.getText()) > 2030)
            out = "error: Enter a Manufacturing Year.";
        else if (Double.parseDouble(priceInUpdateTextField.getText()) < 0 || Double.parseDouble(priceInUpdateTextField.getText()) > 10000000)
            out = "error: Enter a valid Price.";
        else if (Double.parseDouble(yearInUpdateTextField.getText()) < 2000 || Double.parseDouble(yearInUpdateTextField.getText()) > 2050)
            out = "error: Enter a valid Year.";
        else if (Double.parseDouble(monthInUpdateTextField.getText()) < 0 || Double.parseDouble(monthInUpdateTextField.getText()) > 12)
            out = "error: Enter a valid Month.";
        else if (Double.parseDouble(dayInUpdateTextField.getText()) < 0 || Double.parseDouble(dayInUpdateTextField.getText()) > 31)
            out = "error: Enter a valid Day.";
        else if (Double.parseDouble(numOfClaimsInUpdateTextField.getText()) < 0 || Double.parseDouble(numOfClaimsInUpdateTextField.getText()) > 100)
            out = "error: Enter a valid Number of Claims.";
        else if (Double.parseDouble(driverAgeInUpdateTextField.getText()) < 18 || Double.parseDouble(driverAgeInUpdateTextField.getText()) > 100)
            out = "error: Enter a valid Driver Age.";
        else if (Double.parseDouble(levelInUpdateTextField.getText()) < 0 || Double.parseDouble(levelInUpdateTextField.getText()) > 10)
            out = "error: Enter a valid Level.";

        return out;
    }

    public void updatePolicy() {
        try {
            policy.getCar().setModel(modelInUpdateTextField.getText());
            policy.getCar().setManufacturingYear(Integer.parseInt(mYearInUpdateTextField.getText()));
            policy.getCar().setPrice(Double.parseDouble(priceInUpdateTextField.getText()));
            policy.getCar().setType(CarType.valueOf(typeInUpdateComboBox.getSelectedItem().toString()));
            int newYear = Integer.parseInt(yearInUpdateTextField.getText());
            int newMonth = Integer.parseInt(monthInUpdateTextField.getText());
            int newDay = Integer.parseInt(dayInUpdateTextField.getText());
            policy.getExpiryDate().setDate(newYear, newMonth, newDay);
            policy.setNumberOfClaims(Integer.parseInt(numOfClaimsInUpdateTextField.getText()));

            if (policy instanceof ThirdPartyPolicy) { // its a Third Party Policy
                ((ThirdPartyPolicy)policy).setComments("" + commentsInUpdateTextArea.getText());
            } else { // its a Comprehensive Policy
                ((ComprehensivePolicy)policy).setDriverAge(Integer.parseInt(driverAgeInUpdateTextField.getText()));
                ((ComprehensivePolicy)policy).setLevel(Integer.parseInt(levelInUpdateTextField.getText()));
            }
    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numeric data.");
            return;
        }

        if (validatePolicyData().contains("error")) {
            JOptionPane.showMessageDialog(this, validatePolicyData());
            return;
        }

        JOptionPane.showMessageDialog(this, "Policy updated successfully!");
        userUI.fillPoliciesTable();
        this.setVisible(false);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        holderNameInUpdateTextField = new javax.swing.JTextField();
        updateTitleLabel = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        modelInUpdateTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        mYearInUpdateTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        priceInUpdateTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        typeInUpdateComboBox = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        yearInUpdateTextField = new javax.swing.JTextField();
        monthInUpdateTextField = new javax.swing.JTextField();
        dayInUpdateTextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        numOfClaimsInUpdateTextField = new javax.swing.JTextField();
        driverAgeInUpdateLabel = new javax.swing.JLabel();
        driverAgeInUpdateTextField = new javax.swing.JTextField();
        levelInUpdateLabel = new javax.swing.JLabel();
        levelInUpdateTextField = new javax.swing.JTextField();
        commentsInUpdateLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentsInUpdateTextArea = new javax.swing.JTextArea();
        updateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        policyTypeLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel4.setBackground(new java.awt.Color(213, 227, 219));

        jLabel9.setText("Policy Holder Name");

        holderNameInUpdateTextField.setEditable(false);
        holderNameInUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holderNameInUpdateTextFieldActionPerformed(evt);
            }
        });

        updateTitleLabel.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        updateTitleLabel.setText("Update Policy: ");

        jPanel5.setBackground(new java.awt.Color(180, 207, 191));

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel11.setText("Car");

        jLabel12.setText("Model");

        modelInUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelInUpdateTextFieldActionPerformed(evt);
            }
        });

        jLabel13.setText("Manufacturing Year");

        mYearInUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mYearInUpdateTextFieldActionPerformed(evt);
            }
        });

        jLabel14.setText("Price");

        priceInUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceInUpdateTextFieldActionPerformed(evt);
            }
        });

        jLabel16.setText("Type");

        typeInUpdateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeInUpdateComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(modelInUpdateTextField)
                            .addComponent(mYearInUpdateTextField)
                            .addComponent(priceInUpdateTextField)
                            .addComponent(typeInUpdateComboBox, 0, 126, Short.MAX_VALUE))
                        .addGap(22, 22, 22))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(9, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(modelInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(mYearInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(priceInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(typeInUpdateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(180, 207, 191));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel6.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N

        jLabel17.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel17.setText("Expiry Date");

        jLabel18.setText("Year");

        jLabel19.setText("Month");

        jLabel20.setText("Day");

        jLabel21.setText("/");

        jLabel22.setText("/");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(43, 43, 43)
                                .addComponent(jLabel19))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(yearInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(monthInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel22)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(dayInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(yearInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(dayInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22)
                        .addComponent(monthInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(180, 207, 191));

        jLabel23.setText("Number of Claims");

        numOfClaimsInUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numOfClaimsInUpdateTextFieldActionPerformed(evt);
            }
        });

        driverAgeInUpdateLabel.setText("Driver Age");

        driverAgeInUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                driverAgeInUpdateTextFieldActionPerformed(evt);
            }
        });

        levelInUpdateLabel.setText("Level");

        levelInUpdateTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelInUpdateTextFieldActionPerformed(evt);
            }
        });

        commentsInUpdateLabel.setText("Comments");

        commentsInUpdateTextArea.setColumns(20);
        commentsInUpdateTextArea.setRows(5);
        jScrollPane1.setViewportView(commentsInUpdateTextArea);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(driverAgeInUpdateLabel)
                            .addComponent(levelInUpdateLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                                .addComponent(numOfClaimsInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24))
                            .addComponent(driverAgeInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(levelInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(commentsInUpdateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(numOfClaimsInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(driverAgeInUpdateLabel)
                    .addComponent(driverAgeInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(levelInUpdateLabel)
                    .addComponent(levelInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(commentsInUpdateLabel))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        updateButton.setBackground(new java.awt.Color(153, 185, 166));
        updateButton.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        cancelButton.setBackground(new java.awt.Color(185, 210, 195));
        cancelButton.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        policyTypeLabel.setText("Policy Type");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(policyTypeLabel)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(127, 127, 127)
                                .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
                            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(updateTitleLabel)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(117, 117, 117)
                                .addComponent(holderNameInUpdateTextField)))
                        .addContainerGap(37, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(updateTitleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(policyTypeLabel)
                .addGap(14, 14, 14)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(holderNameInUpdateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(updateButton))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jScrollPane3.setViewportView(jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 771, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void holderNameInUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holderNameInUpdateTextFieldActionPerformed
        
    }//GEN-LAST:event_holderNameInUpdateTextFieldActionPerformed

    private void modelInUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelInUpdateTextFieldActionPerformed
        
    }//GEN-LAST:event_modelInUpdateTextFieldActionPerformed

    private void mYearInUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mYearInUpdateTextFieldActionPerformed
        
    }//GEN-LAST:event_mYearInUpdateTextFieldActionPerformed

    private void priceInUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceInUpdateTextFieldActionPerformed
        
    }//GEN-LAST:event_priceInUpdateTextFieldActionPerformed

    private void typeInUpdateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeInUpdateComboBoxActionPerformed
        
    }//GEN-LAST:event_typeInUpdateComboBoxActionPerformed

    private void numOfClaimsInUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numOfClaimsInUpdateTextFieldActionPerformed
        
    }//GEN-LAST:event_numOfClaimsInUpdateTextFieldActionPerformed

    private void driverAgeInUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_driverAgeInUpdateTextFieldActionPerformed
        
    }//GEN-LAST:event_driverAgeInUpdateTextFieldActionPerformed

    private void levelInUpdateTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelInUpdateTextFieldActionPerformed
        
    }//GEN-LAST:event_levelInUpdateTextFieldActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        
        updatePolicy();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        
        this.setVisible(false);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @param args the command line arguments
     */ 

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel commentsInUpdateLabel;
    private javax.swing.JTextArea commentsInUpdateTextArea;
    private javax.swing.JTextField dayInUpdateTextField;
    private javax.swing.JLabel driverAgeInUpdateLabel;
    private javax.swing.JTextField driverAgeInUpdateTextField;
    private javax.swing.JTextField holderNameInUpdateTextField;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel levelInUpdateLabel;
    private javax.swing.JTextField levelInUpdateTextField;
    private javax.swing.JTextField mYearInUpdateTextField;
    private javax.swing.JTextField modelInUpdateTextField;
    private javax.swing.JTextField monthInUpdateTextField;
    private javax.swing.JTextField numOfClaimsInUpdateTextField;
    private javax.swing.JLabel policyTypeLabel;
    private javax.swing.JTextField priceInUpdateTextField;
    private javax.swing.JComboBox<String> typeInUpdateComboBox;
    private javax.swing.JButton updateButton;
    private javax.swing.JLabel updateTitleLabel;
    private javax.swing.JTextField yearInUpdateTextField;
    // End of variables declaration//GEN-END:variables

}
