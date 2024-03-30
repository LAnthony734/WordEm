//
// GamePanelFactory.java
//
// This abstract class is a factory that instantiates an IGamePanel implementation
// and returns the instantiation as an IGamePanel.
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

import java.sql.SQLException;

public abstract class GamePanelFactory
{
    public static IGamePanel createGamePanel(IMainFrame mainFrame) throws Exception
    {
        IGamePanel gamePanel = null;
        GameMode   gameMode  = mainFrame.getGameMode();
        
        switch (gameMode)
        {
            case Nursery:
            {
                gamePanel = new NurseryGamePanel(mainFrame);
                break;
            }
            
            case Elementary:
            {
                gamePanel = new ElementaryGamePanel(mainFrame);
                break;
            }
            
            case Classic:
            {
                gamePanel = new ClassicGamePanel(mainFrame);
                break;
            }
            
            case Advanced:
            {
                gamePanel = new AdvancedGamePanel(mainFrame);
                break;
            }
            
            case Ludicrous:
            {
                gamePanel = new LudicrousGamePanel(mainFrame);
                break;
            }
            
            default:
            {
                throw new SQLException("GamePanelFactory.createGamePanel >> Unrecognized game mode: " + gameMode);
            }
        }
        
        return gamePanel;
    }
}
