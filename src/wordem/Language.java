//
// Language.java
//
// This enum defines different types of written languages.
//
// The home panel class dynamically creates buttons for all languages included in this enum.
// As such, a PNG file must be provided for the construction of each language button.
// If the file is not provided, the home panel will fail to load and an exception will be thrown.
//
// Adding a new language further requires adding a word table in the database reflective of the language,
// as well as modifying the DatabaseManager file to support the fetching of a word from that table.
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

public enum Language
{
    English,
    Spanish
}
