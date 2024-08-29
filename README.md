# NotVim
NotVim is a fast way to edit text files. It works entirely on your terminal, making it very convenient for terminal-based workflows. The project was developed in Java and inspired by Vim.

# Why it was made
The objective of this project was to create an entire text editor from the ground. This includes every aspect of it, from the way the cursor wraps around the text to the logic of how files are loaded and written.

All the interfaces, such as the home screen or the error alerts, were made without using any framework. The program only uses **JNA library** to enter **raw mode** on the terminal, by using the native shared libraries required.
<!-- Link to JNA -->

# How does it work

# How to use it
NotVim has four different modes, each one is used to perform different actions in the editor.

* ***Normal mode:*** Accessed by pressing the `ESC` key at any time. Use it to move the cursor through the text and access other modes.

* ***Insert mode:*** Used to insert text into a new or existing file. You can access it by pressing the letter `i` while in *normal mode*.

* ***Command mode:*** Used to type commands, it is accessed by pressing `:` while in *normal mode*. Here is the **list of commands**. <!-- Link to commands -->

* ***Visual mode:*** Brings the capability of highlighting text to copy and paste it. This mode hasn't been implemented yet, but it will be in a future release. It will be accessed by pressing the letter `v` while in *normal mode*.

> Tip: You can open a file directly by passing it as the first argument when running the program.
> 
> Example: `$ notvim path/to/file`

# Features
The program counts with all the basic features you would expect from a text editor. Including:

* The cursor can only move through the text and react to user inputs, such as: inserting new characters, creating new lines, and deleting lines.
* The cursor has column memory. This means that if the user moves to a line that doesn't have enough text to maintain its previous column position, the cursor will move to the end of that line. If the cursor moves again to a new line and this time it has enough characters, it will restore its original column position.
* If a line with text is deleted, the text from the line will be concatenated to the end of the line above it.
* If a new line is created between text, the text will be divided in two. Bringing the segment from the cursor to the end of the current line to the new line created.
* The program can: open, edit, write, and create text files.

### List of all available commands

* `:q` or `:quit`
  
  *Quits the program.*

* `:open` (requieres arguments)
 
  *Open files and print their content on the screen. Pass as an argument the name of the file you want to open.*

* `:write` or `:w` (accepts arguments)

  *Writes all data inserted into the file passed as arguments. It remembers what file has been written, so it isn't necessary to explicitly tell it again where to write it. Just call the command without arguments and it will be 
  written. This also works for files that have been opened.*

* `:n` or `:new`
  
  *Creates a new empty screen. Same as launching the program without arguments.*

# Installation
Make sure you have installed at least Java 21 on your system and your terminal recognizes the command `java`. You can check your Java version by running this on your terminal.
<!-- Link to Java 21 -->

```
$ java --version
```
> In case you have Java installed but your terminal doesn't recongnize the command you will need to set up your JAVA_HOME.


## Step by step guide of how I would install and set up the program

1. Create a script at `~/.local/bin/notVim`, or at any directory you want, that is accessed by your system's `$PATH`. You can name it any way you want but keep in mind that the name of the script will be the name of the command you will use to run NotVim.
   
```bash
#!/bin/bash
java -jar ./not-vim-1.0.jar "$@"
```
> Change the name of the file to match the version of NotVim you are using.

2. Download the program package from Github releases and save it at the same directory of your script. <!-- Link to release -->

<br>
<br>

> Keep in mind that NotVim was developed for unix systems. It doesn't work on Windows machines yet, but I'm working to make it possible.
