import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder; // Import for Round Rectangle

/**
 * A scientific calculator with a custom UI, dark mode,
 * and a self-contained expression evaluator.
 */
public class Calculator extends JFrame implements ActionListener, MouseListener {

    // --- Define our color palette (No change) ---
    private static final Color COLOR_BG = new Color(30, 30, 30);
    private static final Color COLOR_DISPLAY = new Color(50, 50, 50);
    private static final Color COLOR_DISPLAY_TEXT = Color.WHITE;
    private static final Color COLOR_BTN = new Color(70, 70, 70);
    private static final Color COLOR_BTN_HOVER = new Color(90, 90, 90);
    private static final Color COLOR_BTN_TEXT = Color.WHITE;
    private static final Color COLOR_OPERATOR = new Color(255, 150, 0);
    private static final Color COLOR_OPERATOR_HOVER = new Color(255, 180, 50);
    private static final Color COLOR_CLEAR = new Color(210, 50, 50);
    private static final Color COLOR_CLEAR_HOVER = new Color(230, 80, 80);
    private static final Color COLOR_FUNC = new Color(110, 110, 110);
    private static final Color COLOR_FUNC_HOVER = new Color(130, 130, 130);

    // --- Define our fonts (No change) ---
    private static final Font FONT_DISPLAY = new Font("Arial", Font.BOLD, 28);
    private static final Font FONT_BUTTONS = new Font("Arial", Font.BOLD, 16);

    // --- Class Variables (No change) ---
    JTextField t1;
    private boolean isRadians = false;
    private JButton radDegButton; // We need to store this one

    /**
     * Constructor: Sets up the entire GUI
     */
    Calculator() {
        setTitle("Scientific Calculator");
        setSize(380, 580); // Made window narrower
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(COLOR_BG);

        t1 = new JTextField();
        t1.setBounds(30, 40, 305, 50); // Made display narrower
        t1.setFont(FONT_DISPLAY);
        t1.setEditable(false);
        t1.setBackground(COLOR_DISPLAY);
        t1.setForeground(COLOR_DISPLAY_TEXT);
        t1.setHorizontalAlignment(JTextField.RIGHT);
        t1.setBorder(new EmptyBorder(5, 5, 5, 10));
        add(t1);

        // --- Button layout constants ---
        int x1 = 30, x2 = 95, x3 = 160, x4 = 225, x5 = 290;
        int y1 = 110, y2 = 175, y3 = 240, y4 = 305, y5 = 370, y6 = 435;
        int btnSize = 55; // Button width and height
        
        // --- ================================== ---
        // ---  Row 1 (y=110)
        // --- ================================== ---
        radDegButton = new RoundButton("Deg"); // Store this button
        radDegButton.setBounds(x1, y1, btnSize, btnSize);
        addButton(radDegButton);

        addButton(new RoundButton("sin"), x2, y1, btnSize);
        addButton(new RoundButton("cos"), x3, y1, btnSize);
        addButton(new RoundButton("tan"), x4, y1, btnSize);
        addButton(new RoundButton("!"), x5, y1, btnSize);

        // --- ================================== ---
        // ---  Row 2 (y=175)
        // --- ================================== ---
        addButton(new RoundButton("log"), x1, y2, btnSize);
        addButton(new RoundButton("7"), x2, y2, btnSize);
        addButton(new RoundButton("8"), x3, y2, btnSize);
        addButton(new RoundButton("9"), x4, y2, btnSize);
        addButton(new RoundButton("/"), x5, y2, btnSize);

        // --- ================================== ---
        // ---  Row 3 (y=240)
        // --- ================================== ---
        addButton(new RoundButton("√"), x1, y3, btnSize);
        addButton(new RoundButton("4"), x2, y3, btnSize);
        addButton(new RoundButton("5"), x3, y3, btnSize);
        addButton(new RoundButton("6"), x4, y3, btnSize);
        addButton(new RoundButton("*"), x5, y3, btnSize);
        
        // --- ================================== ---
        // ---  Row 4 (y=305)
        // --- ================================== ---
        addButton(new RoundButton("e^x"), x1, y4, btnSize);
        addButton(new RoundButton("1"), x2, y4, btnSize);
        addButton(new RoundButton("2"), x3, y4, btnSize);
        addButton(new RoundButton("3"), x4, y4, btnSize);
        addButton(new RoundButton("-"), x5, y4, btnSize);
        
        // --- ================================== ---
        // ---  Row 5 (y=370)
        // --- ================================== ---
        addButton(new RoundButton("^"), x1, y5, btnSize);
        addButton(new RoundButton("0"), x2, y5, btnSize);
        addButton(new RoundButton("."), x3, y5, btnSize);
        addButton(new RoundButton("%"), x4, y5, btnSize);
        addButton(new RoundButton("+"), x5, y5, btnSize);

        // --- ================================== ---
        // ---  Row 6 (y=435) - NEW LAYOUT
        // --- ================================== ---
        // --- '(' and ')' are on the LEFT ---
        addButton(new RoundButton("("), x1, y6, btnSize);
        addButton(new RoundButton(")"), x2, y6, btnSize);
        
        // --- 'AC' and '=' are on the RIGHT ---
        JButton clr = new RoundedRectButton("AC");
        clr.setBounds(x3, y6, 120, btnSize); // Spans 2 columns
        addButton(clr);
        
        JButton equals = new RoundButton("=");
        equals.setBounds(x5, y6, btnSize, btnSize);
        addButton(equals);

        // --- Finalize window ---
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    /**
     * --- NEW: Helper method to add buttons ---
     * This just reduces code repetition in the constructor
     */
    private void addButton(JButton button, int x, int y, int size) {
        button.setBounds(x, y, size, size);
        addButton(button);
    }
    
    /**
     * Helper method to add listeners, style, and add to frame
     */
    private void addButton(JButton button) {
        button.addActionListener(this);
        button.addMouseListener(this);
        styleButton(button);
        add(button);
    }

    /**
     * Helper method to style buttons based on their text/type.
     * (No change from previous version)
     */
    private void styleButton(JButton b) {
        String text = b.getText();
        b.setForeground(COLOR_BTN_TEXT);
        
        if (text.equals("AC")) {
            b.setBackground(COLOR_CLEAR);
        } else if (text.equals("/") || text.equals("*") || text.equals("-") || text.equals("+") || text.equals("=")) {
            b.setBackground(COLOR_OPERATOR);
        } else if (text.equals("sin") || text.equals("cos") || text.equals("tan") || text.equals("log") 
                   || text.equals("√") || text.equals("e^x") || text.equals("Deg") || text.equals("Rad")
                   || text.equals("!") || text.equals("%") || text.equals("(") || text.equals(")") || text.equals("^")) {
             b.setBackground(COLOR_FUNC);
        } else {
            b.setBackground(COLOR_BTN);
        }
    }
    
    /**
     * Helper to format the result (removes ".0" from whole numbers)
     * (No change from previous version)
     */
    private String formatResult(double res) {
        if (Double.isNaN(res) || Double.isInfinite(res)) {
            return "Error";
        }
        if (res == (long) res) {
            return String.format("%d", (long)res);
        } else {
            return String.format("%s", res);
        }
    }
    
    /**
     * Helper method to calculate factorial
     * (No change from previous version)
     */
    private long factorial(long n) {
        if (n < 0) throw new RuntimeException("Factorial domain");
        if (n == 0 || n == 1) return 1;
        if (n > 20) throw new RuntimeException("Factorial > 20");
        long result = 1;
        for (long i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    /**
     * Main logic for all button clicks
     * (No change from previous version)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand(); // Get button text
        String currentText = t1.getText();

        // Handle Clear
        if (s.equals("AC")) {
            t1.setText("");
        } 
        // Handle Rad/Deg toggle
        else if (s.equals("Deg") || s.equals("Rad")) {
            isRadians = !isRadians;
            radDegButton.setText(isRadians ? "Rad" : "Deg");
        }
        // Handle functions by adding them with an open parenthesis
        else if (s.equals("sin") || s.equals("cos") || s.equals("tan") || s.equals("log") || s.equals("√") || s.equals("e^x")) {
            String func = s;
            if(s.equals("√")) func = "sqrt";
            if(s.equals("e^x")) func = "exp";
            t1.setText(currentText + func + "(");
        }
        // Handle EVALUATE
        else if (s.equals("=")) {
            try {
                double result = eval(currentText);
                t1.setText(formatResult(result));
            } catch (Exception ex) {
                t1.setText("Error"); // Show generic error
            }
        }
        // Handle all other buttons (numbers, ops, parens, !, %, ^)
        else {
            t1.setText(currentText + s);
        }
    }

    // --- ================================== ---
    // --- MouseListener Methods for Hover FX ---
    // --- ================================== ---

    @Override
    public void mouseEntered(MouseEvent e) {
        JButton b = (JButton) e.getSource();
        String text = b.getText();
        
        if (text.equals("AC")) {
            b.setBackground(COLOR_CLEAR_HOVER);
        } else if (text.equals("/") || text.equals("*") || text.equals("-") || text.equals("+") || text.equals("=")) {
            b.setBackground(COLOR_OPERATOR_HOVER);
        } else if (text.equals("sin") || text.equals("cos") || text.equals("tan") || text.equals("log") 
                 || text.equals("√") || text.equals("e^x") || text.equals("Deg") || text.equals("Rad")
                 || text.equals("!") || text.equals("%") || text.equals("(") || text.equals(")") || text.equals("^")) {
             b.setBackground(COLOR_FUNC_HOVER);
        } else {
            b.setBackground(COLOR_BTN_HOVER);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JButton b = (JButton) e.getSource();
        // --- *** THIS IS THE FIXED LINE (removed 'img') *** ---
        styleButton(b); 
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}

    // --- ================================== ---
    // ---      Main Method (No change)       ---
    // --- ================================== ---
    public static void main(String[] args) {
        new Calculator();
    }
    
    // --- ================================== ---
    // --- Expression Evaluation Engine       ---
    // --- (No change from previous version)  ---
    // --- ================================== ---
    
    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor();
                    else if (eat('/')) x /= parseFactor();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') {
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("exp")) x = Math.exp(x);
                    else if (func.equals("sin")) x = Math.sin(isRadians ? x : Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(isRadians ? x : Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(isRadians ? x : Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor());
                if (eat('!')) {
                    if (x < 0 || x != (long) x) throw new RuntimeException("Factorial domain");
                    x = factorial((long) x);
                }
                if (eat('%')) {
                    x = x / 100.0;
                }
                
                return x;
            }
        }.parse();
    }
    
    // --- ================================== ---
    // ---   Button Inner Classes             ---
    // --- (No change from previous version)  ---
    // --- ================================== ---
    
    class RoundButton extends JButton {
        public RoundButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(null);
            setFont(FONT_BUTTONS);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillOval(0, 0, getWidth(), getHeight());
            g2.setColor(getForeground());
            FontMetrics metrics = g2.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(getText())) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
        @Override
        public boolean contains(int x, int y) {
            return new Ellipse2D.Float(0, 0, getWidth(), getHeight()).contains(x, y);
        }
    }

    class RoundedRectButton extends JButton {
        public RoundedRectButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorder(null);
            setFont(FONT_BUTTONS);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); 
            g2.setColor(getForeground());
            FontMetrics metrics = g2.getFontMetrics();
            int x = (getWidth() - metrics.stringWidth(getText())) / 2;
            int y = ((getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
            g2.drawString(getText(), x, y);
            g2.dispose();
        }
        @Override
        public boolean contains(int x, int y) {
            return new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 30, 30).contains(x, y);
        }
    }
}