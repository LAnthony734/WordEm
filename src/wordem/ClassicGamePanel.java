//
// ClassicGamePanel.java
//
// This class is an extension of a JPanel that defines the attributes and behavior of the
// implementation of a game panel for the Classic game mode.
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
import javax.swing.text.DefaultEditorKit;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class ClassicGamePanel extends JPanel implements IGamePanel, ActionListener, KeyListener
{
    //
    // Overloaded constructor. Constructs with a parent main frame interface. This interface
    // is used to extract the desired language.
    //
    public ClassicGamePanel(IMainFrame mainFrame) throws Exception
    {
        _initialize(mainFrame);
        _initializeNotAWordLabel();
        _initializeHiddenWordLabel();
    }
    
    //
    // This method gets the game mode. This method is used by the parent main frame
    // for updating statistics.
    //
    public GameMode getGameMode()
    {
        return _GAME_MODE;
    }
    
    //
    // This method starts the game operations. For reference, the _gameIsRunning state variable is set here.
    // Event handling is skipped while the _gameIsRunning state variable is not set.
    //
    public void start() throws Exception
    {     
        if (_wordTextField != null)
        {
            this.remove(_wordTextField);
        }
        
        _initializeWordTextField();
        
        _databaseManager.connect();
        _hiddenWord = _databaseManager.getRandomWord(_mainFrame.getLanguage(), _WORD_SIZE);        
        _databaseManager.disconnect();
        
        _hiddenWordLabel.setText(_hiddenWord);
        _hiddenWordLabel.setVisible(false);
        _gameIsRunning      = true;
        _currentWordIndex   = 0;
        _currentLetterIndex = 0;
        _wordTextField.requestFocusInWindow();
    }
    
    //
    // This method pauses operations of the game.
    //
    public void pause()
    {
        _gameIsRunning = false;
    }
    
    //
    // This method resumes operations of the game.
    //
    public void resume()
    {
        _wordTextField.requestFocusInWindow();
        _gameIsRunning = true;
    }
    
    //
    // This method performs necessary operations for quitting the game.
    //
    public void quit()
    {
        _wordTextField = null;
        _gameIsRunning = false;
    }
    
    //
    // This method returns the instantiated game panel as a Component object for
    // necessary method calls not recognized by the game panel interface.
    //
    public Component getComponent()
    {
        return this;
    }    
    
    //
    // This method adds a given action listener to the current array of action listeners.
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
    // This method adds a given key listener to the current array of key listeners.
    //
    public void addKeyListener(KeyListener keyListener)
    {
        KeyListener[] newKeyListeners = new KeyListener[_keyListeners.length + 1];

        for (int index = 0; index < _keyListeners.length; ++index)
        {
            newKeyListeners[index] = _keyListeners[index];
        }
        
        newKeyListeners[_keyListeners.length] = keyListener;
        
        _keyListeners = newKeyListeners;
    }
    
    //
    // This method handles all action events heard by the game panel. This implementation
    // currently only exists in this class because the ActionListener interface requires it.
    // No handling actually occurs.
    //
    public void actionPerformed(ActionEvent event)
    {
        // No implementation defined
    }
    
    //
    // This method handles all key-pressed events heard by the panel. This implementation
    // currently only exists in this class because the KeyListener interface requires it.
    // No handling actually occurs.
    //
    public void keyPressed(KeyEvent event)
    {
        // Doing nothing
    }
    
    //
    // This method handles all key-released events heard by the panel. This implementation
    // currently only exists in this class because the KeyListener interface requires it.
    // No handling actually occurs.
    //
    public void keyReleased(KeyEvent event)
    {
        // Doing nothing
    }
    
    //
    // This method handles all key-typed events heard by the game panel.
    //
    public void keyTyped(KeyEvent event)
    {
        KeyEvent eventForward = null;
        char     keyChar      = event.getKeyChar();
        String   keyCharStr   = "" + keyChar;
        
        try
        {  
            if (keyCharStr.equals("\u221E")) // Infinity: alt-236
            {
                _hiddenWordLabel.setVisible(true);
            }
            else if (keyChar == KeyEvent.VK_SPACE)
            {
                // Ignore
            }
            else if (keyChar == KeyEvent.VK_DELETE)
            {
                // Ignore
            }
            else if (keyChar == KeyEvent.VK_ESCAPE)
            {
                eventForward = new KeyEvent(this, event.getID(), event.getWhen(), event.getModifiers(), 0, keyChar);
            }
            else if (keyChar == KeyEvent.VK_BACK_SPACE)
            {
                _handleBackspace();
            }
            else if (keyChar == KeyEvent.VK_ENTER)
            {
                _handleEnter();
            }
            else // Any other key with a printable character
            {
                _handleCharacter(keyChar);
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        //
        // If a key event was created, pass it to every handler of this panel:
        //
        if (eventForward != null)
        {
            for(int index = 0; index < _keyListeners.length; ++index)
            {
                _keyListeners[index].keyTyped(eventForward);
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
    // This method initializes the game panel.
    //
    private void _initialize(IMainFrame mainFrame) throws Exception
    {
        _mainFrame          = mainFrame;
        _wordTextField      = null;
        _databaseManager    = new DatabaseManager();
        _hiddenWord         = "";
        _actionListeners    = new ActionListener[0];
        _keyListeners       = new KeyListener[0];
        _toolkit            = Toolkit.getDefaultToolkit();
        _backgroundImage    = _toolkit.getImage("art/Game Panel Background.png");
        _currentWordIndex   = 0;
        _currentLetterIndex = 0;
        _gameIsRunning      = false;
        _panelInsets        = this.getInsets();
        
        this.setFocusable(false);
        this.setLayout(null);
    }
    
    //
    // This method initializes the "not a word" label on the game panel.
    //
    private void _initializeNotAWordLabel() throws Exception
    {
        Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
        
        _notAWordLabel = new JLabel("That's not a word!", SwingConstants.CENTER);
        _notAWordLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        _notAWordLabel.setForeground(Color.BLACK);  
        _notAWordLabel.setBackground(Color.WHITE);
        _notAWordLabel.setOpaque(true);
        _notAWordLabel.setBorder(border);
        _notAWordLabel.setVisible(false);
        _notAWordLabel.setBounds(348 + _panelInsets.left,
                                  15 + _panelInsets.top, 
                                 300, 75);        
        this.add(_notAWordLabel);
    }
    
    //
    // This method initializes the "hidden word" label on the game panel.
    //
    private void _initializeHiddenWordLabel() throws Exception
    {
        _hiddenWordLabel = new JLabel(_hiddenWord, SwingConstants.CENTER);
        _hiddenWordLabel.setFont(new Font("SansSerif", Font.BOLD, 50));
        _hiddenWordLabel.setForeground(Color.BLACK);    
        _hiddenWordLabel.setVisible(false);
        _hiddenWordLabel.setBounds(372 + _panelInsets.left,
                                    15 + _panelInsets.top, 
                                   250, 75);
        this.add(_hiddenWordLabel);
    }
        
    //
    // This method initializes the word text field contained in the game panel.
    //
    private void _initializeWordTextField() throws Exception
    {
        _wordTextField = new _WordTextField();
        _wordTextField.setVisible(true);
        _wordTextField.addKeyListener(this);
        this.add(_wordTextField);
    }
    
    //
    // This method handles a typed "Backspace" key.
    //
    private void _handleBackspace()
    {
        try
        {
            if (_gameIsRunning && _currentWordIndex < _GUESS_LIMIT && _currentLetterIndex > 0)
            {
                _notAWordLabel.setVisible(false);
                
                for (int letterIndex = _currentLetterIndex; letterIndex < _WORD_SIZE; ++letterIndex)
                {
                    _wordTextField.resetTile(_currentWordIndex, letterIndex);
                }
                
                _wordTextField.resetTile(_currentWordIndex, _currentLetterIndex - 1);
                
                --_currentLetterIndex;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
    
    //
    // This method handles a typed "Enter" key.
    //
    private void _handleEnter()
    {
        try
        {
            if (_gameIsRunning && _currentWordIndex < _GUESS_LIMIT && _currentLetterIndex == _WORD_SIZE)
            {
                _databaseManager.connect();
                String  guessedWord = _wordTextField.extractWord(_currentWordIndex);        
                boolean isWord      = _databaseManager.isWord(guessedWord, _mainFrame.getLanguage());
                _databaseManager.disconnect();
                
                if (isWord)
                {
                    boolean isCorrectWord = _handleGuessedWord(guessedWord);                    
                    
                    if (isCorrectWord || _currentWordIndex == _GUESS_LIMIT - 1)
                    {
                        _endGame(isCorrectWord);
                    }
                    else
                    {
                        ++_currentWordIndex;
                        _currentLetterIndex = 0;
                    }
                }
                else
                {
                    _notAWordLabel.setVisible(true);
                }
            }
            else
            {
                for (int letterIndex = _currentLetterIndex; letterIndex < _WORD_SIZE; ++letterIndex)
                {
                    _wordTextField.colorTileRed(_currentWordIndex, letterIndex);
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
    
    //
    // This method handles a typed character.
    //
    private void _handleCharacter(char character)
    {
        try
        {
            if (_gameIsRunning && _currentWordIndex < _GUESS_LIMIT && _currentLetterIndex < _WORD_SIZE)
            {
                for (int letterIndex = _currentLetterIndex; letterIndex < _WORD_SIZE; ++letterIndex)
                {
                    _wordTextField.resetTile(_currentWordIndex, letterIndex); // In the event that these tiles are red
                }
                
                _wordTextField.setTile(character, _currentWordIndex, _currentLetterIndex);
                
                ++_currentLetterIndex;
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
    }
    
    //
    // This method determines if a word guess is correct and colors the letter tiles according.
    // Return true if the guessed word was correct, otherwise false.
    //
    private boolean _handleGuessedWord(String guessedWord)
    {
        boolean isCorrectWord = false;
        
        try
        {
            if (guessedWord.equals(_hiddenWord))
            {
                for (int letterIndex = 0; letterIndex < _WORD_SIZE; ++letterIndex)
                {
                    _wordTextField.colorTileGreen(_currentWordIndex, letterIndex);
                }
                
                isCorrectWord = true;
            }
            else
            {
                String hiddenWordTemp  = _hiddenWord;
                char   guessedLetter   = 0;
                char   hiddenLetter    = 0;

                //
                // Determine green tiles:
                //
                for (int letterIndex = 0; letterIndex < _WORD_SIZE; ++letterIndex)
                {
                    guessedLetter   = guessedWord.charAt(letterIndex);
                    hiddenLetter    = hiddenWordTemp.charAt(letterIndex);
                    
                    if (hiddenLetter == guessedLetter)
                    {
                        _wordTextField.colorTileGreen(_currentWordIndex, letterIndex);

                        hiddenWordTemp  = hiddenWordTemp.substring(0, letterIndex) + '-' + hiddenWordTemp.substring(letterIndex + 1);
                        guessedWord     = guessedWord.substring(0, letterIndex) + '+' + guessedWord.substring(letterIndex + 1);
                    }
                }
                
                //
                // Determine yellow tiles:
                //
                for (int letterIndex = 0; letterIndex < _WORD_SIZE; ++letterIndex)
                {
                    guessedLetter = guessedWord.charAt(letterIndex);
                    
                    if (hiddenWordTemp.contains("" + guessedLetter))
                    {
                        _wordTextField.colorTileYellow(_currentWordIndex, letterIndex);
                        
                        int index = hiddenWordTemp.indexOf("" + guessedLetter);
                        
                        hiddenWordTemp  = hiddenWordTemp.substring(0, index) + '-' + hiddenWordTemp.substring(index + 1);
                        guessedWord     = guessedWord.substring(0, letterIndex) + '+' + guessedWord.substring(letterIndex + 1);
                    }
                }
                
                //
                // Determine gray tiles:
                //
                for (int letterIndex = 0; letterIndex < _WORD_SIZE; ++letterIndex)
                {
                    guessedLetter = guessedWord.charAt(letterIndex);
                    
                    if (guessedLetter != '+')
                    {
                        _wordTextField.colorTileGray(_currentWordIndex, letterIndex);
                    }
                }
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            ex.printStackTrace();
        }
        
        return isCorrectWord;
    }
    
    //
    // This method performs necessary internal operations for ending the game. 
    // A "Win" or "Lose" action is created and forwarded to the panel's action listeners for handling.
    //
    //      isCorrectWord - the flag indicating if the correct word was ever guessed
    //
    private void _endGame(boolean isCorrectWord)
    {
        //
        // Internal operations:
        //
        _gameIsRunning = false;
        _hiddenWordLabel.setVisible(true);
        
        //
        // Create and pass the action event to every handler of this panel:
        //
        ActionEvent eventForward = null;
        
        if (isCorrectWord)
        {
            eventForward = new ActionEvent(this, 0, "Win");
        }
        else
        {
            eventForward = new ActionEvent(this, 0, "Lose");
        }

        if (eventForward != null)
        {
            for(int index = 0; index < _actionListeners.length; ++index)
            {
                _actionListeners[index].actionPerformed(eventForward);
            }
        }
    }
    
    //
    // Private member variables:
    //
    private IMainFrame       _mainFrame;
    private Insets           _panelInsets;
    private String           _hiddenWord;
    private JLabel           _hiddenWordLabel;
    private JLabel           _notAWordLabel;
    private DatabaseManager  _databaseManager;
    private ActionListener[] _actionListeners;
    private KeyListener[]    _keyListeners;
    private Toolkit          _toolkit;
    private Image            _backgroundImage;
    private _WordTextField     _wordTextField;
    private int              _currentWordIndex;
    private int              _currentLetterIndex;
    private boolean          _gameIsRunning;
    
    private final GameMode _GAME_MODE   = GameMode.Classic;
    private final int      _WORD_SIZE   = 5;
    private final int      _GUESS_LIMIT = 6;
    
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    // This nested class is an extension of a JTextArea that defines the attributes and behavior of
    // the word text area in the game panel.
    //
    private class _WordTextField extends JTextField
    {
        //
        // Default constructor. Constructs the text field as a 2-D array of labels.
        //
        public _WordTextField()
        {
            GridLayout textAreaLayout       = new GridLayout(_GUESS_LIMIT, _WORD_SIZE, 5, 5);;
            Font       tileFont             = new Font("Dialog", Font.BOLD, 50);
            
            _letterTiles     = new JLabel[_GUESS_LIMIT][_WORD_SIZE];
            _tileColorWhite  = Color.WHITE;
            _tileColorRed    = new Color(246, 205, 205);
            _tileColorGray   = new Color(120, 124, 126);
            _tileColorYellow = new Color(201, 180, 88);
            _tileColorGreen  = new Color(106, 170, 100);
            _grayBorder      = BorderFactory.createLineBorder(Color.GRAY, 3);
            _silverBorder    = BorderFactory.createLineBorder(new Color(211, 214, 218), 3);
            _emptyBorder     = BorderFactory.createEmptyBorder();

            this.setBorder(_emptyBorder);
            this.setLayout(textAreaLayout);
            this.setBounds(280, 100, 434, 499);
            this.setEditable(false);
            this.setFocusable(true);
            this.getActionMap().get(DefaultEditorKit.deletePrevCharAction).setEnabled(false); // Caution: prevents all "Backspace" operations
            this.getActionMap().get(DefaultEditorKit.deleteNextCharAction).setEnabled(false); // Caution: prevents all "Delete" operations

            for (int i = 0; i < _letterTiles.length; ++i)
            {
                for (int j = 0; j < _letterTiles[0].length; ++j)
                {
                    _letterTiles[i][j] = new JLabel("", SwingConstants.CENTER);
                    _letterTiles[i][j].setBackground(_tileColorWhite);
                    _letterTiles[i][j].setOpaque(true);
                    _letterTiles[i][j].setFont(tileFont);
                    _letterTiles[i][j].setBorder(_silverBorder);
                    _letterTiles[i][j].setVisible(true);
                    this.add(_letterTiles[i][j]);
                }
            }
        }
        
        //
        // This method resets a specified letter tile to its default settings.
        //
        public void resetTile(int wordIndex, int letterIndex) throws Exception
        {
            _validateTileIndex(wordIndex, letterIndex);
            
            _letterTiles[wordIndex][letterIndex].setBackground(_tileColorWhite);
            _letterTiles[wordIndex][letterIndex].setText("");
            _letterTiles[wordIndex][letterIndex].setBorder(_silverBorder);
        }
        
        //
        // This methods sets the letter of a specified letter tile.
        //
        public void setTile(char character, int wordIndex, int letterIndex) throws Exception
        {
            _validateTileIndex(wordIndex, letterIndex);
            
            String letter = "" + Character.toUpperCase(character);
            
            _letterTiles[wordIndex][letterIndex].setForeground(Color.BLACK);
            _letterTiles[wordIndex][letterIndex].setText("" + letter);
            _letterTiles[wordIndex][letterIndex].setBorder(_grayBorder);
        }
        
        //
        // This method sets the color of a specified letter tile to red.
        //
        public void colorTileRed(int wordIndex, int letterIndex) throws Exception
        {
            _validateTileIndex(wordIndex, letterIndex);
            
            _letterTiles[wordIndex][letterIndex].setForeground(Color.WHITE);
            _letterTiles[wordIndex][letterIndex].setBackground(_tileColorRed);
            _letterTiles[wordIndex][letterIndex].setBorder(_silverBorder);
        }
        
        //
        // This method sets the color of a specified letter tile to green.
        //
        public void colorTileGreen(int wordIndex, int letterIndex) throws Exception
        {
            _validateTileIndex(wordIndex, letterIndex);
            
            _letterTiles[wordIndex][letterIndex].setForeground(Color.WHITE);
            _letterTiles[wordIndex][letterIndex].setBackground(_tileColorGreen);
            _letterTiles[wordIndex][letterIndex].setBorder(_emptyBorder);
        }
        
        //
        // This method sets the color of a specified letter tile to yellow.
        //
        public void colorTileYellow(int wordIndex, int letterIndex) throws Exception
        {
            _validateTileIndex(wordIndex, letterIndex);
            
            _letterTiles[wordIndex][letterIndex].setForeground(Color.WHITE);
            _letterTiles[wordIndex][letterIndex].setBackground(_tileColorYellow);
            _letterTiles[wordIndex][letterIndex].setBorder(_emptyBorder);
        }
        
        //
        // This method sets the color of a specified letter tile to gray.
        //
        public void colorTileGray(int wordIndex, int letterIndex) throws Exception
        {
            _validateTileIndex(wordIndex, letterIndex);
            
            _letterTiles[wordIndex][letterIndex].setForeground(Color.WHITE);
            _letterTiles[wordIndex][letterIndex].setBackground(_tileColorGray);
            _letterTiles[wordIndex][letterIndex].setBorder(_emptyBorder);
        }

        //
        // This method constructs and returns the word at a specified word row.
        //
        public String extractWord(int wordIndex) throws Exception
        {
            _validateTileIndex(wordIndex, 0);
            
            String        word      = "";
            StringBuilder builder   = new StringBuilder();
            String        letter = "";
            
            for (int letterIndex = 0; letterIndex < _letterTiles[0].length; ++letterIndex)
            {
                letter = _letterTiles[wordIndex][letterIndex].getText().toLowerCase();
                builder.append(letter);
            }
            
            word = builder.toString();
            
            return word;
        }
        
        //
        // This method validate that a given row and column index is within the range of the letter tile array.
        //
        private void _validateTileIndex(int wordIndex, int letterIndex) throws Exception
        {
            if (wordIndex >= _letterTiles.length)
            {
                throw new IndexOutOfBoundsException("ClassicGamePanel._wordTextField.setLetter() >> Word index of " + wordIndex + " out of range");
            }
            else if (wordIndex < 0)
            {
                throw new IndexOutOfBoundsException("ClassicGamePanel._wordTextField.setLetter() >> Word index of " + wordIndex + " too small");
            }
            else if (letterIndex >= _letterTiles[0].length)
            {
                throw new IndexOutOfBoundsException("ClassicGamePanel._wordTextField.setLetter() >> Letter index of " + letterIndex + " too large");
            }
            else if (letterIndex < 0)
            {
                throw new IndexOutOfBoundsException("ClassicGamePanel._wordTextField.setLetter() >> Letter index of " + letterIndex + " too small");
            }
        }
        
        //
        // Private member variables:
        //
        private JLabel[][] _letterTiles;        
        private Color      _tileColorWhite;
        private Color      _tileColorRed;
        private Color      _tileColorGray;
        private Color      _tileColorYellow;
        private Color      _tileColorGreen;
        private Border     _grayBorder;
        private Border     _silverBorder;
        private Border     _emptyBorder;
    }
}
