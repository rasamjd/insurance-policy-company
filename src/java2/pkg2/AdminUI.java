/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package java2.pkg2;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ItemEvent;

/**
 *
 * @author LENOVO
 */
public class AdminUI extends javax.swing.JFrame {

    private InsuranceCompany company;
    private User selectedUser = null;
    private LoginUI login;
    
    /**
     * Creates new form UserUI
     */
    public AdminUI(LoginUI login, InsuranceCompany company) {
        this.company = company;
        this.login = login;
        initComponents();

    }
    
    public void run() {
        this.setVisible(true);
        
        setUsersComboBox();
    }
    
    public void setUsersComboBox() {
        for (User user: company.getUsers().values()) {
            usersComboBox.addItem(user.getUserID() + " - " + user.getName());
        }
    }
    
    public void setData() {
        // edit personal data form
        jTextField1.setText("" + selectedUser.getUserID());
        jTextField3.setText(selectedUser.getName());
        jTextField2.setText(selectedUser.getPassword());
        jTextField5.setText("" + selectedUser.getAddress().getStreetNum());
        jTextField6.setText(selectedUser.getAddress().getStreet());
        jTextField7.setText(selectedUser.getAddress().getSuburb());
        for (String city: company.populateDistinctCityNames()) {
            jComboBox1.addItem(city);
        }
        jComboBox1.setSelectedItem(selectedUser.getAddress().getCity());
        
        // policy add & update form
        for (CarType type: CarType.values()) {
            typeComboBox.addItem(type.toString());
        }
        typeComboBox.setSelectedItem(selectedUser.getAddress().getCity());
        
        // default selected policy in add
        commentsTextArea.setEnabled(true);
        driverAgeTextField.setEnabled(false);
        levelTextField.setEnabled(false);

        // policies information table 
        fillPoliciesTable();
        
        filteredPoliciesTextArea.setText(InsurancePolicy.policiesList(selectedUser.getPolicies()));
    }
    
    public void setSelectedUser() {
        String selectedItem = "" + (String) usersComboBox.getSelectedItem();
        System.out.println(selectedItem);
        for (User user: company.getUsers().values()) {
            if (selectedItem.contains(user.getName())) {
                selectedUser = user;  
                System.out.println("user is not null anymore!");
            }
        }
    }

    public void editData() {
        Tools.printInfo("user before editing:");
        selectedUser.print(50);

        String newPassword = jTextField2.getText();
        
        int newStreetNum =  Integer.parseInt(jTextField5.getText());
        String newStreet = jTextField6.getText();
        String newSuburb = jTextField7.getText();
        String newCity = "";
        if (!jCheckBox1.isSelected()) {
            newCity = jComboBox1.getSelectedItem().toString();
        } else newCity = jTextField8.getText();
        
        Address newAddress = new Address(newStreetNum, newStreet, newSuburb, newCity);

        selectedUser.setAddress(newAddress);
        selectedUser.setPassword(newPassword);

        login.getCompany().save("company.ser");

        Tools.printInfo("user after editing:");
        selectedUser.print(50);
    } 
    
    public void fillPoliciesTable() {
        DefaultTableModel model = (DefaultTableModel) policiesTable.getModel();
        model.setRowCount(0);
        Object rowData[] = new Object[11];
        for (InsurancePolicy policy: selectedUser.getPolicies().values()) {
            rowData[0] = policy.getId();
            rowData[1] = policy.getPolicyHolderName();
            rowData[2] = policy.getCar().getModel();
            rowData[3] = policy.getCar().getType().toString();
            rowData[4] = policy.getCar().getPrice();
            rowData[5] = policy.getCar().getManufacturingYear();
            rowData[6] = policy.getExpiryDate().toString();
            rowData[7] = policy.getNumberOfClaims();
            if (policy instanceof ComprehensivePolicy) {
                rowData[8] = ((ComprehensivePolicy)policy).getLevel();
                rowData[9] = ((ComprehensivePolicy)policy).getDriverAge();
                rowData[10] = "N/A";
            } else {
                rowData[8] = "N/A";
                rowData[9] = "N/A";
                rowData[10] = ((ThirdPartyPolicy)policy).getComments();
            }
            model.addRow(rowData);
        }
    }

    public void handlePolicyTypeChoice() {
        if (comprehensiveRadio.isSelected()) { // it's Comprehensive
            commentsTextArea.setEnabled(false);
            driverAgeTextField.setEnabled(true);
            levelTextField.setEnabled(true);
        } else { // it's Third Party
            commentsTextArea.setEnabled(true);
            driverAgeTextField.setEnabled(false);
            levelTextField.setEnabled(false);
        }
    }

    public String validatePolicyData() {
        String out = "";
        if (Double.parseDouble(mYearTextField.getText()) < 1950 || Double.parseDouble(mYearTextField.getText()) > 2030)
            out = "error: Enter a Manufacturing Year.";
        else if (Double.parseDouble(priceTextField.getText()) < 0 || Double.parseDouble(priceTextField.getText()) > 10000000)
            out = "error: Enter a valid Price.";
        else if (Double.parseDouble(yearTextField.getText()) < 2000 || Double.parseDouble(yearTextField.getText()) > 2050)
            out = "error: Enter a valid Year.";
        else if (Double.parseDouble(monthTextField.getText()) < 0 || Double.parseDouble(monthTextField.getText()) > 12)
            out = "error: Enter a valid Month.";
        else if (Double.parseDouble(dayTextField.getText()) < 0 || Double.parseDouble(dayTextField.getText()) > 31)
            out = "error: Enter a valid Day.";
        else if (Double.parseDouble(numOfClaimsTextField.getText()) < 0 || Double.parseDouble(numOfClaimsTextField.getText()) > 100)
            out = "error: Enter a valid Number of Claims.";
        else if (Double.parseDouble(driverAgeTextField.getText()) < 18 || Double.parseDouble(driverAgeTextField.getText()) > 100)
            out = "error: Enter a valid Driver Age.";
        else if (Double.parseDouble(levelTextField.getText()) < 0 || Double.parseDouble(levelTextField.getText()) > 10)
            out = "error: Enter a valid Level.";

        return out;
    }

    public void addPolicy() {
        String holderName, model;
        int id, manufacturingYear, year, month, day, numOfClaims;
        double price;
        CarType carType; 
        String comments = "";
        int driverAge = 0;
        int level = 0;

        try {
            holderName = holderNameTextField.getText();
            id = Integer.parseInt(idTextField.getText());
            model = modelTextField.getText();
            manufacturingYear = Integer.parseInt(mYearTextField.getText());
            price = Double.parseDouble(priceTextField.getText());
            carType = CarType.valueOf(typeComboBox.getSelectedItem().toString());
            year = Integer.parseInt(yearTextField.getText()); 
            month = Integer.parseInt(monthTextField.getText()); 
            day = Integer.parseInt(dayTextField.getText());
            numOfClaims = Integer.parseInt(numOfClaimsTextField.getText());
            if (thirdPartyRadio.isSelected()) {
                comments = commentsTextArea.getText(); 
            } else {
                driverAge = Integer.parseInt(driverAgeTextField.getText());
                level = Integer.parseInt(levelTextField.getText());    
            }
        
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numeric data.");
            return;
        }

        if (validatePolicyData().contains("error")) {
            JOptionPane.showMessageDialog(this, validatePolicyData());
            return;
        }

        Car car = new Car(model, carType, manufacturingYear, price);
        MyDate expiryDate = new MyDate(year, month, day);
        InsurancePolicy policy = null; // it's set to null to pass the try_catch 

        try {
            if (thirdPartyRadio.isSelected()) { // a Third Party Policy
                policy = ThirdPartyPolicy.createThirdPartyPolicy(holderName, id, car, expiryDate, numOfClaims, comments); 
            } else { // a Comprehensive Policy
                policy = ComprehensivePolicy.createComprehensivePolicy(holderName, id, car, expiryDate, numOfClaims, driverAge, level);
            }   

        } catch (PolicyException e) {
            JOptionPane.showMessageDialog(this, e.toString());            

        } catch (PolicyHolderNameException e) {
            JOptionPane.showMessageDialog(this, e.toString());
            return;
        }

        if (selectedUser.addPolicy(policy) && login.getCompany().save("company.ser")) {
            JOptionPane.showMessageDialog(this, "Policy added successfully!");
            holderNameTextField.setText("");
            idTextField.setText("");
            modelTextField.setText("");
            mYearTextField.setText("");
            priceTextField.setText("");
            typeComboBox.setSelectedIndex(0);
            yearTextField.setText("");
            monthTextField.setText("");
            dayTextField.setText("");
            numOfClaimsTextField.setText("");
            driverAgeTextField.setText("");
            levelTextField.setText("");
            commentsTextArea.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "There was a problem while adding the policy! Try again.");
        }
    }

    public boolean isPolicySelectedInTable() {
        if (policiesTable.getSelectedRow() == -1 || policiesTable.getSelectedColumn() == -1) {
            JOptionPane.showMessageDialog(this, "First choose a policy!");
            return false;
        } 
        return true;
    }

    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel18 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        usersComboBox = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel12 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        holderNameTextField = new javax.swing.JTextField();
        idTextField = new javax.swing.JTextField();
        thirdPartyRadio = new javax.swing.JRadioButton();
        comprehensiveRadio = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        modelTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        mYearTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        priceTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        yearTextField = new javax.swing.JTextField();
        monthTextField = new javax.swing.JTextField();
        dayTextField = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        numOfClaimsTextField = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        driverAgeTextField = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        levelTextField = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentsTextArea = new javax.swing.JTextArea();
        addPolicyButton = new javax.swing.JButton();
        jPanel21 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        findByIdTextField3 = new javax.swing.JTextField();
        findPolicyButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        foundPoliciesTextArea = new javax.swing.JTextArea();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        policiesTable = new javax.swing.JTable();
        deletePolicyBtn = new javax.swing.JButton();
        updatePolicyBtn = new javax.swing.JButton();
        jPanel23 = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        modelFilterTextField = new javax.swing.JTextField();
        modelFilterButton = new javax.swing.JButton();
        jLabel48 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        dayFilterTextField = new javax.swing.JTextField();
        monthFilterTextField = new javax.swing.JTextField();
        yearFilterTextField = new javax.swing.JTextField();
        dateFilterButton = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        filteredPoliciesTextArea = new javax.swing.JTextArea();
        jPanel26 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        sortButton = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        jLabel36.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel36.setText("Choose a user to continue");

        usersComboBox.setName("usersComboBox"); // NOI18N
        usersComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usersComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(usersComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(657, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(jLabel36)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(usersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(369, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Users", jPanel18);

        jLabel1.setText("Username:");

        jLabel2.setText("Password:");

        jLabel3.setText("ID:");

        jLabel4.setText("Address:");

        jLabel5.setText("Street number:");

        jLabel6.setText("Suburb:");

        jLabel7.setText("Street:");

        jLabel8.setText("City:");

        jTextField1.setColumns(6);
        jTextField1.setText("id");
        jTextField1.setEnabled(false);
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setColumns(6);
        jTextField2.setText("password");

        jTextField3.setColumns(6);
        jTextField3.setText("username");
        jTextField3.setEnabled(false);

        jTextField5.setColumns(6);
        jTextField5.setText("street num");

        jTextField6.setColumns(6);
        jTextField6.setText("street");

        jTextField7.setColumns(6);
        jTextField7.setText("suburb");

        jTextField8.setColumns(6);
        jTextField8.setText("new city");
        jTextField8.setEnabled(false);

        jButton1.setText("Apply");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jCheckBox1.setText("new city");
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))))
                .addGap(51, 51, 51)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCheckBox1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(146, 146, 146)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(389, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4))
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(78, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Edit Personal Data", jPanel3);

        jPanel4.setBackground(new java.awt.Color(180, 207, 191));
        jPanel4.setAutoscrolls(true);

        jLabel9.setText("Policy Holder Name");

        jLabel10.setText("Policy ID");

        holderNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                holderNameTextFieldActionPerformed(evt);
            }
        });

        idTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                idTextFieldActionPerformed(evt);
            }
        });

        thirdPartyRadio.setSelected(true);
        thirdPartyRadio.setText("Third Party Policy");
        thirdPartyRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thirdPartyRadioActionPerformed(evt);
            }
        });

        comprehensiveRadio.setText("Comprehensive Policy");
        comprehensiveRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comprehensiveRadioActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(thirdPartyRadio)
                        .addGap(18, 18, 18)
                        .addComponent(comprehensiveRadio)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(117, 117, 117)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(idTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                            .addComponent(holderNameTextField))
                        .addGap(21, 21, 21))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thirdPartyRadio)
                    .addComponent(comprehensiveRadio))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(holderNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(180, 207, 191));

        jLabel11.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel11.setText("Car");

        jLabel12.setText("Model");

        modelTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelTextFieldActionPerformed(evt);
            }
        });

        jLabel13.setText("Manufacturing Year");

        mYearTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mYearTextFieldActionPerformed(evt);
            }
        });

        jLabel14.setText("Price");

        priceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priceTextFieldActionPerformed(evt);
            }
        });

        jLabel16.setText("Type");

        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
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
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(modelTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                                    .addComponent(mYearTextField)
                                    .addComponent(priceTextField))))
                        .addGap(22, 22, 22))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(modelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(mYearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(priceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addGap(20, 20, 20)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 4, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(monthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel22)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel20)
                            .addComponent(dayTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(39, 39, 39))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(yearTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dayTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jLabel22))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(180, 207, 191));

        jLabel23.setText("Number of Claims");

        numOfClaimsTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numOfClaimsTextFieldActionPerformed(evt);
            }
        });

        jLabel24.setText("Driver Age");

        driverAgeTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                driverAgeTextFieldActionPerformed(evt);
            }
        });

        jLabel25.setText("Level");

        levelTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelTextFieldActionPerformed(evt);
            }
        });

        jLabel26.setText("Comments");

        commentsTextArea.setColumns(20);
        commentsTextArea.setRows(5);
        jScrollPane1.setViewportView(commentsTextArea);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel24)
                            .addComponent(jLabel25))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(numOfClaimsTextField)
                            .addComponent(driverAgeTextField)
                            .addComponent(levelTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))))
                .addGap(22, 22, 22))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(numOfClaimsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(driverAgeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(levelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        addPolicyButton.setBackground(new java.awt.Color(185, 210, 195));
        addPolicyButton.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        addPolicyButton.setText("Submit");
        addPolicyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPolicyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(addPolicyButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(addPolicyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add Policy", jPanel2);

        jPanel22.setBackground(new java.awt.Color(180, 207, 191));

        jLabel46.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        jLabel46.setText("Enter a policy ID");

        findPolicyButton3.setText("Find");
        findPolicyButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findPolicyButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel22Layout = new javax.swing.GroupLayout(jPanel22);
        jPanel22.setLayout(jPanel22Layout);
        jPanel22Layout.setHorizontalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel22Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 405, Short.MAX_VALUE)
                .addComponent(findByIdTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(findPolicyButton3)
                .addGap(17, 17, 17))
        );
        jPanel22Layout.setVerticalGroup(
            jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel22Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel22Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(findByIdTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(findPolicyButton3))
                .addGap(12, 12, 12))
        );

        foundPoliciesTextArea.setEditable(false);
        foundPoliciesTextArea.setColumns(20);
        foundPoliciesTextArea.setRows(5);
        jScrollPane2.setViewportView(foundPoliciesTextArea);

        javax.swing.GroupLayout jPanel21Layout = new javax.swing.GroupLayout(jPanel21);
        jPanel21.setLayout(jPanel21Layout);
        jPanel21Layout.setHorizontalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel21Layout.setVerticalGroup(
            jPanel21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel21Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Find Policy", jPanel21);

        policiesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Policy ID", "Holder Name", "Car Model", "Car Type", "Car Price", "Car Manufacturing Year", "Expiry Date", "Number of Claims", "Level", "Driver Age", "Comments"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(policiesTable);

        deletePolicyBtn.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        deletePolicyBtn.setText("ِDelete");
        deletePolicyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deletePolicyBtnActionPerformed(evt);
            }
        });

        updatePolicyBtn.setFont(new java.awt.Font("sansserif", 0, 18)); // NOI18N
        updatePolicyBtn.setText("Update");
        updatePolicyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updatePolicyBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 869, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(updatePolicyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deletePolicyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(14, 14, 14))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(deletePolicyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(updatePolicyBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Policies Information", jPanel10);

        jPanel24.setBackground(new java.awt.Color(158, 158, 158));

        jLabel47.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel47.setText("Filter By Car Model");

        modelFilterTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelFilterTextFieldActionPerformed(evt);
            }
        });
        modelFilterTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                modelFilterTextFieldKeyPressed(evt);
            }
        });

        modelFilterButton.setText("Filter by Car Model");
        modelFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modelFilterButtonActionPerformed(evt);
            }
        });

        jLabel48.setText("Car Model:");

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(modelFilterTextField)
                            .addComponent(jLabel47, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                        .addComponent(modelFilterButton)
                        .addGap(16, 16, 16))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel47)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(jLabel48)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modelFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(modelFilterButton))
                .addGap(21, 21, 21))
        );

        jPanel25.setBackground(new java.awt.Color(158, 158, 158));

        jLabel49.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel49.setText("Filter By Expiry Date");

        jLabel50.setText("Year");

        jLabel51.setText("Month");

        jLabel52.setText("Day");

        dateFilterButton.setText("Filter by Expiry Date");
        dateFilterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dateFilterButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel25Layout = new javax.swing.GroupLayout(jPanel25);
        jPanel25.setLayout(jPanel25Layout);
        jPanel25Layout.setHorizontalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel50))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(monthFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel52)
                            .addGroup(jPanel25Layout.createSequentialGroup()
                                .addComponent(dayFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(dateFilterButton))))
                    .addGroup(jPanel25Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel49)
                        .addGap(172, 172, 172)))
                .addGap(16, 16, 16))
        );
        jPanel25Layout.setVerticalGroup(
            jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel25Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel49)
                .addGap(18, 18, 18)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addComponent(jLabel52))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel25Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(dayFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(monthFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(yearFilterTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateFilterButton))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        filteredPoliciesTextArea.setEditable(false);
        filteredPoliciesTextArea.setColumns(20);
        filteredPoliciesTextArea.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        filteredPoliciesTextArea.setRows(5);
        filteredPoliciesTextArea.setMargin(new java.awt.Insets(10, 10, 10, 10));
        jScrollPane4.setViewportView(filteredPoliciesTextArea);

        jPanel26.setBackground(new java.awt.Color(158, 158, 158));

        jLabel53.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel53.setText("Sort By Name");

        sortButton.setText("Sort");
        sortButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel53)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel26Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sortButton)
                .addGap(18, 18, 18))
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel26Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel53)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(sortButton)
                .addGap(19, 19, 19))
        );

        jButton4.setText("Reset");
        jButton4.setName("resetFilteredList"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4filterResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel23Layout.createSequentialGroup()
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel25, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 871, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel25, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addGap(8, 8, 8)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Filter Policy", jPanel23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 930, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void filterResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterResetActionPerformed
        filteredPoliciesTextArea.setText(InsurancePolicy.policiesList(selectedUser.getPolicies()));        
    }//GEN-LAST:event_filterResetActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed

    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        editData();
        login.getCompany().save("company.ser");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed

    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged

        if (evt.getStateChange() == ItemEvent.SELECTED) {
            jComboBox1.setEnabled(false);
            jTextField8.setEnabled(true);
        } else {
            jComboBox1.setEnabled(true);
            jTextField8.setEnabled(false);
        }
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed

    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void holderNameTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_holderNameTextFieldActionPerformed

    }//GEN-LAST:event_holderNameTextFieldActionPerformed

    private void idTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_idTextFieldActionPerformed

    }//GEN-LAST:event_idTextFieldActionPerformed

    private void thirdPartyRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thirdPartyRadioActionPerformed
        handlePolicyTypeChoice();
    }//GEN-LAST:event_thirdPartyRadioActionPerformed

    private void comprehensiveRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comprehensiveRadioActionPerformed
        handlePolicyTypeChoice();
    }//GEN-LAST:event_comprehensiveRadioActionPerformed

    private void modelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelTextFieldActionPerformed

    }//GEN-LAST:event_modelTextFieldActionPerformed

    private void mYearTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mYearTextFieldActionPerformed

    }//GEN-LAST:event_mYearTextFieldActionPerformed

    private void priceTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priceTextFieldActionPerformed

    }//GEN-LAST:event_priceTextFieldActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed

    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void numOfClaimsTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numOfClaimsTextFieldActionPerformed

    }//GEN-LAST:event_numOfClaimsTextFieldActionPerformed

    private void driverAgeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_driverAgeTextFieldActionPerformed

    }//GEN-LAST:event_driverAgeTextFieldActionPerformed

    private void levelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelTextFieldActionPerformed

    }//GEN-LAST:event_levelTextFieldActionPerformed

    private void addPolicyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPolicyButtonActionPerformed

        addPolicy();
    }//GEN-LAST:event_addPolicyButtonActionPerformed

    private void findPolicyButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findPolicyButton3ActionPerformed
        int PolicyID = Integer.parseInt(findByIdTextField3.getText());
        InsurancePolicy foundPolicy = selectedUser.findPolicy(PolicyID);
        if (foundPolicy == null) {
            foundPoliciesTextArea.append("=> No match for the inserted ID of " + findByIdTextField3.getText() + "! \n\n");
        } else {
            foundPoliciesTextArea.append("=> found policy: \n" + foundPolicy.toString() + "\n\n");
        }
        findByIdTextField3.setText("");
    }//GEN-LAST:event_findPolicyButton3ActionPerformed

    private void deletePolicyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deletePolicyBtnActionPerformed
        if (!isPolicySelectedInTable()) return;

        int policyID = Integer.parseInt(policiesTable.getModel().getValueAt(policiesTable.getSelectedRow(), 0).toString());

        int deleteConfirmDialoge = JOptionPane.showConfirmDialog (this, "Are you sure you want to delete Policy " + policyID + "?");
        if (deleteConfirmDialoge == JOptionPane.YES_OPTION) {
            selectedUser.getPolicies().remove(policyID);
            JOptionPane.showMessageDialog(this, "Policy " + policyID + " deleted successfully.");
            login.getCompany().save("company.ser");
        }
    }//GEN-LAST:event_deletePolicyBtnActionPerformed

    private void updatePolicyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updatePolicyBtnActionPerformed
        if (!isPolicySelectedInTable()) return;

        int policyID = Integer.parseInt(policiesTable.getModel().getValueAt(policiesTable.getSelectedRow(), 0).toString());
        InsurancePolicy selectedPolicy = selectedUser.findPolicy(policyID);
        UpdateUI updateUI = new UpdateUI(new UserUI(company.populateDistinctCityNames(), selectedUser, login), selectedPolicy);
        updateUI.run();

    }//GEN-LAST:event_updatePolicyBtnActionPerformed

    private void modelFilterTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelFilterTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modelFilterTextFieldActionPerformed

    private void modelFilterTextFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_modelFilterTextFieldKeyPressed
        if (modelFilterTextField.getText().equals(""))
        filteredPoliciesTextArea.setText(InsurancePolicy.policiesList(selectedUser.getPolicies()));

        String carModel = modelFilterTextField.getText();

        String policiesList = InsurancePolicy.policiesList(selectedUser.filterByCarModel(carModel));
        System.out.println(policiesList);
        filteredPoliciesTextArea.setText(policiesList);        // TODO add your handling code here:
    }//GEN-LAST:event_modelFilterTextFieldKeyPressed

    private void modelFilterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modelFilterButtonActionPerformed
        if (modelFilterTextField.getText().equals(""))
        filteredPoliciesTextArea.setText(InsurancePolicy.policiesList(selectedUser.getPolicies()));

        String carModel = modelFilterTextField.getText();

        String policiesList = InsurancePolicy.policiesList(selectedUser.filterByCarModel(carModel));
        System.out.println(policiesList);
        filteredPoliciesTextArea.setText(policiesList);
    }//GEN-LAST:event_modelFilterButtonActionPerformed

    private void dateFilterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dateFilterButtonActionPerformed
        if (yearFilterTextField.getText().equals("")
            || monthFilterTextField.getText().equals("")
            || dayFilterTextField.getText().equals("")
        ) return;

        int year, month, day;

        try {
            year = Integer.parseInt(yearFilterTextField.getText());
            month = Integer.parseInt(monthFilterTextField.getText());
            day = Integer.parseInt(dayFilterTextField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter numberes!");
            return;
        }

        if (year < 1980 || year > 2030 || month < 1 || month > 12 || day < 1 || day > 31) {
            JOptionPane.showMessageDialog(this, "Enter valid data!");
            return;
        }

        MyDate expiryDate = new MyDate(year, month, day);
        String policiesList = InsurancePolicy.policiesList(selectedUser.filterByExpiryDate(expiryDate));
        System.out.println(policiesList);
        filteredPoliciesTextArea.setText(policiesList);
    }//GEN-LAST:event_dateFilterButtonActionPerformed

    private void sortButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortButtonActionPerformed

    }//GEN-LAST:event_sortButtonActionPerformed

    private void jButton4filterResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4filterResetActionPerformed
        filteredPoliciesTextArea.setText(InsurancePolicy.policiesList(selectedUser.getPolicies()));
    }//GEN-LAST:event_jButton4filterResetActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        if (selectedUser == null) {
            JOptionPane.showMessageDialog(null, "Choose a user to continue first!", "Alert", JOptionPane.INFORMATION_MESSAGE);
            jTabbedPane1.setSelectedIndex(0);
        } else {
            setData();
        }        
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void usersComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usersComboBoxActionPerformed
        setSelectedUser();
        // TODO add your handling code here:
    }//GEN-LAST:event_usersComboBoxActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addPolicyButton;
    private javax.swing.JTextArea commentsTextArea;
    private javax.swing.JRadioButton comprehensiveRadio;
    private javax.swing.JButton dateFilterButton;
    private javax.swing.JTextField dayFilterTextField;
    private javax.swing.JTextField dayTextField;
    private javax.swing.JButton deletePolicyBtn;
    private javax.swing.JTextField driverAgeTextField;
    private javax.swing.JTextArea filteredPoliciesTextArea;
    private javax.swing.JTextField findByIdTextField3;
    private javax.swing.JButton findPolicyButton3;
    private javax.swing.JTextArea foundPoliciesTextArea;
    private javax.swing.JTextField holderNameTextField;
    private javax.swing.JTextField idTextField;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField levelTextField;
    private javax.swing.JTextField mYearTextField;
    private javax.swing.JButton modelFilterButton;
    private javax.swing.JTextField modelFilterTextField;
    private javax.swing.JTextField modelTextField;
    private javax.swing.JTextField monthFilterTextField;
    private javax.swing.JTextField monthTextField;
    private javax.swing.JTextField numOfClaimsTextField;
    private javax.swing.JTable policiesTable;
    private javax.swing.JTextField priceTextField;
    private javax.swing.JButton sortButton;
    private javax.swing.JRadioButton thirdPartyRadio;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JButton updatePolicyBtn;
    private javax.swing.JComboBox<String> usersComboBox;
    private javax.swing.JTextField yearFilterTextField;
    private javax.swing.JTextField yearTextField;
    // End of variables declaration//GEN-END:variables
}
