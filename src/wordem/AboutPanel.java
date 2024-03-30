//
// AboutPanel.java
//
// This class is an extension of a JPanel that defines the attributes and behavior of the about panel in the main frame.
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

public class AboutPanel extends JPanel
{
    //
    // Default constructor. Constructs with default values.
    //
    public AboutPanel()
    {
        _initialize();
        _initializeAboutText();
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
    // This method initializes the panel's .
    //
    private void _initialize()
    {
        _toolkit         = Toolkit.getDefaultToolkit();
        _backgroundImage = _toolkit.getImage("art/About Panel Background.png");
        
        this.setFocusable(false);
        this.setLayout(null);
        this.setVisible(false);
    }
    
    //
    // This method initializes the text area on the panel with 'about' information.
    //
    private void _initializeAboutText()
    {
        Font font = new Font("SansSerif", Font.BOLD, 20);
        
        _aboutText = new JTextArea();
        _aboutText.setBackground(new Color(23,140,87));
        _aboutText.setForeground(Color.WHITE);
        _aboutText.setEditable(false);
        _aboutText.setLineWrap(true);
        _aboutText.setWrapStyleWord(true);
        _aboutText.setFont(font);
        _aboutText.setBounds(165, 87, 575, 464);
        
        _aboutText.append("Welcome to WordEm\u00A9 version 1.0.4!\n");
        _aboutText.append("Credited authors: Luke, Konrad, Ayoub and Congminh.\n");
        _aboutText.append("\n");
        _aboutText.append("WordEm\u00A9 is a word guessing game based on the popular web-based New York Times game Wordle\u00A9. "
                        + "It implements a Mastermind-like approach to word guessing, as the gamer uses their acquired "
                        + "clues to narrow in on the hidden word! We wrote this game as a school project to improve our "
                        + "skills in team organization and communication, software architecture and design, and JAVA Swing, "
                        + "as well as to simply pay homage to Wordle\u00A9. As such, this game is not intended for external use.\n");
        _aboutText.append("\n");
        _aboutText.append("https://en.wikipedia.org/wiki/Wordle\n");
        _aboutText.append("https://www.nytimes.com/games/wordle/index.html");
        
        this.add(_aboutText);
    }
    
    //
    // Private member variables:
    //
    private Toolkit     _toolkit;
    private Image       _backgroundImage;
    private JTextArea   _aboutText;
}
