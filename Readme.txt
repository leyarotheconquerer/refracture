stuiff

He means "stuf"

Hazen ads moar stuuffs

So, on a more serious note...

I'm trying to arrange the directory structure. Here's my idea. Feel free to build onto it. Document if you can.

Also, Visio seems to store things in binary format... so it might be kind of hard for Git to merge two files. My suggestion is that, before you start working on it, pull the current copy of the Visio diagram off the server. Then, commit often to make sure the copy on the server is up to date. That way, if someone else commits, you'll be aware of it and work from their copy rather than having to try to merge a binary file by hand.

Thanks, Hazen.

Root
-->docs  (documentation, etc.)
-->src   (source code, etc.)
-->assets (graphics, music, etc.)