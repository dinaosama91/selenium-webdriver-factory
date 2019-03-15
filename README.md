# Selenium Webdriver Factory
Start automation tests by initiating different browsers in different modes. 
You can run your tests locally, remotely, or in a grid using multiple browsers/platforms simultaneously. This library provides a utility to manage WebDriver instances. It helps to create, reuse and dismiss WebDriver instances.

## Factory Pattern
Factory Pattern is one of the creation Patterns. It is mostly used when we need to create an object from one of several possible classes that share a common super class / implements an interface. It creates objects without exposing the instantiation logic to the user. We, as the user, refer to the newly created object through a common interface.

### Factory Pattern in Creating WebDriver Instance
we can address this using a Factory Pattern. Test classes, as the users, should not really care how the drivers are actually created. What it needs is just a WebDriver instance to execute the given test case. So we come up with our own abstract class – DriverManager – which test classes could use to get a driver instance from it and use them in their tests.
