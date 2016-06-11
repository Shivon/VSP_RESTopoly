package clientUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 * Created by Jana Mareike on 11.05.2016.
 */
public class GamesWindowUI {
    private JFrame _mainFrame;
    private JPanel _allGamesPanel;
    private JTable _allGamesTable;
    private DefaultTableModel _tableModel;
    private TableColumn _gamesColumn;
    private JPanel _takePartButtonPanel;
    private JButton _takePartButton;
    private JButton _newGameButton;
//    private  JButton _startGameButton;



    public GamesWindowUI(){
        _mainFrame = new JFrame("Available Games");
        _allGamesPanel = new JPanel();
        _tableModel = new DefaultTableModel();
        _gamesColumn = new TableColumn();
        _allGamesTable =  new JTable(_tableModel);
        _takePartButtonPanel = new JPanel();
        _takePartButton = new JButton("Join Game");
        _newGameButton = new JButton("New Game");
//        _startGameButton = new JButton("Start Game");

        _takePartButtonPanel.setBorder(new EmptyBorder(2,3,2,3));
        _takePartButtonPanel.setPreferredSize(new Dimension(300,50));
        _takePartButtonPanel.add(_takePartButton);
        _takePartButtonPanel.add(_newGameButton);
//        _takePartButtonPanel.add(_startGameButton);

        _allGamesPanel.setBorder(new EmptyBorder(2,3,2,3));
        _allGamesPanel.setPreferredSize(new Dimension(300,450));

        _allGamesTable.setBackground(Color.DARK_GRAY);
        _allGamesTable.setForeground(Color.ORANGE);
        _allGamesTable.setSelectionBackground(Color.BLUE);
        _allGamesTable.setSelectionForeground(Color.CYAN);
        _tableModel.addColumn(_gamesColumn);
        _allGamesTable.setSelectionMode(0);
        _allGamesTable.setRowSelectionAllowed(true);
        _allGamesTable.setPreferredSize(new Dimension(400,10000));
        _allGamesTable.setEnabled(false);

        _allGamesPanel.add(_allGamesTable, BorderLayout.CENTER );

        _mainFrame.setSize(450, 550);
        _mainFrame.add(_allGamesPanel, BorderLayout.NORTH);
        _mainFrame.add(_takePartButtonPanel, BorderLayout.PAGE_END);

        _mainFrame.setVisible(false);
        _mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public DefaultTableModel getTableModel(){ return _tableModel;}

    public JFrame getMainFrame(){
        return _mainFrame;
    }

    public JTable getAllGameTable(){
        return _allGamesTable;
    }

    public JButton getTakePartButton(){
        return _takePartButton;
    }

    public JButton getNewGameButton() { return _newGameButton; }

//    public JButton getStartGameButton() { return  _startGameButton; }
}
