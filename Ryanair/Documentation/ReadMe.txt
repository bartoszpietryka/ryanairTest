This is a simple project that will buy ticket on Ryanair page.
It's objective it's to show few typical programming techniques used in tests created with Selenium WebDriver 
and jUnit  . There are also some useful tricks. For details see in comments. 

Of course this is a short code and does not perform real testing or Ryanair web page.
It would take many days to creates such tests.

It is assumed that reader knows how to use jUnit in Eclipse IDE. 
Before test can be run, Java JDK 1.8, Eclipse, maven and chromedriver must be first installed. 
Path to chromedriver must be set in src/main/resources/test.properties file
After importing project to Eclipse, first use "Maven"->"Update Project".
I've used newest available Webdrivers, but it's a know fact that they offen don't work with most recent 
browser versions.

To run tests simply right click on src/tests/com.bart.test.testSuites.TestSuite1 file in Package Explorer 
and select "Run As" ->"JUnit test" . Status of tests will be visible in jUnit View . Progress of 
tests can be observed in console and logs. 

Interesting things in this project:
* Custom log4j 
* Custom exceptions
* Test that expect custom Exception to succeed 
* Page Object Design Pattern
* Singletons
* org.junit.rules.TestRule Polymorphism  for better logging
* java.util.Properties readed from file
* tests are run against both Firefox and chrome. It's done with org.junit.runners.Parameterized 
* Polymorphism of jUnit tags (i.e. @BeforeClass)
* jUnit test suites 
* multilevel exception handling 
* Maven
* .gitIgnore
* JavaDoc
 