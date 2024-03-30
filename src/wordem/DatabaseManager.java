//
// DatabaseManager.java
//
// This class contains methods for modifying and retrieving data from the game database.
// The game's database should already be constructed and valid before construction of this manager.
// Any exceptions during construction are the result of an invalid database and thus not the responsibility of this manager. 
// These exceptions are thrown to the caller for handling.
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

import java.sql.*;

public class DatabaseManager
{
    //
    // Default constructor.
    //
    public DatabaseManager()
    {
        _conn = null;
    }
    
    //
    // This method gets a random word of a specified language and length from the database.
    //
    public String getRandomWord(Language language, int length) throws SQLException
    {
        String    table = null;
        String    word  = null;
        Statement stmt  = null;
        ResultSet rs    = null;
        
        table = _getWordTableName(language);
        stmt  = _conn.createStatement();  
        rs    = stmt.executeQuery("SELECT \"Word\" FROM \"" + table + "\" WHERE LENGTH(\"Word\") = " + length + " ORDER BY RANDOM() LIMIT 1");
        word  = rs.getString("Word");

        return word;
    }
    
    //
    // This method determines if a given word of a specified language is recognized by the database.
    //
    public boolean isWord(String word, Language language) throws SQLException
    {
        boolean   isWord = false;  
        String    table  = null;
        Statement stmt   = null;
        ResultSet rs     = null;
        int       count  = 0;

        table = _getWordTableName(language);  
        stmt  = _conn.createStatement();
        rs    = stmt.executeQuery("SELECT COUNT() FROM \"" + table + "\" WHERE \"Word\" = \"" + word + "\"");
        count = rs.getInt("COUNT()");
        
        if (count != 0)
        {
            isWord = true;
        }
        
        return isWord;
    }
    
    //
    // This method increments the number of games played of a given game mode.
    //
    public void incrementGamesPlayedStatistic(GameMode gameMode) throws SQLException
    {
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            stmt.execute("UPDATE \"" + table + "\" SET \"Value\" = \"Value\" + 1 WHERE \"Statistic\" = \"Games Played\"");
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.incrementGamesPlayedStatistic >> \"Games Played\" statistic not found in table: " + table);
        }
    }
    
    //
    // This method get the number of games played of a given game mode.
    //
    public int getGamesPlayedStatistic(GameMode gameMode) throws SQLException
    {
        int    value = 0;
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            ResultSet rs   = null;
            
            rs    = stmt.executeQuery("SELECT \"Value\" FROM \"" + table + "\" WHERE \"Statistic\" = \"Games Played\"");
            value = rs.getInt(1);
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.getGamesPlayedStatistic >> \"Games Played\" statistic not found in table: " + table);
        }
        
        return value;
    }
       
    //
    // This method increments the number of games quit of a given game mode.
    //
    public void incrementGamesQuitStatistic(GameMode gameMode) throws SQLException
    {
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            stmt.execute("UPDATE \"" + table + "\" SET \"Value\" = \"Value\" + 1 WHERE \"Statistic\" = \"Games Quit\"");
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.incrementGamesPlayedStatistic >> \"Games Quit\" statistic not found in table: " + table);
        }
    }
    
    //
    // This method get the number of games quit of a given game mode.
    //
    public int getGamesQuitStatistic(GameMode gameMode) throws SQLException
    {
        int    value = 0;
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            ResultSet rs   = null;
            
            rs    = stmt.executeQuery("SELECT \"Value\" FROM \"" + table + "\" WHERE \"Statistic\" = \"Games Quit\"");
            value = rs.getInt(1);
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.getGamesPlayedStatistic >> \"Games Quit\" statistic not found in table: " + table);
        }
        
        return value;
    }
    
    //
    // This method increments the number of games won of a given game mode.
    //
    public void incrementGamesWonStatistic(GameMode gameMode) throws SQLException
    {
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            stmt.execute("UPDATE \"" + table + "\" SET \"Value\" = \"Value\" + 1 WHERE \"Statistic\" = \"Games Won\"");
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.incrementGamesWonStatistic >> \"Games Won\" statistic not found in table: " + table);
        }
    }
    
    //
    // This method get the number of games won of a given game mode.
    //
    public int getGamesWonStatistic(GameMode gameMode) throws SQLException
    {
        int    value = 0;
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            ResultSet rs   = null;
            
            rs    = stmt.executeQuery("SELECT \"Value\" FROM \"" + table + "\" WHERE \"Statistic\" = \"Games Won\"");
            value = rs.getInt(1);
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.getGamesWonStatistic >> \"Games Won\" statistic not found in table: " + table);
        }
        
        return value;
    }
    
    //
    // This method increments the number of games lost of a given game mode.
    //
    public void incrementGamesLostStatistic(GameMode gameMode) throws SQLException
    {
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            stmt.execute("UPDATE \"" + table + "\" SET \"Value\" = \"Value\" + 1 WHERE \"Statistic\" = \"Games Lost\"");
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.incrementGamesLostStatistic >> \"Games Lost\" statistic not found in table: " + table);
        }
    }
    
    //
    // This method get the number of games lost of a given game mode.
    //
    public int getGamesLostStatistic(GameMode gameMode) throws SQLException
    {
        int    value = 0;
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            ResultSet rs   = null;
            
            rs    = stmt.executeQuery("SELECT \"Value\" FROM \"" + table + "\" WHERE \"Statistic\" = \"Games Lost\"");
            value = rs.getInt(1);
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.getGamesLostStatistic >> \"Games Lost\" statistic not found in table: " + table);
        }
        
        return value;
    }
    
    //
    // This methods recalculates and updates the win percentage statistic of a given game mode.
    //
    public void updateWinPercentageStatistic(GameMode gameMode) throws SQLException
    {
        String    table         = _getStatisticTableName(gameMode);
        Statement stmt          = _conn.createStatement();
        ResultSet rs            = null;
        int       gamesPlayed   = 0;
        int       wins          = 0;
        int       winPercentage = 0;
        
        try
        {
            rs = stmt.executeQuery("SELECT \"Value\" FROM \"" + table + "\" WHERE \"Statistic\" = \"Games Played\"");
            gamesPlayed = rs.getInt(1);
            
            rs = stmt.executeQuery("SELECT \"Value\" FROM \"" + table + "\" WHERE \"Statistic\" = \"Games Won\"");
            wins = rs.getInt(1);
            
            if (gamesPlayed == 0)
            {
                winPercentage = 0;
            }
            else
            {
                winPercentage = (int)(((double)wins/(double)gamesPlayed) * 100);
            }
            
            stmt.execute("UPDATE \"" + table + "\" SET \"Value\" = " + winPercentage + " WHERE \"Statistic\" = \"Win Percentage\"");
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.updateWinPercentageStatistic >> Could not find one or more necessary statistics for game mode: " + gameMode);
        }
    }
    
    //
    // This method get the win percentage of a given game mode.
    //
    public int getWinPercentageStatistic(GameMode gameMode) throws SQLException
    {
        int    value = 0;
        String table = _getStatisticTableName(gameMode);
        
        try
        {
            Statement stmt = _conn.createStatement();
            ResultSet rs   = null;
            
            rs    = stmt.executeQuery("SELECT \"Value\" FROM \"" + table + "\" WHERE \"Statistic\" = \"Win Percentage\"");
            value = rs.getInt(1);
        }
        catch (SQLException ex)
        {
            throw new SQLException("DatabaseManager.getWinPercentageStatistic >> \"Win Percentage\" statistic not found in table: " + table);
        }
        
        return value;
    }
    
    //
    // This method establishes a permanent connection with the database.
    //
    public void connect() throws SQLException
    {
        _conn = DriverManager.getConnection("jdbc:sqlite:Data.sqlite"); 
    }
    
    //
    // This method severs the permanent connection with the database.
    //
    public void disconnect() throws SQLException
    {
        _conn.close();
        _conn = null;
    }
    
    //
    // This method gets the string name of the table of a given language.
    //
    private String _getWordTableName(Language language) throws SQLException
    {
        String name = null;
        
        switch (language)
        {
            case English:
            {
                name = "Words_en";
                break;
            }
            
            case Spanish:
            {
                name = "Words_es";
                break;
            }
            
            default:
            {
                throw new SQLException("DatabaseManager.getRandomWord >> Could not locate word table for language: " + language);
            }
        }
        
        return name;
    }
    
    //
    // This methods gets the string name of the table of a given game mode.
    //
    private String _getStatisticTableName(GameMode gameMode) throws SQLException
    {
        String name = null;
        
        if (gameMode == null)
        {
            name = "Statistics_Global";
        }
        else
        {
            switch (gameMode)
            {
                case Nursery:
                {
                    name = "Statistics_Nursery";
                    break;
                }
                
                case Elementary:
                {
                    name = "Statistics_Elementary";
                    break;
                }
                
                case Classic:
                {
                    name = "Statistics_Classic";
                    break;
                }
                
                case Advanced:
                {
                    name = "Statistics_Advanced";
                    break;
                }
                
                case Ludicrous:
                {
                    name = "Statistics_Ludicrous";
                    break;
                }
                
                default:
                {
                    throw new SQLException("DatabaseManager.getRandomWord >> Could not locate statistic table for game mode: " + gameMode);
                }
            }
        }
        
        return name;
    }
    
    //
    // Private members variables:
    //
    private Connection _conn;
}