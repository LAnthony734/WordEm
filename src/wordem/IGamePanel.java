//
// IGamePanel.java
//
// This interface specifies the minimum behavior of a game panel.
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

import java.awt.Component;
import java.awt.Insets;
import java.awt.event.*;

public interface IGamePanel
{
    public GameMode getGameMode();
    
    public void start() throws Exception; 
    
    public void pause();
    
    public void resume() throws Exception;
    
    public void quit();
    
    public Component getComponent();
    
    public void addActionListener(ActionListener actionListener);
    
    public void addKeyListener(KeyListener keyListener);
    
    public void actionPerformed(ActionEvent event);
    
    public void keyPressed(KeyEvent event);
    
    public void keyReleased(KeyEvent event);
    
    public void keyTyped(KeyEvent event);
}
