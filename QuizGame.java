import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class QuizGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}

class LoginFrame extends JFrame {
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame() {
        setTitle("Quiz Login");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(44, 62, 80));

        JLabel title = new JLabel("Welcome to Java Quiz", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 38));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 1, 20, 20));
        centerPanel.setBackground(new Color(44, 62, 80));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(150, 600, 150, 600));

        nameField = new JTextField();
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createTitledBorder("Enter Your Name"));
        centerPanel.add(nameField);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createTitledBorder("Enter Password"));
        centerPanel.add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        loginButton.setBackground(new Color(41, 128, 185));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);

        loginButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter your name and password.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals("java123")) {
                JOptionPane.showMessageDialog(this, "Incorrect password. Please try again.", "Access Denied", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose();
                new DashboardFrame(name);
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(44, 62, 80));
        buttonPanel.add(loginButton);

        centerPanel.add(buttonPanel);
        add(centerPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

class DashboardFrame extends JFrame {
    private JButton startQuizButton;

    public DashboardFrame(String userName) {
        setTitle("Dashboard");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(52, 73, 94));
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.gridx = 0;

        JLabel welcomeLabel = new JLabel("Welcome " + userName + "!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        add(welcomeLabel, gbc);

        startQuizButton = new JButton("Start Quiz");
        startQuizButton.setFont(new Font("Segoe UI", Font.BOLD, 24));
        startQuizButton.setBackground(new Color(39, 174, 96));
        startQuizButton.setForeground(Color.WHITE);
        startQuizButton.setFocusPainted(false);
        startQuizButton.setPreferredSize(new Dimension(200, 60));

        startQuizButton.addActionListener(e -> {
            dispose();
            new QuizFrame(userName);
        });

        gbc.gridy = 1;
        add(startQuizButton, gbc);

        setVisible(true);
    }
}

class QuizFrame extends JFrame {
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton nextButton;
    private JLabel timerLabel;

    private int currentQuestionIndex = 0;
    private int score = 0;
    private String userName;

    private int timeRemaining = 2 * 60;
    private Timer quizTimer;

    private String[] questions = {
        "Which keyword is used to create a class in Java?",
        "What is the size of int in Java?",
        "Which method is the entry point for a Java application?",
        "Which concept allows multiple methods with the same name but different parameters?",
        "Which keyword is used to inherit a class in Java?",
        "Which package contains the Scanner class?",
        "Which of these is not a Java primitive type?",
        "Which keyword is used to define a constant in Java?",
        "Which exception is thrown when a division by zero occurs?",
        "Which collection class allows key-value pair storage?"
    };

    private String[][] choices = {
        {"class", "interface", "new", "void"},
        {"2 bytes", "4 bytes", "8 bytes", "16 bytes"},
        {"start()", "run()", "main()", "init()"},
        {"Encapsulation", "Inheritance", "Polymorphism", "Abstraction"},
        {"extends", "implements", "inherits", "super"},
        {"java.util", "java.io", "java.lang", "java.net"},
        {"int", "boolean", "float", "String"},
        {"final", "const", "static", "constant"},
        {"ArithmeticException", "IOException", "NullPointerException", "ClassCastException"},
        {"ArrayList", "HashMap", "HashSet", "LinkedList"}
    };

    private String[] answers = {
        "class", "4 bytes", "main()", "Polymorphism", "extends",
        "java.util", "String", "final", "ArithmeticException", "HashMap"
    };

    public QuizFrame(String userName) {
        this.userName = userName;
        setTitle("Java Quiz");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(236, 240, 241));
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(236, 240, 241));
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel welcomeLabel = new JLabel(" Answer the following questions:");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        topPanel.add(welcomeLabel, BorderLayout.WEST);

        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(Color.BLACK);
        timerLabel = new JLabel("Time Left: 02:00");
        timerLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        timerLabel.setForeground(Color.RED);
        timerPanel.add(timerLabel);
        topPanel.add(timerPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel quizPanel = new JPanel();
        quizPanel.setLayout(new BoxLayout(quizPanel, BoxLayout.Y_AXIS));
        quizPanel.setBorder(BorderFactory.createEmptyBorder(30, 100, 30, 100));
        quizPanel.setBackground(new Color(236, 240, 241));

        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
        quizPanel.add(questionLabel);

        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setFont(new Font("Segoe UI", Font.PLAIN, 20));
            options[i].setBackground(new Color(236, 240, 241));
            optionGroup.add(options[i]);
            quizPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            quizPanel.add(options[i]);
        }

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 18));
        nextButton.setBackground(new Color(39, 174, 96));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(e -> handleNext());
        quizPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        quizPanel.add(nextButton);

        add(quizPanel, BorderLayout.CENTER);
        loadQuestion();
        startTimer();
        setVisible(true);
    }

    private void startTimer() {
        quizTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                int minutes = timeRemaining / 60;
                int seconds = timeRemaining % 60;
                timerLabel.setText(String.format("Time Left: %02d:%02d", minutes, seconds));
                if (timeRemaining <= 0) {
                    quizTimer.stop();
                    JOptionPane.showMessageDialog(null, "Time's up! Submitting your quiz.");
                    showResult();
                }
            }
        });
        quizTimer.start();
    }

    private void loadQuestion() {
        optionGroup.clearSelection();
        if (currentQuestionIndex < questions.length) {
            questionLabel.setText("Q" + (currentQuestionIndex + 1) + ": " + questions[currentQuestionIndex]);
            char optionChar = 'A';
            for (int i = 0; i < 4; i++) {
                options[i].setText(optionChar + ". " + choices[currentQuestionIndex][i]);
                optionChar++;
            }
        } else {
            quizTimer.stop();
            showResult();
        }
    }

    private void handleNext() {
        String selected = null;
        for (JRadioButton option : options) {
            if (option.isSelected()) {
                selected = option.getText().substring(3);
                break;
            }
        }

        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Please select an answer.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selected.equals(answers[currentQuestionIndex])) {
            score++;
        }

        currentQuestionIndex++;
        loadQuestion();
    }

    private void showResult() {
        JOptionPane.showMessageDialog(this,
                userName + ", your score is: " + score + "/" + questions.length,
                "Quiz Completed", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}
