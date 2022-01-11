# The Zoo Ice Cream Parlour

Tools used:
* Intellij IDEA, SpringBoot initializer, Java 11, Maven 4
* Upgrade to 2.16.0 log4j

Assumptions:
* Order Item quantity and Order price are assumed as small numbers (i.e. strictly below max value), as we assume this is for a retail ice cream shop and quantities would be limited
* There is only up to one discount available per ice cream type
* There is only one location for the parlour, LocalDate will be consistent for the unique time zone

How to use:
* Run mvm clean install
* Start the ZooIceCreamParlourApplication app
