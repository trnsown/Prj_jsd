package a2_1901040179;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class CourseManProg {
    private Connection cn;
    private JFrame sFrame;
    private JLabel bLabel;
    private JFrame mFrame;
    private final static String url = "jdbc:sqlite:src\\main\\java\\a2_1901040179\\database.sqlite3";

    public CourseManProg() {
        mFrame = new JFrame("Assignment2");
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setSize(500, 500);
        mFrame.setLocationRelativeTo(null);
        mFrame.setLayout(new GridLayout(2, 1));
        
        
        bLabel = new JLabel("Author: Tran Ngoc Son", JLabel.CENTER);
        

        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu student = new JMenu("Student");
        JMenu module = new JMenu("Module");
        JMenu enrolment = new JMenu("Enrolment");


        JMenuItem exMi = new JMenuItem("Exit");

        JMenuItem newStMi = new JMenuItem("New Student");
        JMenuItem listStMi = new JMenuItem("List Students");

        JMenuItem newMoMi = new JMenuItem("New Module");
        JMenuItem listMoMi = new JMenuItem("List modules");

        JMenuItem newErMi = new JMenuItem("New enrolment");
        JMenuItem initialRpMi = new JMenuItem("Initial report");
        JMenuItem assessmentRpMi = new JMenuItem("Assessment report");

        file.add(exMi);

        student.add(newStMi);
        student.add(listStMi);

        module.add(newMoMi);
        module.add(listMoMi);

        enrolment.add(newErMi);
        enrolment.add(initialRpMi);
        enrolment.add(assessmentRpMi);

        menuBar.add(file);
        menuBar.add(student);
        menuBar.add(module);
        menuBar.add(enrolment);

        mFrame.setJMenuBar(menuBar);
       
 
        mFrame.add(bLabel);

        newStMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertNeSt();
            }
        });
        listStMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableSt();
            }
        });
        newMoMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertNeMd();
            }
        });
        listMoMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableMd();
            }
        });
        newErMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertNeEn();
            }
        });
        initialRpMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableInRp();
            }
        });
        assessmentRpMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tableAss();
            }
        });
        exMi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        mFrame.setVisible(true);
    }

    private void insertNeEn() {
        sFrame = new JFrame("New Enrolment");
        sFrame.setLayout(new GridLayout(5, 1));
        sFrame.setSize(500, 500);
        sFrame.setLocationRelativeTo(null);
        JPanel interMarkPn = new JPanel();
        JLabel interMarkLb = new JLabel("Internal Mark: ");
        final JTextField interMarkTf = new JTextField(30);

        JPanel exMarkPn = new JPanel();
        JLabel examMarkL = new JLabel("Examination Mark: ");
        final JTextField examMarkTf = new JTextField(30);

        HashMap<String, String > studentList = getStudentList();
        final JComboBox selectStCb = new JComboBox();
        selectStCb.setRenderer(new StudentRender());
        for (Map.Entry<String, String> s:
                studentList.entrySet()) {
            selectStCb.addItem(s);
        }
        JPanel selectStPn = new JPanel();

        HashMap<String, String> moduleList = getModuleList();
        final JComboBox selectMdCb = new JComboBox();
        selectMdCb.setRenderer(new ModuleRender());
        for (Map.Entry<String, String> m:
                moduleList.entrySet()) {
            selectMdCb.addItem(m);
        }

        JPanel selectMdPn = new JPanel();

        JButton button = new JButton("Create");


        interMarkPn.add(interMarkLb);
        interMarkPn.add(interMarkTf);
        exMarkPn.add(examMarkL);
        exMarkPn.add(examMarkTf);
        selectStPn.add(selectStCb);
        selectMdPn.add(selectMdCb);

        sFrame.add(interMarkPn);
        sFrame.add(exMarkPn);
        sFrame.add(selectStPn);
        sFrame.add(selectMdPn);
        sFrame.add(button);
        sFrame.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(sFrame,
                        "Do you want to add this new enrolment?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    Connection connection = getConnection();
                    try {
                        Statement statement = connection.createStatement();
                        Map.Entry<String, String> stHm  = (Map.Entry<String, String>) selectStCb.getSelectedItem();
                        Map.Entry<String, String> mdHm  = (Map.Entry<String, String>) selectMdCb.getSelectedItem();
                        String sql = "INSERT INTO enrolment (internalMark, examinationMark, studentID, moduleCode)" +
                                "VALUES ('"+interMarkTf.getText()+"','"
                                +examMarkTf.getText()+"','"
                                +stHm.getKey()+"','"
                                +mdHm.getKey()+"')";
                        statement.executeUpdate(sql);
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }

                } else return;
            }
        });
    }

    private HashMap<String, String> getModuleList() {
        HashMap<String, String> moduleList = new HashMap<>();
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql ="SELECT code, name FROM module ";
            ResultSet reslSetVa =  statement.executeQuery(sql);
            while (reslSetVa.next()){
                moduleList.put(reslSetVa.getString("code"), reslSetVa.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        return moduleList;
    }
    class ModuleRender extends BasicComboBoxRenderer{
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);
            if (value != null) {
                Map.Entry<String, String> module = (Map.Entry<String, String>) value;
                setText(module.getValue());
            }
            return this;
        }
    }
    class StudentRender extends BasicComboBoxRenderer{
        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected,
                    cellHasFocus);
            if (value != null) {
                Map.Entry<String, String> student = (Map.Entry<String, String>) value;
                setText(student.getValue());
            }
            return this;
        }
    }
    private HashMap<String, String> getStudentList(){
        HashMap<String, String> studentList = new HashMap<>();
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql ="SELECT id, name FROM student";
            ResultSet resultSet =  statement.executeQuery(sql);
            while (resultSet.next()){
                studentList.put(resultSet.getString("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        return studentList;
    }

    private void tableAss() {
        sFrame = new JFrame("Assessment Report");
        String[][] data = new String[totalEnrolment()][6];
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql =  "SELECT e.id, e.studentID, s.name nameStudent, m.code, m.name nameModule, e.internalMark, e.examinationMark " +
                    "FROM enrolment e " +
                    "JOIN student s ON s.id = e.studentID " +
                    "JOIN  module m ON m.code = e.moduleCode";
            ResultSet rs = statement.executeQuery(sql);
            int col = 0;
            while (rs.next()) {
                System.out.println(col);
                String id = String.valueOf(rs.getInt("id"));
                data[col][0] = id;
                String studentID = rs.getString("studentID");
                data[col][1] = studentID;
                String code = rs.getString("code");
                data[col][2] = code;
                float internalMark = rs.getFloat("internalMark");
                data[col][3] = String.valueOf(internalMark);
                float examinationMark = rs.getFloat("examinationMark");
                data[col][4] = String.valueOf(examinationMark);
                String finalGrade = assessment(internalMark, examinationMark);
                data[col][5] = finalGrade;
                col++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        String column[] = {"ID", "STUDENT ID",  "CODE", "INTERNAL MARK", "EXAMINATION MARK", "FINAL GRADE"};

        JTable jt = new JTable(data, column);
        jt.setBounds(40, 50, 250, 350);

        JScrollPane sp = new JScrollPane(jt);
        sFrame.add(sp);
        sFrame.setSize(800, 500);
        sFrame.setLocationRelativeTo(null);
        sFrame.setVisible(true);
    }

    private void tableInRp() {
        sFrame = new JFrame("Initial Report");
        String[][] data = new String[totalEnrolment()][5];
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT e.id, e.studentID, s.name nameStudent, m.code, m.name nameModule " +
                    "FROM enrolment e " +
                    "JOIN student s ON s.id = e.studentID " +
                    "JOIN  module m ON m.code = e.moduleCode";
            ResultSet rs = statement.executeQuery(sql);
            int col = 0;
            while (rs.next()) {
                System.out.println(col);
                String id = String.valueOf(rs.getInt("id"));
                data[col][0] = id;
                String studentID = rs.getString("studentID");
                data[col][1] = studentID;
                String nameStudent = rs.getString("nameStudent");
                data[col][2] = nameStudent;
                String code = rs.getString("code");
                data[col][3] = code;
                String nameModule = rs.getString("nameModule");
                data[col][4] = nameModule;
                col++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        String column[] = {"ID", "STUDENT ID", "STUDENT NAME", "CODE", "MODULE NAME"};

        JTable jt = new JTable(data, column);
        jt.setBounds(40, 50, 250, 350);

        JScrollPane sp = new JScrollPane(jt);
        sFrame.add(sp);
        sFrame.setSize(700, 500);
        sFrame.setLocationRelativeTo(null);
        sFrame.setVisible(true);
    }
    private String assessment(float internalMark, float examinationMark){
        FinalGrade finalGrade = null;
        float aggregatedMark = (float) (0.4 * internalMark + 0.6 * examinationMark);
        if (aggregatedMark <= 10 && aggregatedMark >= 8.6){
            finalGrade = FinalGrade.E;
        }else if (aggregatedMark <= 8.5 && aggregatedMark >= 6.6){
            finalGrade = FinalGrade.G;
        }else if (aggregatedMark <= 6.5 && aggregatedMark >= 5){
            finalGrade= FinalGrade.P;
        } else finalGrade = FinalGrade.F;
        return finalGrade.getAssessment();
    }

    private int totalEnrolment(){
        int result = 0;
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(*) total FROM enrolment";
            ResultSet resultSet = statement.executeQuery(sql);
            result = resultSet.getInt("total");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        return result;
    }

    private void insertNeMd() {
        sFrame = new JFrame("New Module");
        sFrame.setLayout(new GridLayout(6, 1));
        sFrame.setSize(500, 500);
        sFrame.setLocationRelativeTo(null);

        JLabel lbl = new JLabel("Select one of the possible choices and click OK");
        final JComboBox cb = new JComboBox();
        cb.addItem("ELECTIVE");
        cb.addItem("COMPULSORY");
        JPanel selectMdPn = new JPanel();

        JPanel namePn = new JPanel();
        JLabel nameLb = new JLabel("Name: ");
        final JTextField nameTf = new JTextField(30);

        JPanel semesterPN = new JPanel();
        JLabel semesterL = new JLabel("Semester: ");
        final JTextField semesterTF = new JTextField(30);

        JPanel creditsPN = new JPanel();
        JLabel creditsL = new JLabel("Credits: ");
        final JTextField creditsTF = new JTextField(30);

        final JPanel departmentnamePn = new JPanel();
        JLabel departmentnameLb = new JLabel("Department Name: ");
        final JTextField departmentnameTf = new JTextField(30);

        JButton button = new JButton("Create");

        namePn.add(nameLb);
        namePn.add(nameTf);
        semesterPN.add(semesterL);
        semesterPN.add(semesterTF);
        creditsPN.add(creditsL);
        creditsPN.add(creditsTF);
        departmentnamePn.add(departmentnameLb);
        departmentnamePn.add(departmentnameTf);
        selectMdPn.add(lbl);
        selectMdPn.add(cb);

        sFrame.add(selectMdPn);
        sFrame.add(namePn);
        sFrame.add(semesterPN);
        sFrame.add(creditsPN);
        sFrame.add(departmentnamePn);
        sFrame.add(button);

        sFrame.setVisible(true);

        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                JComboBox comboBox = (JComboBox) event.getSource();
                Object selected = comboBox.getSelectedItem();
                if (selected.toString().equals("ELECTIVE")) {
                    sFrame.setLayout(new GridLayout(6,1));
                    departmentnamePn.setVisible(true);
                } else if (selected.toString().equals("COMPULSORY")) {
                    sFrame.setLayout(new GridLayout(6,1));
                    departmentnamePn.setVisible(false);
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(sFrame,
                        "Do you want to add this new module?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    String code = "M" + semesterTF.getText() + "0" + (countBySemester(Integer.parseInt(semesterTF.getText())) + 1);
                    Connection connection = getConnection();
                    try {
                        Statement statement = connection.createStatement();
                        String sql ="";
                        if (cb.getSelectedItem().equals("ELECTIVE")){
                            sql = "INSERT INTO module (code, `name`, semester, credits, departmentName)" +
                                    "VALUES ('" + code+"','"
                                                + nameTf.getText()+"','"
                                                + semesterTF.getText()+"','"
                                                + creditsTF.getText()+"','"
                                                + departmentnameTf.getText()+"')";
                        }else if (cb.getSelectedItem().equals("COMPULSORY")){
                            sql = "INSERT INTO module (code, `name`, semester, credits)" +
                                    "VALUES ('" + code+"','"
                                    + nameTf.getText()+"','"
                                    + semesterTF.getText()+"','"
                                    + creditsTF.getText()+"')";
                        }
                        statement.executeUpdate(sql);
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }finally {
                        disconnectDB();
                    }
                } else if (result == JOptionPane.NO_OPTION) {
                    return;
                }
            }
        });
    }
    public int countBySemester(int semester) {
        Connection connection = getConnection();
        int result = 0;
        try {
            String sql = "SELECT COUNT(*)" +
                    "FROM module WHERE semester = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, semester);
            ResultSet resultSet = statement.executeQuery();
            result = resultSet.getInt(1);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            disconnectDB();
        }
        return result;
    }

    private void tableMd() {
        sFrame = new JFrame("List Modules");
        String[][] data = new String[totalModule()][5];
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT code, name, semester, credits, departmentName FROM module";
            ResultSet rs = statement.executeQuery(sql);
            int col = 0;
            while (rs.next()) {
                System.out.println(col);
                String id = rs.getString("code");
                data[col][0] = id;
                String name = rs.getString("name");
                data[col][1] = name;
                String semester = String.valueOf(rs.getInt("semester"));
                data[col][2] = semester;
                String credits = String.valueOf(rs.getInt("credits"));
                data[col][3] = credits;
                String departmentName = rs.getString("departmentName");
                data[col][4] = departmentName;
                col++;
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        String column[] = {"CODE", "NAME", "SEMESTER", "CREDITS", "DEPARTMENT NAME"};

        JTable jt = new JTable(data, column);
        jt.setBounds(40, 50, 250, 350);

        JScrollPane sp = new JScrollPane(jt);
        sFrame.add(sp);
        sFrame.setSize(700, 500);
        sFrame.setLocationRelativeTo(null);
        sFrame.setVisible(true);


    }
    
    private int totalModule(){
        int result = 0;
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(*) total FROM module";
            ResultSet resultSet = statement.executeQuery(sql);
            result = resultSet.getInt("total");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        return result;
    }

    private void insertNeSt() {
        sFrame = new JFrame("New Student");
        sFrame.setLayout(new GridLayout(5, 1));
        sFrame.setSize(500, 500);
        sFrame.setLocationRelativeTo(null);
        JPanel namePn = new JPanel();
        JLabel nameLb = new JLabel("Name: ");
        final JTextField nameTf = new JTextField(30);

        JPanel dobPn = new JPanel();
        JLabel dobLb = new JLabel("Date Of Birth: ");
        final JTextField dobTf = new JTextField(30);

        JPanel addPn = new JPanel();
        JLabel addLb = new JLabel("Address: ");
        final JTextField addTf = new JTextField(30);
        
        JPanel emailPn = new JPanel();
        JLabel emailLb = new JLabel("Email: ");
        final JTextField emailTf = new JTextField(30);

        JButton button = new JButton("Create");

        namePn.add(nameLb);
        namePn.add(nameTf);
        dobPn.add(dobLb);
        dobPn.add(dobTf);
        addPn.add(addLb);
        addPn.add(addTf);
        emailPn.add(emailLb);
        emailPn.add(emailTf);

        sFrame.add(namePn);
        sFrame.add(dobPn);
        sFrame.add(addPn);
        sFrame.add(emailPn);
        sFrame.add(button);

        sFrame.setVisible(true);


        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(sFrame,
                        "Do you want to add this new student?",
                        "Confirm",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    Statement createState = null;
                    try {
                        Statement slStatement = getConnection().createStatement();
                        String sqL = "SELECT COUNT(*) FROM `student`";
                        ResultSet resSetVa = slStatement.executeQuery(sqL);
                        Integer year = 2019 + resSetVa.getInt(1) ;
                        String id = "S"+ year.toString();
                        resSetVa.close();
                        slStatement.close();
                        getConnection().close();
                        createState = getConnection().createStatement();
                        String sql = "INSERT INTO `student` (id, name, dob, address, email)" +
                                "VALUES ('"+id+"','" + nameTf.getText() + "','" + dobTf.getText() + "','" + addTf.getText() + "','" + emailTf.getText() +"')";
                        System.out.println(sql);
                        createState.executeUpdate(sql);
                    } catch (SQLException ex) {
                        System.err.println(ex.getMessage());
                    }
                } else if (result == JOptionPane.NO_OPTION) {
                    return;
                }
            }
        });
    }

    private void tableSt() {
        sFrame = new JFrame("List Students");
        String[][] data = new String[getTotalStudent()][5];
        sFrame.setLocationRelativeTo(null);
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            Statement statement = conn.createStatement();
            String sql = "SELECT id, name, dob, address, email FROM `student`";
            ResultSet rs = statement.executeQuery(sql);
            int col = 0;
            while (rs.next()) {
                System.out.println(col);
                String id = rs.getString("id");
                data[col][0] = id;
                String name = rs.getString("name");
                data[col][1] = name;
                String dob = rs.getString("dob");
                data[col][2] = dob;
                String address = rs.getString("address");
                data[col][3] = address;
                String email = rs.getString("email");
                data[col][4] = email;
                col++;
            }
            rs.close();
            statement.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        String column[] = {"ID", "NAME", "DOB", "ADDRESS", "EMAIL"};

        JTable jt = new JTable(data, column);
        jt.setBounds(40, 50, 250, 350);

        JScrollPane sp = new JScrollPane(jt);
        sFrame.add(sp);
        sFrame.setSize(700, 500);
        sFrame.setLocationRelativeTo(null);
        sFrame.setVisible(true);
    }
    public int getTotalStudent(){
        int total =1;
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = "SELECT COUNT(*) total FROM student";
            ResultSet rs = statement.executeQuery(sql);
            total = rs.getInt("total");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }finally {
            disconnectDB();
        }
        return total;

    }
    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            cn = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cn;
    }
    public void disconnectDB(){
        try {
            cn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new CourseManProg();
    }


}
