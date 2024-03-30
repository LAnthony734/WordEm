//
// MainFrame.java
//
// This class is an extension of a JFrame and owns and manages the home panel, statistics panel,
// achievement panel, about panel, and a current game panel, as well as handles all SQLExceptions 
// thrown by these panels.
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
import javax.swing.border.Border;

public class MainFrame extends JFrame implements IMainFrame, ActionListener, KeyListener
{
    //
    // Default constructor. Constructs the frame.
    //
    public MainFrame()
    {
        _initialize();   
        _initializeToolBar();
        _initializeStatisticsPanel();
        _initializeAchievementsPanel();
        _initializeAboutPanel();
        _initializeHomePanel();

        _homePanel.repaint();
        _homePanel.setVisible(true);
        
        this.repaint();
        this.setVisible(true);
    }
      
    //
    // This method sets the language of the game.
    //
    public void setLanguage(Language language)
    {
        _language = language;
    }

    //
    // This method gets the language of the game.
    //
    public Language getLanguage()
    {
        return _language;
    }
    //
    // This method sets the game mode.
    //
    public void setGameMode(GameMode gameMode)
    {
        _gameMode = gameMode;
    }

    //
    // This method gets the game mode.
    //
    public GameMode getGameMode()
    {
        return _gameMode;
    }

    //
    // This method handles all action events heard by the frame.
    //
    public void actionPerformed(ActionEvent event)
    {
        //
        // Handle the quit button:
        //
        if (event.getSource() == _quitButton)
        {
            if (_statisticsPanel.isVisible())
            {
                _statisticsPanel.setVisible(false);
                this.remove(_statisticsPanel);
                
                this.add(_homePanel);
                _homePanel.setVisible(true);
                
                _statisticsButton.setEnabled(true);
                _achievementsButton.setEnabled(true);
                _aboutButton.setEnabled(true);
                _quitButton.setEnabled(false);
            }
            else if (_achievementsPanel.isVisible())
            {
                _achievementsPanel.setVisible(false);
                this.remove(_achievementsPanel);
                
                this.add(_homePanel);
                _homePanel.setVisible(true);
                
                _statisticsButton.setEnabled(true);
                _achievementsButton.setEnabled(true);
                _aboutButton.setEnabled(true);
                _quitButton.setEnabled(false);
            }
            else if (_aboutPanel.isVisible())
            {
                _aboutPanel.setVisible(false);
                this.remove(_aboutPanel);
                
                this.add(_homePanel);
                _homePanel.setVisible(true);
                
                _statisticsButton.setEnabled(true);
                _achievementsButton.setEnabled(true);
                _aboutButton.setEnabled(true);
                _quitButton.setEnabled(false);
            }
            else
            {
                _handleQuitGame();
            }
        }
        //
        // Handle the statistics button:
        //
        else if (event.getSource() == _statisticsButton)
        {
            _homePanel.setVisible(false);
            this.remove(_homePanel);
            
            this.add(_statisticsPanel, BorderLayout.CENTER); 
            _statisticsPanel.setVisible(true);
            
            try
            {
                _statisticsPanel.update();
            }
            catch (SQLException ex)
            {
                _handleError(ErrorCode.ProblemWithStatisticsPanel);
            }
            
            _statisticsButton.setEnabled(false);
            _achievementsButton.setEnabled(false);
            _aboutButton.setEnabled(false);
            _quitButton.setEnabled(true);
        }
        //
        // Handle the achievements button:
        //
        else if (event.getSource() == _achievementsButton)
        {
            _homePanel.setVisible(false);
            this.remove(_homePanel);
            
            this.add(_achievementsPanel, BorderLayout.CENTER);
            _achievementsPanel.setVisible(true);
            
            _statisticsButton.setEnabled(false);
            _achievementsButton.setEnabled(false);
            _aboutButton.setEnabled(false);
            _quitButton.setEnabled(true);
        }
        //
        // Handle the about button:
        //
        else if (event.getSource() == _aboutButton)
        {
            _homePanel.setVisible(false);
            this.remove(_homePanel);
            
            this.add(_aboutPanel, BorderLayout.CENTER);
            _aboutPanel.setVisible(true);
            
            _statisticsButton.setEnabled(false);
            _achievementsButton.setEnabled(false);
            _aboutButton.setEnabled(false);
            _quitButton.setEnabled(true);
        }
        //
        // Handle the home panel:
        //
        else if (event.getSource() == _homePanel)
        {
            //
            // Handle the play button:
            //
            if (event.getActionCommand().equals("Play"))
            {
                try
                {
                    _gamePanel = GamePanelFactory.createGamePanel(this);
                    _gamePanel.addActionListener(this);
                    _gamePanel.addKeyListener(this);
                    this.add(_gamePanel.getComponent());
                    
                    _statisticsButton.setEnabled(false);
                    _achievementsButton.setEnabled(false);
                    _aboutButton.setEnabled(false);
                    _quitButton.setEnabled(true);
                    
                    _homePanel.setVisible(false);
                    _gamePanel.getComponent().setVisible(true);
                    _gamePanel.start();
                }
                catch (SQLException ex)
                {
                    _handleError(ErrorCode.ProblemWithDatabase);
                }
                catch (Exception ex)
                {
                    _handleError(ErrorCode.ProblemWithGamePanel);
                }
            }
        }
        //
        // Handle the game panel:
        //
        else if (event.getSource() == _gamePanel)
        {
            if (event.getActionCommand() == "Win")
            {
                _handleEndOfGame(true);
            }           
            else if (event.getActionCommand() == "Lose")
            {
                _handleEndOfGame(false);
            }
        }
    }
    
    //
    // This method handles all key-pressed events heard by the frame. This implementation
    // currently only exists in this class because the KeyListener interface requires it.
    // No handling actually occurs.
    //
    public void keyPressed(KeyEvent event)
    {
        // Doing nothing
    }

    //
    // This method handles all key-released events heard by the frame. This implementation
    // currently only exists in this class because the KeyListener interface requires it.
    // No handling actually occurs.
    //
    public void keyReleased(KeyEvent event)
    {
        // Doing nothing
    }

    //
    // This method handles all key-typed events heard by the frame.
    //
    public void keyTyped(KeyEvent event)
    {
        //
        // Handle the game panel:
        //
        if (event.getSource() == _gamePanel)
        {
            char keyChar = event.getKeyChar();
            
            if (keyChar == 27) // Escape
            {
                _handleQuitGame();
            }
        }
    }

    //
    // This method initializes the frame.
    //
    private void _initialize()
    {
        _language        = null;
        _gameMode        = null;
        _databaseManager = new DatabaseManager();
        
        BorderLayout frameLayout = new BorderLayout();
        Toolkit      toolkit     = Toolkit.getDefaultToolkit();
        Image        logoImage   = toolkit.getImage("art/Logo.png");

        this.setLayout(frameLayout);
        this.setBounds(350, 100, 1000, 800);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("WordEm\u00A9");
        this.setIconImage(logoImage); 
        this.setFocusable(false);
        this.setVisible(true);
    }

    //
    // This method initializes the tool bar on the frame.
    //
    private void _initializeToolBar()
    {
        //
        // Configure tool bar:
        //
        FlowLayout toolBarLayout = new FlowLayout(FlowLayout.LEFT, 10, 0);
        Insets     toolBarInsets = new Insets(5,5,5,5);
        
        _toolBar = new JToolBar();
        
        _toolBar.setLayout(toolBarLayout);
        _toolBar.setMargin(toolBarInsets);
        _toolBar.setFloatable(false);

        //
        // Create and add quit button:
        //
        Dimension quitButtonDim    = new Dimension(50, 50);
        Border    quitButtonBorder = BorderFactory.createEmptyBorder();
        ImageIcon quitButtonIcon   = new ImageIcon("art/Quit Button.png");
        
        if (quitButtonIcon.getImageLoadStatus() != MediaTracker.COMPLETE)
        {
            _handleError(ErrorCode.ProblemWithMainFrame);
        }
        
        _quitButton = new JButton(quitButtonIcon);
        _quitButton.setEnabled(false);
        _quitButton.setPreferredSize(quitButtonDim);
        _quitButton.setBorder(quitButtonBorder);
        _quitButton.addActionListener(this);
        
        _toolBar.add(_quitButton);
        
        //
        // Create and add statistics button:
        //
        Dimension statisticsButtonDim    = new Dimension(50, 50);
        Border    statisticsButtonBorder = BorderFactory.createEmptyBorder();
        ImageIcon statisticsButtonIcon   = new ImageIcon("art/Statistics Button.png");
        
        if (statisticsButtonIcon.getImageLoadStatus() != MediaTracker.COMPLETE)
        {
            _handleError(ErrorCode.ProblemWithMainFrame);
        }
        
        _statisticsButton = new JButton(statisticsButtonIcon);
        _statisticsButton.setEnabled(true);
        _statisticsButton.setPreferredSize(statisticsButtonDim);
        _statisticsButton.setBorder(statisticsButtonBorder);
        _statisticsButton.addActionListener(this);
        
        _toolBar.add(_statisticsButton);
        
        //
        // Create and add achievements button:
        //
        Dimension achievementsButtonDim    = new Dimension(50, 50);
        Border    achievementsButtonBorder = BorderFactory.createEmptyBorder();
        ImageIcon achievementsButtonIcon   = new ImageIcon("art/Achievements Button.png");
        
        if (achievementsButtonIcon.getImageLoadStatus() != MediaTracker.COMPLETE)
        {
            _handleError(ErrorCode.ProblemWithMainFrame);
        }
        
        _achievementsButton = new JButton(achievementsButtonIcon);
        _achievementsButton.setEnabled(true);
        _achievementsButton.setPreferredSize(achievementsButtonDim);
        _achievementsButton.setBorder(achievementsButtonBorder);
        _achievementsButton.addActionListener(this);
        
        _toolBar.add(_achievementsButton);
        
        //
        // Create and add about button:
        //
        Dimension aboutButtonDim    = new Dimension(50, 50);
        Border    aboutButtonBorder = BorderFactory.createEmptyBorder();
        ImageIcon aboutButtonIcon   = new ImageIcon("art/About Button.png");
        
        if (achievementsButtonIcon.getImageLoadStatus() != MediaTracker.COMPLETE)
        {
            _handleError(ErrorCode.ProblemWithMainFrame);
        }
        
        _aboutButton = new JButton(aboutButtonIcon);
        _aboutButton.setEnabled(true);
        _aboutButton.setPreferredSize(aboutButtonDim);
        _aboutButton.setBorder(aboutButtonBorder);
        _aboutButton.addActionListener(this);
        
        _toolBar.add(_aboutButton);
        
        this.add(_toolBar, BorderLayout.NORTH);
    }

    //
    // This method initializes the home panel on the frame.
    //
    private void _initializeHomePanel()
    {       
        try
        {
            _homePanel = new HomePanel(this);
            this.add(_homePanel, BorderLayout.CENTER);
            
            _homePanel.addActionListener(this);
            _homePanel.setVisible(true);
        }
        catch (Exception ex)
        {
            _handleError(ErrorCode.ProblemWithHomePanel);
        }
    }

    //
    // This method initializes the statistics panel on the frame.
    //
    private void _initializeStatisticsPanel()
    {
        try
        {
            _statisticsPanel = new StatisticsPanel();           
            _statisticsPanel.setVisible(false);
        }
        catch (Exception ex)
        {
            _handleError(ErrorCode.ProblemWithStatisticsPanel);
        }
    }

    //
    // This method initializes the achievements panel on the frame.
    //
    private void _initializeAchievementsPanel()
    {
        try
        {
            _achievementsPanel = new AchievementsPanel();         
            _achievementsPanel.setVisible(false);
        }
        catch (Exception ex)
        {
            _handleError(ErrorCode.ProblemWithAchievementsPanel);
        }
    }

    //
    // This method initializes the about panel on the frame.
    //
    private void _initializeAboutPanel()
    {
        try
        {
            _aboutPanel = new AboutPanel();         
            _aboutPanel.setVisible(false);
        }
        catch (Exception ex)
        {
            _handleError(ErrorCode.ProblemWithAchievementsPanel);
        }
    }

    //
    // This method handles quitting from a game panel.
    //
    private void _handleQuitGame()
    {
        _gamePanel.pause();
        
        //
        // Create confirmation box:
        //
        JLabel      textArea       = new JLabel(new ImageIcon("art/Quitting.png"));
        JOptionPane pane           = new JOptionPane(textArea, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog     dialog         = pane.createDialog(this, "Quitting?");       
        Point       frameLocation  = this.getLocation();
        double      frameLocationX = frameLocation.getX();
        double      frameLocationY = frameLocation.getY();
        Toolkit     toolKit        = Toolkit.getDefaultToolkit();
        Image       logoImage      = toolKit.getImage("art/Logo.png");
        
        dialog.setBounds((int)(375 + frameLocationX),
                         (int)(300 + frameLocationY),
                         250, 250);
        dialog.setIconImage(logoImage);
        dialog.setVisible(true);
        
        //
        // Handle user response:
        //
        Object    selection          = pane.getValue();
        Component gamePanelComponent = _gamePanel.getComponent();
        
        //
        // Quit game if "Yes":
        //
        if (selection != null && selection.equals(JOptionPane.YES_OPTION))
        {      
            _gamePanel.quit();
            gamePanelComponent.setVisible(false);
            this.remove(gamePanelComponent);
            
            this.add(_homePanel);
            _homePanel.setVisible(true);
            
            _statisticsButton.setEnabled(true);
            _achievementsButton.setEnabled(true);
            _aboutButton.setEnabled(true);
            _quitButton.setEnabled(false);
            
            //
            // Update statistics:
            //
            try
            {
                GameMode gameMode = _gamePanel.getGameMode();
                
                _databaseManager.connect();      
                _databaseManager.incrementGamesQuitStatistic(null);
                _databaseManager.incrementGamesQuitStatistic(gameMode);
                _databaseManager.disconnect();
            }
            catch (SQLException ex)
            {
                _handleError(ErrorCode.ProblemWithDatabase);
            }
        }
        //
        // Otherwise resume game:
        //
        else
        {
            try
            {
                _gamePanel.resume();
            }
            catch (Exception ex)
            {
                _handleError(ErrorCode.ProblemWithGamePanel);
            }
        }
    }

    //
    // This method handles the end of a game. A dialog box is displayed asking the user if they
    // want to quit or replay the game.
    //
    //      isWinner - whether the player ended the game as a winner
    //
    private void _handleEndOfGame(boolean isWinner)
    {
        //
        // Update statistics:
        //
        String exclamation = "";
        
        try
        {
            GameMode gameMode = _gamePanel.getGameMode();
            
            _databaseManager.connect();      
            _databaseManager.incrementGamesPlayedStatistic(null);
            _databaseManager.incrementGamesPlayedStatistic(gameMode);
            
            if (isWinner)
            {
                exclamation = "Hooray! You guessed it!";
                _databaseManager.incrementGamesWonStatistic(null);
                _databaseManager.incrementGamesWonStatistic(gameMode);
            }
            else
            {
                exclamation = "Oof! Better luck next time!";
                _databaseManager.incrementGamesLostStatistic(null);
                _databaseManager.incrementGamesLostStatistic(gameMode);
            }
            
            _databaseManager.updateWinPercentageStatistic(null);
            _databaseManager.updateWinPercentageStatistic(gameMode);
            _databaseManager.disconnect();
        }
        catch (SQLException ex)
        {
            _handleError(ErrorCode.ProblemWithDatabase);
        }
        
        //
        // Create dialog box:
        //
        JLabel      textLabel      = new JLabel(new ImageIcon("art/Play Again.png"));
        JOptionPane pane           = new JOptionPane(textLabel, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog     dialog         = pane.createDialog(this, exclamation);
        Point       frameLocation  = this.getLocation();
        double      frameLocationX = frameLocation.getX();
        double      frameLocationY = frameLocation.getY();
        Toolkit     toolKit        = Toolkit.getDefaultToolkit();
        Image       logoImage      = toolKit.getImage("art/Logo.png");       
        
        pane.setBackground(new Color(238,238,238));

        dialog.setBounds((int)(275 + frameLocationX),
                         (int)(275 + frameLocationY),
                         450, 350);
        dialog.setIconImage(logoImage);
        dialog.setVisible(true);
        
        //
        // Handle user response:
        //
        Object    selection          = pane.getValue();
        Component gamePanelComponent = _gamePanel.getComponent();
        
        if (selection == null || selection.equals(JOptionPane.YES_OPTION))
        {
            try
            {
                _gamePanel.start();
            }
            catch(Exception ex)
            {
                _handleError(ErrorCode.ProblemWithGamePanel);
            }
        }
        else if (selection.equals(JOptionPane.NO_OPTION))
        {
            _gamePanel.quit();
            gamePanelComponent.setVisible(false);
            this.remove(gamePanelComponent);
            
            this.add(_homePanel);
            _homePanel.setVisible(true);
            
            _statisticsButton.setEnabled(true);
            _achievementsButton.setEnabled(true);
            _aboutButton.setEnabled(true);
            _quitButton.setEnabled(false);
        }
    }

    //
    // This method creates an error message for a given error code and displays an error dialog box to the user.
    // Upon responding to this dialog box, the program is terminated.
    //
    private void _handleError(ErrorCode errorCode)
    {
        String errorMessage = "";
        
        //
        // Create error message:
        //
        switch (errorCode)
        {
            case ProblemWithMainFrame:
            {
                errorMessage = "It seems there was a problem loading the game.\n"
                             + "Please resolve or reinstall the game folder.";
                break;
            }
            
            case ProblemWithHomePanel:
            {
                errorMessage = "It seems there was a problem loading the home page.\n"
                             + "Please resolve or reinstall the game folder.";
                break;
            }
            
            case ProblemWithStatisticsPanel:
            {
                errorMessage = "It seems there was a problem loading the statistics page.\n"
                             + "Please resolve or reinstall the game folder.";
                break;
            }
            
            case ProblemWithAchievementsPanel:
            {
                errorMessage = "It seems there was a problem loading the achievements page.\n"
                             + "Please resolve or reinstall the game folder.";
                break;
            }
            
            case ProblemWithGamePanel:
            {
                errorMessage = "It seems there was a problem loading the game page.\n"
                             + "Please resolve or reinstall the game folder.";
                break;
            }
            
            case ProblemWithDatabase:
            {
                errorMessage = "It seems there was a problem accessing game data.\n"
                             + "Please resolve or reinstall the game folder.";
                break;
            }
           
            default:
            {
                errorMessage = "It seems there's some problem with your current game files.\n"
                             + "Please resolve or reinstall the game folder.";
                break;
            }
        }
        
        //
        // Create and display error dialog box:
        //
        JTextArea textArea = new JTextArea(500, 250);
        Font      font     = new Font("SansSarif", Font.PLAIN, 15);
        
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setOpaque(false);
        textArea.setFont(font);
        textArea.setText(errorMessage);
        
        JOptionPane pane           = new JOptionPane(textArea, JOptionPane.ERROR_MESSAGE, JOptionPane.DEFAULT_OPTION);
        JDialog     dialog         = pane.createDialog(this, "Oops!");       
        Point       frameLocation  = this.getLocation();

        dialog.setBounds((int)(250 + frameLocation.getX()),
                         (int)(300 + frameLocation.getY()),
                         500, 250);
        dialog.setVisible(true);
        
        System.exit(-1);
    }
    
    //
    // Private member variables:
    //
    private Language          _language;
    private GameMode          _gameMode;
    private JToolBar          _toolBar;
    private JButton           _quitButton;
    private JButton           _statisticsButton;
    private JButton           _achievementsButton;
    private JButton           _aboutButton;
    private HomePanel         _homePanel;
    private StatisticsPanel   _statisticsPanel;
    private AchievementsPanel _achievementsPanel;
    private AboutPanel        _aboutPanel;
    private IGamePanel        _gamePanel;
    private DatabaseManager   _databaseManager;
}  
