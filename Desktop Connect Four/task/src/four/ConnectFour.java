package four;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConnectFour extends JFrame {
    boolean winner = false;
    Turn turn = new Turn();
    ConnectFourGrid gridArray = new ConnectFourGrid(6, 7);
    public ConnectFour() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setVisible(true);
        setTitle("Connect Four");
        JPanel gameGrid = new JPanel();
        add(gameGrid);
        gameGrid.setLayout(new GridLayout(6, 7, 5, 5));
        for (int y = 0; y < this.gridArray.gridArray.length; y++) {
            for (int x = 0; x < this.gridArray.gridArray[0].length; x++) {
                String tagName = (char) ('A' + x) + "" +  ((int) this.gridArray.gridArray.length - y);
                ConnectFourField localField = new ConnectFourField(tagName, x, y);
                gameGrid.add(localField);
                this.gridArray.gridArray[y][x] = localField;
            }
        }
        for (int i = 0; i < this.gridArray.gridArray[0].length; i++) {
            ConnectFourField[] column = this.gridArray.getColumn(i);
            for (int j = 0; j < this.gridArray.gridArray.length; j++) {
                this.gridArray.gridArray[j][i].addListener(this, column);
            }
        }
        ResetButton resetButton = new ResetButton(this);
        JPanel resetPanel = new JPanel();
        resetPanel.setBounds(0, 0, 0, 0);
        resetPanel.add(resetButton);
        add(resetPanel);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(gameGrid, BorderLayout.CENTER);
        getContentPane().add(resetPanel, BorderLayout.SOUTH);
    }

    void resetFields() {
        this.winner = false;
        this.turn = new Turn();
        for (ConnectFourField[] column : this.gridArray.gridArray) {
            for (ConnectFourField field : column) {
                field.setText(" ");
                field.setBackground(Color.white);
            }
        }
    }
    protected void checkForWinner(ConnectFourField field) {
        ArrayList<ConnectFourField> listV = this.counterHelper(field, 1, 0);
        this.winnerHelper(listV, field);
        ArrayList<ConnectFourField> listH = this.counterHelper(field, 0, 1);
        ArrayList<ConnectFourField> listHl = this.counterHelper(field, 0, -1);
        listH.addAll(listHl);
        this.winnerHelper(listH, field);
        ArrayList<ConnectFourField> listD0 = this.counterHelper(field, -1, 1);
        ArrayList<ConnectFourField> listDlo = this.counterHelper(field, 1, -1);
        listD0.addAll(listDlo);
        this.winnerHelper(listD0, field);
        ArrayList<ConnectFourField> listD1 = this.counterHelper(field, -1, -1);
        ArrayList<ConnectFourField> listDro = this.counterHelper(field, 1, 1);
        listD1.addAll(listDro);
        this.winnerHelper(listD1, field);
    }
    private void winnerHelper(ArrayList<ConnectFourField> list, ConnectFourField currentField) {
        if (list.size() >= 3) {
            this.winner = true;
            currentField.setBackground(this.turn.value.getColor());
            for (ConnectFourField field : list) {
                field.setBackground(this.turn.value.getColor());
            }
        }

    }

    private ArrayList<ConnectFourField> counterHelper(ConnectFourField startField, int yValue, int xValue) {
        int checkX = startField.x + xValue; 
        int checkY = startField.y + yValue;

        ArrayList<ConnectFourField> fieldList = new ArrayList<>();
        while (checkX < this.gridArray.gridArray[0].length && checkY < this.gridArray.gridArray.length && checkY >= 0 && checkX >= 0) {
            String checkFieldValue = this.gridArray.gridArray[checkY][checkX].getText();
            if (!checkFieldValue.equals(startField.getText())) {
                break;
            } else {
                fieldList.add(this.gridArray.gridArray[checkY][checkX]);
            }
            checkY += yValue;
            checkX += xValue;
        }
        return fieldList;
    }
}

class ResetButton extends JButton {
    ResetButton(ConnectFour game) {
        setName("ButtonReset");
        setText("Reset");
        setVisible(true);
        setSize(80, 40);
        addActionListener(e -> game.resetFields());
    }
}
class ConnectFourGrid {
    ConnectFourField[][] gridArray;
    ConnectFourGrid(int yLength, int xLength) {
        this.gridArray = new ConnectFourField[yLength][xLength];
    }
    ConnectFourField[] getColumn(int xIndex) {
        ConnectFourField[] outArray = new ConnectFourField[this.gridArray.length];
        for (int i = this.gridArray.length - 1; i >= 0; i--) {
            outArray[i] = this.gridArray[i][xIndex];
        }
        return outArray;
    }
}
class ConnectFourField extends JButton {
    int x;
    int y;
    ConnectFourField(String nameTag, int x, int y) {
        setText(" ");
        setVisible(true);
        setName("Button" + nameTag);
        setBackground(Color.white);
        this.x = x;
        this.y = y;

    }
    public void addListener(ConnectFour game, ConnectFourField[] column) {
        addActionListener(e ->  this.fieldMarked(game, column));
    }
    public void fieldMarked(ConnectFour game, ConnectFourField[] column) {
        if (!game.winner) {
            for (int i = column.length - 1; i >= 0; i--) {
                if (" ".equals(column[i].getText())) {
                    column[i].setText(game.turn.getValue() + "");
                    game.checkForWinner(column[i]);
                    game.turn.switchTurn();
                    return;
                }
            }
        }
    }
}
class Turn {
    TurnEnum value = TurnEnum.X;
    char getValue() {
        return this.value.getCharacter();
    }
    public void switchTurn() {
        this.value = TurnEnum.switchTurn(this.value);
    }
    enum TurnEnum {
        X('X', java.awt.Color.pink), O('O', java.awt.Color.green);
        TurnEnum(char c, java.awt.Color color) {
            this.c = c;
            this.color = color;
        }
        private final char c;
        private final java.awt.Color color;
        private static TurnEnum switchTurn(TurnEnum turn) {
            return turn == X ? O : X;
        }
        char getCharacter() {
            return this.c;
        }
        java.awt.Color getColor() {
            return color;
        }

    }
}
