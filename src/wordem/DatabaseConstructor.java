//
// DatabaseConstructor.java
//
// This class constructs the SQL database for the WordEm game.
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

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

public class DatabaseConstructor
{
    //
    // Program entry point.
    //
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Starting Construction");
            
            File file = new File("Data.sqlite");
            file.delete();
            
            _conn = DriverManager.getConnection("jdbc:sqlite:Data.sqlite");    

            _readWordsToTable("Words_en.txt", "Words_en");
            _readWordsToTable("Words_es.txt", "Words_es");
            _constructGlobalStatisticsTable();
            _constructNurseryStatisticsTable();
            _constructElementaryStatisticsTable();
            _constructClassicStatisticsTable();
            _constructAdvancedStatisticsTable();
            _constructLudicrousStatisticsTable();

            _conn.close();
            
            System.out.println("Finished Constructing");
        }
        catch (IOException ex)
        {
            System.out.println(ex);
        }
        catch (SQLException ex)
        {
            System.out.println(ex);
        }
    }
    
    //
    // This method reads the words from a given text file and creates a respective table in the database for those words.
    //
    private static void _readWordsToTable(String fileName, String tableName) throws IOException, SQLException
    {
        Statement stmt = _conn.createStatement();
        
        stmt.execute("CREATE TABLE \"" + tableName + "\" (\"Word\" TEXT)");

        PreparedStatement pstmt             = null;
        FileInputStream   fileInputStream   = new FileInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
        BufferedReader    bufferedReader    = new BufferedReader(inputStreamReader);
        String            line              = null; 
        
        pstmt = _conn.prepareStatement("INSERT INTO \"" + tableName + "\" (Word) VALUES(?);");
        
        stmt.execute("BEGIN TRANSACTION;");
        do
        {
            line = bufferedReader.readLine();
            
            if (line != null)
            {
                pstmt.setString(1, line);
                pstmt.execute();
            }
        }
        while (line != null);
        stmt.execute("COMMIT TRANSACTION;");
        
        bufferedReader.close();
    }
    
    //
    // This methods constructs the global statistics table.
    //
    private static void _constructGlobalStatisticsTable() throws SQLException
    {
        Statement stmt = _conn.createStatement();

        stmt.execute("CREATE TABLE \"Statistics_Global\" (\"Statistic\" TEXT, \"Value\" INTEGER)");

        stmt.execute("BEGIN TRANSACTION;");
        stmt.execute("INSERT INTO \"Statistics_Global\" (\"Statistic\", \"Value\") VALUES (\"Games Played\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Global\" (\"Statistic\", \"Value\") VALUES (\"Games Quit\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Global\" (\"Statistic\", \"Value\") VALUES (\"Games Won\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Global\" (\"Statistic\", \"Value\") VALUES (\"Games Lost\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Global\" (\"Statistic\", \"Value\") VALUES (\"Win Percentage\", 0)");   
        stmt.execute("COMMIT TRANSACTION;");
    }
    
    //
    // This methods constructs the statistics table for the nursery game mode.
    //
    private static void _constructNurseryStatisticsTable() throws SQLException
    {
        Statement stmt = _conn.createStatement();

        stmt.execute("CREATE TABLE \"Statistics_Nursery\" (\"Statistic\" TEXT, \"Value\" INTEGER)");
        
        stmt.execute("BEGIN TRANSACTION;");       
        stmt.execute("INSERT INTO \"Statistics_Nursery\" (\"Statistic\", \"Value\") VALUES (\"Games Played\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Nursery\" (\"Statistic\", \"Value\") VALUES (\"Games Quit\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Nursery\" (\"Statistic\", \"Value\") VALUES (\"Games Won\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Nursery\" (\"Statistic\", \"Value\") VALUES (\"Games Lost\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Nursery\" (\"Statistic\", \"Value\") VALUES (\"Win Percentage\", 0)");   
        stmt.execute("COMMIT TRANSACTION;");
    }
    
    //
    // This methods constructs the statistics table for the elementary game mode.
    //
    private static void _constructElementaryStatisticsTable() throws SQLException
    {
        Statement stmt = _conn.createStatement();
        
        stmt.execute("CREATE TABLE \"Statistics_Elementary\" (\"Statistic\" TEXT, \"Value\" INTEGER)");
    
        stmt.execute("BEGIN TRANSACTION;");
        stmt.execute("INSERT INTO \"Statistics_Elementary\" (\"Statistic\", \"Value\") VALUES (\"Games Played\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Elementary\" (\"Statistic\", \"Value\") VALUES (\"Games Quit\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Elementary\" (\"Statistic\", \"Value\") VALUES (\"Games Won\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Elementary\" (\"Statistic\", \"Value\") VALUES (\"Games Lost\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Elementary\" (\"Statistic\", \"Value\") VALUES (\"Win Percentage\", 0)");   
        stmt.execute("COMMIT TRANSACTION;");
    }
    
    //
    // This methods constructs the statistics table for the classic game mode.
    //
    private static void _constructClassicStatisticsTable() throws SQLException
    {
        Statement stmt = _conn.createStatement();

        stmt.execute("CREATE TABLE \"Statistics_Classic\" (\"Statistic\" TEXT, \"Value\" INTEGER)");
        
        stmt.execute("BEGIN TRANSACTION;");
        stmt.execute("INSERT INTO \"Statistics_Classic\" (\"Statistic\", \"Value\") VALUES (\"Games Played\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Classic\" (\"Statistic\", \"Value\") VALUES (\"Games Quit\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Classic\" (\"Statistic\", \"Value\") VALUES (\"Games Won\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Classic\" (\"Statistic\", \"Value\") VALUES (\"Games Lost\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Classic\" (\"Statistic\", \"Value\") VALUES (\"Win Percentage\", 0)");   
        stmt.execute("COMMIT TRANSACTION;");
    }
    
    //
    // This methods constructs the statistics table for the advanced game mode.
    //
    private static void _constructAdvancedStatisticsTable() throws SQLException
    {
        Statement stmt = _conn.createStatement();

        stmt.execute("CREATE TABLE \"Statistics_Advanced\" (\"Statistic\" TEXT, \"Value\" INTEGER)");
        
        stmt.execute("BEGIN TRANSACTION;");
        stmt.execute("INSERT INTO \"Statistics_Advanced\" (\"Statistic\", \"Value\") VALUES (\"Games Played\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Advanced\" (\"Statistic\", \"Value\") VALUES (\"Games Quit\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Advanced\" (\"Statistic\", \"Value\") VALUES (\"Games Won\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Advanced\" (\"Statistic\", \"Value\") VALUES (\"Games Lost\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Advanced\" (\"Statistic\", \"Value\") VALUES (\"Win Percentage\", 0)"); 
        stmt.execute("COMMIT TRANSACTION;");
    }
    
    //
    // This methods constructs the statistics table for the ludicrous game mode.
    //
    private static void _constructLudicrousStatisticsTable() throws SQLException
    {
        Statement stmt = _conn.createStatement();

        stmt.execute("CREATE TABLE \"Statistics_Ludicrous\" (\"Statistic\" TEXT, \"Value\" INTEGER)");
        
        stmt.execute("BEGIN TRANSACTION;");        
        stmt.execute("INSERT INTO \"Statistics_Ludicrous\" (\"Statistic\", \"Value\") VALUES (\"Games Played\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Ludicrous\" (\"Statistic\", \"Value\") VALUES (\"Games Quit\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Ludicrous\" (\"Statistic\", \"Value\") VALUES (\"Games Won\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Ludicrous\" (\"Statistic\", \"Value\") VALUES (\"Games Lost\", 0)");
        stmt.execute("INSERT INTO \"Statistics_Ludicrous\" (\"Statistic\", \"Value\") VALUES (\"Win Percentage\", 0)");
        stmt.execute("COMMIT TRANSACTION;");
    }

    //
    // Private member variables:
    //
    private static Connection _conn;    
}