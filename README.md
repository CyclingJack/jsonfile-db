# jsonfile-db
Tiny embedded Database in Java based on Json-Files - Fork with enhanced features of the Jsondb-core

Jsonfile-db is a file-based database for embedded use in Java programs. The focus is on speed with 
small amounts of data (about a few thousand records). It uses text-based files to store the data, 
which are only locked during writing. The database should therefore be able to be used simultaneously 
by several users, depending on the size and structure.

The basis is the database jsondb-core by Farooq Khan.
