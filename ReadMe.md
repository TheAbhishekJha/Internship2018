# DigiLocker Requester API (JAVA)

Project contains a Button which fetches Documents/Images from the DigiLocker account of Logged In user.

### Requirements
1. Oracle Java 8 or higher.
2. Apache Tomcat Server 8 or higher.

#### File Locations
* JSP 
 
	 a. WebContent/DigiLocker.jsp - Contains the script for button 
	 b. WebContent/popup.jsp - opens the popup
 
* JAVA
 
	 a. org/DigiLocker/Curl/Curl.java - Handles the request and response from API
	 
* Configuration 

	 a. src/config.properties - Contains hardcoded credentials for the PullDocRequest XML extracted in Curl.java
	 
	 

