# What is NotVim
NotVim is a fast way to edit text files. It works entirely on your terminal so it is very convinient for terminal-based workflows. The project was developed in Java and inspired by Vim.

# Why it was made
The project was made with the idea of creating an entire text editor from the ground. This includes every aspect of it, from the way the cursor wraps arround the text to the logic of how the files are loaded and written.

All the interfaces, such as the home screen or the error alerts, were made without using any framework. The program only uses **JNA library** to enter **raw mode** on the terminal, by using the native shared libraries required.

# How does it work

# How to use it
NotVim has four different modes, each one is used to perform different actions in the editor.

* ***Normal mode:*** Accessed by pressing the `ESC` key at any time. Use it to move the cursor trough the text and access other modes.

* ***Insert mode:*** Used to insert text into a new or existing file. You can access it by pressing the letter `i` while in *normal mode*.

* ***Command mode:*** Used to type new commands, it is accessed by pressing `:` while in *normal mode*. List of commands specified here. <!-- Link to commands -->

* ***Visual mode:*** Brings the capability of highlighting text to copy and paste it. This mode hasn't been implemented yet, but it will be in a future release. It will be accessed by pressing the letter `v` while in *normal mode*.

# Features
The program counts with all the basics features you will expect from a text editor. Including:

* The cursor can only move trough the text and reacts to user inputs, such as: inserting new characters, creating new lines and deleting lines.
* The cursor has column memory. This means that if the user moves to a line that doesn't have enough text to maintain 
* If a line with text is deleted, the text from the line will be concatenated to the text of the line above it.
* If a new line is created between text, the text will be divided in two. Bringing the segment from the cursor to the end of the current line to the new line created.


# Installation

Because the application is still under development, the only way to install it is by cloning this repo and building the executable with maven.

1. Create a script at `~/.local/bin/notVim`, or at any directory you want that is accessed by your system's `$PATH`. You can name it the way you want, keep in mind that the name of the script will be the name of the command you will use to run NotVim.
   
```bash
#!/bin/bash
java -jar ./not-vim-1.0.jar "$@"
```

1. Download the program package from Github realeses and store it at the same directory of your script.

<br>
<br>

> Keep in mind that NotVim was developed for unix systems. It doesn't work on Windows machines yet, but I'm working to make it possible.
