Search Engine Project Version 1
-------------------------------

This is a somewhat fancy version of SearchEngine that 
does what's needed for the first iteration, but also plans
a bit for the future.

Project Files
-------------

.project

> This is a normally hidden file, but essential if you clone or
> download the project and would like to import into Eclipse. 
> Just File -> Import -> General -> Existing Java Project 

> Notice please that all java files are in the package 
> org.uiowa.cs2820.engine

Field.java

> This is the Field class, which users will use to index
> and search for content. Each Field object has two components,
> a field name (like "Color", "Part", "Title", etc) and an
> associated value.  Since a value could be a string, a number,
> or something more complex like an array or object, we need
> a universal way for the value to be anything. Hence, the value
> just has type Object (which could be String, Integer, even a
> Map).  

> Later (beyond Version 1) we will need to save things into 
> a file, including the values can can be objects. To enable    
> this future design, Field has two class methods "convert" 
> and "revert", which use standard Java library methods to convert 
> anything into a byte array. The "toBytes" method returns 
> convert(this), that is, the Field object itself as an array 
> of bytes.

> The Field constructor saves name and value; Two getter 
> methods return the field name and field value for a Field 
> object.            

Node.java

> Nodes are the vertices of trees (or lists, arrays, etc) that 
> hold the data for lookup. The Database creates nodes, copies 
> data into nodes, stores the nodes for later operations.
> FieldSearch also needs to know about nodes for lookup
> operations.  Each node has two parts, a key and an associated
> list of identifiers.  The idea is that a lookup finds content
> by key; the list of identifiers is then enough to satisfy the 
> user's query.  

> The constructor expects a key and an identifier: new nodes 
> have only one identifier (a string), and the key is a byte
> array. The "add" method adds a new identifier to a node's 
> list of identifiers. The "del" method removes an identifier
> from the list. 

Indexer.java

> The Indexer is simple: it stores data from a Field object 
> into the Database, calling the database "store" method. The 
> idea of an Indexer is that is may get called many times on
> behalf of one file, or even one line in a file.  It's the 
> file name, or the line number, that needs to be remembered
> later so that a query tells the user the location of the 
> content. Therefore, the identifier (file name, line number) 
> is a string that is saved by Indexer's constructor.

FieldSeach.java

> The FieldSearch class is very simple; in fact, it is far 
> too simple when thinking about future enhancements to the 
> project.  Currently there is only one kind of search, find
> all the locations where a specified Field object is found.

Database.java

> Unlike the other files, this one's not a class. Database is 
> an interface, which just has the methods for working with 
> the persistent storage.  What's perhaps interesting to learn
> is that an interface type can be used to type a variable, 
> and that is what Indexer and FieldSearch do: they have a variable
> of type Database that is set in the constructor. Conceptually, 
> there is only one Database, so it could be a class (static) 
> implementation rather than instance-based; we're using an 
> instance-based implementation because it offers a bit more 
> flexibility for unit testing. 

LinearMemoryDatabase.java

> This class implements the Database interface in memory, 
> with a simple ArrayList of Nodes. Search and update is 
> by brute-force iteration to find a Node with the desired
> key.  

FieldTest.java

> The integration test for all classes above. 

Limitation
----------

A major limitation of this design is that the entire Field 
object itself is a key for lookup into the Database. It's done
by converting Field into a byte array (the "toBytes" method 
does this).  In future, we will have to think how to do things
differently, so that users can query by Field name, or by 
Field value, or by a range: show me anything with a field name
of "quantity" and an associated value in the range 100-200.
