Sydney DeCyllis, Iselda Aiello

No error is thrown when the port number is negative, and the only error that arises when setting the user to 'null' is when comparing using the equals() method (i.e. nullPointerException).
To fix this, under the constructors for each object, throw an exception whenever the port number is negative, or when the user is set to 'null'.
