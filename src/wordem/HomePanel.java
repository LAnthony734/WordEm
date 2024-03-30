//
// HomePanel.java
//
// This class is an extension of a JPanel that defines the attributes and behavior of the home panel in the main frame.
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
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;

public class HomePanel extends JPanel implements ActionListener
{
    //
    // Overloaded constructor. Constructs with an instance of IMainFrame.
    //
    public HomePanel(IMainFrame mainFrame) throws Exception
    {
        _initialize(mainFrame);
        _initializeLanguageButtons();
        _initializeGameModeButtons();
        _initializePlayButton();
        _initializeTitle();
    }
    
    //
    // This method adds a passed action listener to the current array of action listeners.
    //
    public void addActionListener(ActionListener actionListener)
    {
        ActionListener[] newActionListeners = new ActionListener[_actionListeners.length + 1];

        for (int index = 0; index < _actionListeners.length; ++index)
        {
            newActionListeners[index] = _actionListeners[index];
        }
        
        newActionListeners[_actionListeners.length] = actionListener;
        
        _actionListeners = newActionListeners;
    }
    
    //
    // This method handles all action events heard by this panel.
    //
    public void actionPerformed(ActionEvent event)
    {
        ActionEvent eventForward = null;
        
        if (event.getSource() == _playButton)
        {
            Object languageSelection = _languageOptions.getSelectedItem();
            Object gameModeSelection = _gameModeOptions.getSelectedItem();
            
            if (languageSelection != null)
            {
                if (gameModeSelection != null)
                {
                    Language language = Language.valueOf(languageSelection.toString());
                    GameMode gameMode = GameMode.valueOf(gameModeSelection.toString());
                    
                    _mainFrame.setLanguage(language);
                    _mainFrame.setGameMode(gameMode);
                    
                    eventForward = new ActionEvent(this, event.getID(), "Play");
                }
                else
                {
                    System.out.println("Select a game mode");
                }
            }
            else
            {
                System.out.println("Select a language");
            }
        }
        
        //
        // If an action event was created, pass it to every handler of this panel:
        //
        if (eventForward != null)
        {
            for(int index = 0; index < _actionListeners.length; ++index)
            {
                _actionListeners[index].actionPerformed(eventForward);
            }
        }
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
        this.setBackground(new Color(245,245,245));
        graphics.drawImage(_backgroundImage, 0, 0, this);
    }
    
    //
    // This method initializes this panel.
    //
    private void _initialize(IMainFrame mainFrame)
    {
        _mainFrame       = mainFrame;
        _actionListeners = new ActionListener[0];
        _toolkit         = Toolkit.getDefaultToolkit();
        _backgroundImage = _toolkit.getImage("art/Home Panel Background.png");
        
        this.setFocusable(false);
        this.setLayout(null);
        this.setVisible(false);
    }
    
    //
    // This method initializes the language buttons on this panel.
    //
    private void _initializeLanguageButtons() throws Exception
    {    
        //
        // Create combo box:
        //
        Language[]  languages             = Language.values();
        String      languageName          = "";
        String      languageImageFileName = "";
        ImageIcon   languageIcon         = null;
        ImageIcon[] languageIcons        = new ImageIcon[languages.length];
        int         index                 = 0;
        
        for (Language language : languages)
        {
            languageName          = language.toString();
            languageImageFileName = languageName + " Language.png";
            languageIcon         = new ImageIcon("art/" + languageImageFileName);
            
            if (languageIcon.getImageLoadStatus() != MediaTracker.COMPLETE)
            {
                throw new Exception("HomePanel._initializeLanguageButtons >> Could not load language image file: " + languageImageFileName);
            }
            
            languageIcon.setDescription(languageName);
            languageIcons[index] = languageIcon;
            ++index;
        }

        _languageOptions = new JComboBox<ImageIcon>(languageIcons);
        _languageOptions.setBounds(123, 475, 314, 76);
        _languageOptions.setBackground(new Color(250,250,250));
        _languageOptions.setFocusable(false);
        
        //
        // Create combo box shadow:
        //
        JLabel comboBoxShadow = new JLabel();
        
        comboBoxShadow.setBounds(118, 480, 314, 76);
        comboBoxShadow.setOpaque(true);
        comboBoxShadow.setBackground(new Color(100,100,100));
        comboBoxShadow.setVisible(true);
        
        this.add(_languageOptions);
        this.add(comboBoxShadow);
    }
    
    //
    // This method initializes the game mode combo box on this panel.
    //
    private void _initializeGameModeButtons() throws Exception
    {
        //
        // Create the combo box:
        //
        GameMode[]  gameModes             = GameMode.values();
        String      gameModeName          = "";
        String      gameModeImageFileName = "";
        ImageIcon   gameModeImage         = null;
        ImageIcon[] gameModeImages        = new ImageIcon[gameModes.length];
        int         index                 = 0;
        
        for (GameMode gameMode : gameModes)
        {
            gameModeName          = gameMode.toString();
            gameModeImageFileName = gameModeName + " Game Mode.png";
            gameModeImage         = new ImageIcon("art/" + gameModeImageFileName);
            
            if (gameModeImage.getImageLoadStatus() != MediaTracker.COMPLETE)
            {
                throw new Exception("HomePanel._initializeGameModeButtons >> Could not load game mode image file: " + gameModeImageFileName);
            }
            
            gameModeImage.setDescription(gameModeName);
            gameModeImages[index] = gameModeImage;
            ++index;
        }

        _gameModeOptions = new JComboBox<ImageIcon>(gameModeImages);      
        _gameModeOptions.setBounds(557, 475, 314, 76);
        _gameModeOptions.setBackground(new Color(250,250,250));
        _gameModeOptions.setFocusable(false);
        
        //
        // Create combo box shadow:
        //
        JLabel comboBoxShadow = new JLabel();
        comboBoxShadow.setBounds(552, 480, 314, 76);
        comboBoxShadow.setOpaque(true);
        comboBoxShadow.setBackground(new Color(100,100,100));
        comboBoxShadow.setVisible(true);
      
        
        this.add(_gameModeOptions);
        this.add(comboBoxShadow);
    }
    
    //
    // This method initializes the play button on this panel.
    //
    private void _initializePlayButton() throws Exception
    {
        ImageIcon playButtonStaticImage = new ImageIcon("art/Play Button Static.png");
        
        if (playButtonStaticImage.getImageLoadStatus() != MediaTracker.COMPLETE)
        {
            throw new Exception("HomePanel._initializeGameModeButtons >> Could not load game mode image file: " + playButtonStaticImage);
        }
        
        ImageIcon playButtonRolloverImage = new ImageIcon("art/Play Button Rollover.png");
        
        if (playButtonRolloverImage.getImageLoadStatus() != MediaTracker.COMPLETE)
        {
            throw new Exception("HomePanel._initializeGameModeButtons >> Could not load game mode image file: " + playButtonRolloverImage);
        } 
        
        Border playButtonBorder = BorderFactory.createEmptyBorder();
        
        _playButton = new JButton(playButtonStaticImage);

        _playButton.setBounds(372, 324, 250, 100);
        _playButton.setBorder(playButtonBorder);
        _playButton.setContentAreaFilled(false);
        _playButton.setRolloverEnabled(true);
        _playButton.setRolloverIcon(playButtonRolloverImage);
        _playButton.addActionListener(this);
        this.add(_playButton);
    }
    
    //
    // This method initialized the WordEm title label on this panel.
    //
    private void _initializeTitle() throws Exception
    {
        ImageIcon titleImage = new ImageIcon("art/Title.png");
        
        if (titleImage.getImageLoadStatus() != MediaTracker.COMPLETE)
        {
            throw new Exception("HomePanel._initializeGameModeButtons >> Could not load game mode image file: " + titleImage);
        }
        
        JLabel title = new JLabel(titleImage);
        
        title.setBounds(113, 20, 700, 259);
        
        this.add(title);
    }
    
    //
    // Private member variables:
    //
    private IMainFrame           _mainFrame;
    private ActionListener[]     _actionListeners;
    private JButton              _playButton;
    private JComboBox<ImageIcon> _languageOptions;
    private JComboBox<ImageIcon> _gameModeOptions;
    private Toolkit              _toolkit;
    private Image                _backgroundImage;
}
