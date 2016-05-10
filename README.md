# File-Transfer

File Transfer is a Java Application designed using the Swing Graphics Library.

It has been designed to facilitate file transfer between two PCs connected to the internet. 
Java Network Libraray (Sockets) has been used for this purpose

Created with Eclipse Luna 


#FIRST COMMIT 12:13 pm  10th May 2016#

Defects :
---------
1. Single File gets transferred. Error on multiple file transferr. 
	Possible Correction: (i)  Multithreaded Socket or 
						 (ii) Multiplexing IO Streams over same Socket Connection

2. Settings have not been dealt with. Need to store them for future reference.

#Second COMMIT 1:51 pm 10th May 2016#
 
Modification made : 1. Connect Button Action Modified. Connection Label changes
  						only when connection is established.
  					 2. Comments.

Defects: SAME AS FIRST COMMIT

#THIRD COMIT 9:05 pm 10th May 2016#

Modification made: 1. Multiple File transfer
				   2. Download Directory Option
				   3. Default Download Directory: "C:\File Transfer\"

Future Modiifications: 1. Proxy Settings
					   2. Working with Java Preferences API to save User Settings