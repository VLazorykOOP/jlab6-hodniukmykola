import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MatrixToVectorGUI extends JFrame {
    private JTextField nField;
    private JTable matrixTable;
    private JTextArea resultArea;
    private JButton calculateButton;

    public MatrixToVectorGUI() {
        setTitle("Matrix to Vector Converter");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("Enter n (n ≤ 20):"));
        nField = new JTextField(5);
        inputPanel.add(nField);

        calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        matrixTable = new JTable(new DefaultTableModel(20, 20)); // Максимум 20x20
        JScrollPane tableScrollPane = new JScrollPane(matrixTable);

        resultArea = new JTextArea(5, 40);
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        add(inputPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);
        add(resultScrollPane, BorderLayout.SOUTH);

        calculateButton.addActionListener(new CalculateButtonListener());
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = Integer.parseInt(nField.getText());
                if (n <= 0 || n > 20) {
                    throw new IllegalArgumentException("n повинно бути між 1 та 20!");
                }

                double[][] A = new double[n][n];
                double[] B = new double[n];

                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        Object value = matrixTable.getValueAt(i, j); 
                        if (value != null) {
                            A[i][j] = Double.parseDouble(value.toString());
                        } else {
                            throw new IllegalArgumentException("У таблиці є порожні клітинки. Заповніть всі клітинки перед обчисленням.");
                        }
                    }
                }

                for (int i = 0; i < n; i++) {
                    double rowSum = 0;
                    for (int j = 0; j < n; j++) {
                        rowSum += A[i][j];
                    }
                    B[i] = rowSum / n;
                }

                StringBuilder result = new StringBuilder("Vector B (average of rows):\n");
                for (double v : B) {
                    result.append(v).append(" ");
                }
                resultArea.setText(result.toString());

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Невірний формат чисел!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (CustomArithmeticException ex) {
                JOptionPane.showMessageDialog(null, "Власне виключення: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static class CustomArithmeticException extends ArithmeticException {
        public CustomArithmeticException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MatrixToVectorGUI gui = new MatrixToVectorGUI();
            gui.setVisible(true);
        });
    }
}
