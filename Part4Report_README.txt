Sydney DeCyllis, Iselda Aiello

The user can only send data to the server one input at a time. The server, however, can send many inputs to the user bcause there may be many clients connected, so creating a new class that can multi-thread this connection will make communication with the server more efficient. This class is called a "listener" because it "lsitens" for data sent by the server to the client.

Since clients can send data at random, multi-threading a client class allows the server to send and receive data to all the different clients at once, rather than waiting to send and receive data for individual clients. The listener class only takes in input from the server, while the ServerSideClientIO class listens to all the current clients, and sends back the data received to every client connected.

broadcast() and remove() are synchronized so you don't accidentally send a message to someone who had just left the server. If they weren't, then you can find a client in the list, and then remove them before sending the data, resulting in the data being send to a user that no longer exists.

First, the implementation of typing "LISTUSERS" was added. An ArrayList was made in ClackServer to track the list of current users. When a user joins, it send an "init" message to the server, which triggers it to add a new user to the ArrayList. Then, whenever "LISTUSERS" is inputed, the method broadcastTo() is called, which sends the user list back to whoever requested the data. This is done with the helper function getData() in ServerSideClientIO, which returns the current ClackData object associated with each client, until it finds the one who requested the "LISTUSERS" command. Then, it prints the user list to the screen. If a client were to type "DONE", then they are removed from the ArrayList and no longer shown when typing "LISTUSERS".

== ClackServer Command Line ==
A connection was established!
A connection was established!
Connection Terminated
Connection Terminated

== ClackClient Command Line == (Iselda)
hello!
User: Iselda
Date: Mon Dec 05 23:16:50 EST 2022
Data: hello!
User: Sydney
Date: Mon Dec 05 23:16:55 EST 2022
Data: hi!!!
how are you today??
User: Iselda
Date: Mon Dec 05 23:17:01 EST 2022
Data: how are you today??
User: Sydney
Date: Mon Dec 05 23:17:12 EST 2022
Data: great!!! how are you????
fantastic!!! hey, check out this cool file!
User: Iselda
Date: Mon Dec 05 23:17:24 EST 2022
Data: fantastic!!! hey, check out this cool file!
SENDFILE test1.txt
User: Iselda
Date: Mon Dec 05 23:17:31 EST 2022
Data: This is a new file
This is a new line
This is the last line
User: Sydney
Date: Mon Dec 05 23:17:39 EST 2022
Data: woah thats so cool!!!!
ikr!!! okay i have to go now, goodbye!!!!
User: Iselda
Date: Mon Dec 05 23:17:53 EST 2022
Data: ikr!!! okay i have to go now, goodbye!!!!
User: Sydney
Date: Mon Dec 05 23:17:58 EST 2022
Data: bye!!!
DONE

== ClackClient Command Line == (Sydney)
User: Iselda
Date: Mon Dec 05 23:16:50 EST 2022
Data: hello!
hi!!!
User: Sydney
Date: Mon Dec 05 23:16:55 EST 2022
Data: hi!!!
User: Iselda
Date: Mon Dec 05 23:17:01 EST 2022
Data: how are you today??
great!!! how are you????
User: Sydney
Date: Mon Dec 05 23:17:12 EST 2022
Data: great!!! how are you????
User: Iselda
Date: Mon Dec 05 23:17:24 EST 2022
Data: fantastic!!! hey, check out this cool file!
User: Iselda
Date: Mon Dec 05 23:17:31 EST 2022
Data: This is a new file
This is a new line
This is the last line
woah thats so cool!!!!
User: Sydney
Date: Mon Dec 05 23:17:39 EST 2022
Data: woah thats so cool!!!!
User: Iselda
Date: Mon Dec 05 23:17:53 EST 2022
Data: ikr!!! okay i have to go now, goodbye!!!!
bye!!!
User: Sydney
Date: Mon Dec 05 23:17:58 EST 2022
Data: bye!!!
LISTUSERS
User List:
Sydney
DONE
