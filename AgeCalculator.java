import java.awt.*;
import java.awt.event.*;

class AgeCalculator extends Frame implements ActionListener {
    private Label lblCurrentYear, lblCurrentMonth, lblCurrentDate;
    private Label lblBirthYear, lblBirthMonth, lblBirthDate, lblResult;
    private Button btnCalculate;
    private TextField txtCurrentYear, txtCurrentMonth, txtCurrentDate;
    private TextField txtBirthYear, txtBirthMonth, txtBirthDate, txtResult;
    
    AgeCalculator() {
        super("Age Calculator");
        setSize(400, 300);
        setLayout(new GridLayout(8, 2, 5, 5));
        
        lblCurrentYear = new Label("Current Year:");
        lblCurrentMonth = new Label("Current Month:");
        lblCurrentDate = new Label("Current Date:");
        lblBirthYear = new Label("Birth Year:");
        lblBirthMonth = new Label("Birth Month:");
        lblBirthDate = new Label("Birth Date:");
        lblResult = new Label("Your Age:");
        
        txtCurrentYear = new TextField();
        txtCurrentMonth = new TextField();
        txtCurrentDate = new TextField();
        txtBirthYear = new TextField();
        txtBirthMonth = new TextField();
        txtBirthDate = new TextField();
        txtResult = new TextField();
        txtResult.setEditable(false);
        
        btnCalculate = new Button("Calculate");
        btnCalculate.addActionListener(this);
        
        add(lblCurrentYear); add(txtCurrentYear);
        add(lblCurrentMonth); add(txtCurrentMonth);
        add(lblCurrentDate); add(txtCurrentDate);
        add(lblBirthYear); add(txtBirthYear);
        add(lblBirthMonth); add(txtBirthMonth);
        add(lblBirthDate); add(txtBirthDate);
        add(lblResult); add(txtResult);
        add(btnCalculate);
        
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        try {
            int cy = Integer.parseInt(txtCurrentYear.getText());
            int cm = Integer.parseInt(txtCurrentMonth.getText());
            int cd = Integer.parseInt(txtCurrentDate.getText());
            int by = Integer.parseInt(txtBirthYear.getText());
            int bm = Integer.parseInt(txtBirthMonth.getText());
            int bd = Integer.parseInt(txtBirthDate.getText());
            
            Age age = new Age();
            if (!age.isValidDate(cy, cm, cd) || !age.isValidDate(by, bm, bd) || by > cy) {
                txtResult.setText("Invalid Date");
                return;
            }
            
            int years = age.calculateYears(cy, by, cm, bm, cd, bd);
            int months = age.calculateMonths(cm, bm, cd, bd);
            int days = age.calculateDays(cy, cm, cd, bd);
            
            txtResult.setText(years + " Years, " + months + " Months, " + days + " Days");
        } catch (NumberFormatException ex) {
            txtResult.setText("Invalid Input");
        }
    }
    
    public static void main(String[] args) {
        new AgeCalculator();
    }
}

class Age {
    public int calculateYears(int cy, int by, int cm, int bm, int cd, int bd) {
        return (cm > bm || (cm == bm && cd >= bd)) ? (cy - by) : (cy - by - 1);
    }
    
    public int calculateMonths(int cm, int bm, int cd, int bd) {
        if (cm > bm || (cm == bm && cd >= bd)) {
            return (cm - bm);
        } else {
            return (12 - bm + cm - 1);
        }
    }
    
    public int calculateDays(int cy, int cm, int cd, int bd) {
        if (cd >= bd) {
            return (cd - bd);
        } else {
            int prevMonth = (cm == 1) ? 12 : cm - 1;
            return getDaysInMonth(prevMonth, cy) - bd + cd;
        }
    }
    
    public boolean isValidDate(int year, int month, int day) {
        if (year < 0 || month < 1 || month > 12 || day < 1 || day > getDaysInMonth(month, year)) {
            return false;
        }
        return true;
    }
    
    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 2: return (isLeapYear(year)) ? 29 : 28;
            case 4: case 6: case 9: case 11: return 30;
            default: return 31;
        }
    }
    
    private boolean isLeapYear(int year) {
        return (year % 400 == 0) || (year % 4 == 0 && year % 100 != 0);
    }
}
