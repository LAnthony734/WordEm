//
// StatisticsPanel.java
//
// This class is an extension of a JPanel that defines the attributes and behavior of the statistics panel in the main frame.
//
// The MIT License (MIT)
// 
// Copyright (c) 2022 WordEm Development Group.  All Rights Reserved.
// 
// Permission is hereby granted, free of charge, to any person obtaining a copy of this
// software and associated documentation files (the "Software"), to deal in the Software
// without restriction, including without limitation the rights to use, copy, modify, merge,
// publish, distribute, sub-license, and/or sell copies of the Software, and to permit persons
// to whom the Software is furnished to do so, subject to the following conditions:
// 
// * The above copyright notice and this permission notice shall be included in all copies or
// substantial portions of the Software.
// 
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
// INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
// PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
// FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
// DEALINGS IN THE SOFTWARE.
//
package wordem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class StatisticsPanel extends JPanel
{
    //
    // Default constructor. Constructs with default values.
    //
    public StatisticsPanel()
    {
        _initialize();
        _initializeStatisticsText();
        _initializeScrollPane();
    }
    
    //
    // This method clears the statistics text and reconstructs it with the current statistics.
    //
    public void update() throws SQLException
    {
        _statisticsText.setText("");
        _databaseManager.connect();

        int value = 0;
        
        //
        // Global statistics:
        //
        _statisticsText.append("[Global Stats]\n");

        value = _databaseManager.getGamesPlayedStatistic(null);
        _statisticsText.append(value + " games played\n");
        
        value = _databaseManager.getGamesQuitStatistic(null);
        _statisticsText.append(value + " games quit\n");
            
        value = _databaseManager.getGamesWonStatistic(null);
        _statisticsText.append(value + " games won\n");
        
        value = _databaseManager.getGamesLostStatistic(null);
        _statisticsText.append(value + " games lost\n");
        
        value = _databaseManager.getWinPercentageStatistic(null);
        _statisticsText.append(value + "% win percentage\n");
        
        _statisticsText.append("\n");
        
        //
        // Game mode statistics:
        //
        for (GameMode gameMode : GameMode.values())
        {
            value = _databaseManager.getGamesPlayedStatistic(gameMode);
            
            _statisticsText.append("[" + gameMode + " Stats]\n");

            value = _databaseManager.getGamesPlayedStatistic(gameMode);
            _statisticsText.append(value + " games played\n");
            
            value = _databaseManager.getGamesQuitStatistic(gameMode);
            _statisticsText.append(value + " games quit\n");
                
            value = _databaseManager.getGamesWonStatistic(gameMode);
            _statisticsText.append(value + " games won\n");
            
            value = _databaseManager.getGamesLostStatistic(gameMode);
            _statisticsText.append(value + " games lost\n");
            
            value = _databaseManager.getWinPercentageStatistic(gameMode);
            _statisticsText.append(value + "% win percentage\n");
            
            _statisticsText.append("\n");
        }

        _databaseManager.disconnect();
        
        _statisticsText.select(0,0); // Force scroll bar to top
    }
    
    //
    // This method paints the panel with preset graphics and a background image.
    //
    //      graphics - this encapsulates state information for graphics rendering. 
    //                 It is provided by the JVM and must NOT be modified.
    //
    @Override
    protected void paintComponent(Graphics graphics)
    {
        super.paintComponent(graphics);
        graphics.drawImage(_backgroundImage, 0, 0, this);
    }
    
    //
    // This method initializes this panel.
    //
    private void _initialize()
    {
        _databaseManager = new DatabaseManager();
        _actionListeners = new ActionListener[0];
        _toolkit         = Toolkit.getDefaultToolkit();
        _backgroundImage = _toolkit.getImage("art/Statistics Panel Background.png");
        
        this.setFocusable(false);
        this.setLayout(null);
        this.setVisible(false);
    }
    
    //
    // This method initializes the statistics text area for the scroll pane.
    //
    private void _initializeStatisticsText()
    {
        Font font = new Font("Monospaced", Font.BOLD, 20);
        
        _statisticsText = new JTextArea();
        _statisticsText.setBackground(new Color(249,249,249));
        _statisticsText.setEditable(false);
        _statisticsText.setFont(font);
    }
    
    //
    // This method initializes the scroll pane in this panel.
    //
    private void _initializeScrollPane()
    {
        _scrollPane = new JScrollPane(_statisticsText);
        _scrollPane.setBounds(298, 162, 380, 487);
        _scrollPane.setBorder(BorderFactory.createEmptyBorder());        
        _scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  
        _scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
      
        this.add(_scrollPane);
    }
       
    //
    // Private member variables:
    //
    private DatabaseManager  _databaseManager;
    private ActionListener[] _actionListeners;
    private Toolkit          _toolkit;
    private Image            _backgroundImage;
    private JTextArea        _statisticsText;
    private JScrollPane      _scrollPane;
}
