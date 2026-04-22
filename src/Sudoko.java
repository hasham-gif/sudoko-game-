import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Sudoko {
    class Tile extends JButton{
        int r;
        int c;

        Tile (int r,int c)
        {
            this.r = r;
            this.c = c;
        }
    }
     int boardWidth = 600;
      int boardHeight = 650;

      String [] puzzle =  {
              "--74916-5",
              "2---6-3-9",
              "-----7-1-",
              "-586----4",
              "--3----9-",
              "--62--187",
              "9-4-7---2",
              "67-83----",
              "81--45---"
    };

    String [] solution  = {
            "387491625",
            "241568379",
            "569327418",
            "758619234",
            "123784596",
            "496253187",
            "934176852",
            "675832941",
            "812945763"
    };

    JFrame frame = new JFrame();
      JLabel textlable = new JLabel();

      JPanel textpanel = new JPanel();
      JPanel  boardPanel = new JPanel();
      JPanel  buttonsPanel = new JPanel();

      JButton numSelected = null;
      int error = 0;


      Sudoko (){
          frame.setSize(boardWidth,boardHeight);
          frame.setResizable(false);
          frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
          frame.setLocationRelativeTo(null);
          frame.setLayout(new BorderLayout());

          ImageIcon image = new ImageIcon("images2.jfif");
          frame.setIconImage(image.getImage());



          textlable.setFont(new Font("Arial",Font.BOLD, 30 ));
          textlable.setHorizontalAlignment(JLabel.CENTER);
          textlable.setText("Sudoko = 0");

          textpanel.add(textlable);
          frame.add(textpanel,BorderLayout.NORTH);
          boardPanel.setLayout(new GridLayout(9, 9));
          setupTiles();
          frame.add(boardPanel, BorderLayout.CENTER);

          buttonsPanel.setLayout(new GridLayout(1, 9));
          setupButtons();
          frame.add(buttonsPanel, BorderLayout.SOUTH);

          frame.setVisible(true);
      }

    void setupTiles() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Tile tile = new Tile(r, c);
                char tileChar = puzzle[r].charAt(c);
                if (tileChar != '-') {
                    tile.setFont(new Font("Arial", Font.BOLD, 20));
                    tile.setText(String.valueOf(tileChar));
                    tile.setBackground(Color.lightGray);
                }
                else {
                    tile.setFont(new Font("Arial", Font.PLAIN, 20));
                    tile.setBackground(Color.white);
                }
                if ((r == 2 && c == 2) || (r == 2 && c == 5) || (r == 5 && c == 2) || (r == 5 && c == 5)) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 5, Color.black));
                }
                else if (r == 2 || r == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 5, 1, Color.black));
                }
                else if (c == 2 || c == 5) {
                    tile.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 5, Color.black));
                }
                else {
                    tile.setBorder(BorderFactory.createLineBorder(Color.black));
                }
                tile.setFocusable(false);
                boardPanel.add(tile);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        Tile tile = (Tile) e.getSource();
                        int r = tile.r;
                        int c = tile.c;
                        if (numSelected != null) {
                            if (tile.getText() != "") {
                                return;
                            }
                            String numSelectedText = numSelected.getText();
                            String tileSolution = String.valueOf(solution[r].charAt(c));
                            if (tileSolution.equals(numSelectedText)) {
                                tile.setText(numSelectedText);

                                // CHECK IF PUZZLE IS COMPLETE
                                boolean allFilled = true;
                                for (int row = 0; row < 9; row++) {
                                    for (int col = 0; col < 9; col++) {
                                        Tile t = (Tile) boardPanel.getComponent(row * 9 + col);
                                        if (t.getText() == null || t.getText().isEmpty()) {
                                            allFilled = false;
                                            break;
                                        }
                                    }
                                }

                                if (allFilled) {
                                    JOptionPane.showMessageDialog(frame,
                                            "CONGRATULATIONS! You solved the puzzle! 🎉\nErrors: " + error,
                                            "You Win!",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                            else {
                                error += 1;
                                textlable.setText("Sudoku: " + String.valueOf(error));
                            }

                        }
                    }
                });
            }
        }
    }

    void setupButtons() {
        for (int i = 1; i < 10; i++) {
            JButton button = new JButton();
            button.setFont(new Font("Arial", Font.BOLD, 20));
            button.setText(String.valueOf(i));
            button.setFocusable(false);
            button.setBackground(Color.white);
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    if (numSelected != null) {
                        numSelected.setBackground(Color.white);
                    }
                    numSelected = button;
                    numSelected.setBackground(Color.BLUE);
                }
            });
        }
    }

    boolean isPuzzleComplete() {
        for (int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                Tile tile = (Tile) boardPanel.getComponent(r * 9 + c);
                if (tile.getText() == null || tile.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }


}