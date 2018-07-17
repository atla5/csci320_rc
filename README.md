# csci320_rc

Retail (h2) database project made in fulfillment of RIT's course CSCI-320.

## Contributors

|name |rit email|github username|
|:---:|:-------:|:-------------:|
|Aidan Sawyer|aks5238|atla5|
|Graham Home|gmh5970|grahamhome|
|Abdullah|aaa1008|aaa1008|
|Ethan|exh3509|ethanol722|

## How to Run
```
$ mvn install
$ java ./src/main/java/com/rainforestcommerce/rcdb/RcdbApplication.java
``` 

## Design Rationale

We're implementing a basic Model-View-Controller pattern:

|     | path | description |
|:----|:-----|:------------|
|Model|`src/main/.../models`|shared programmatic understanding of db schema used by views|
|View |`src/main/.../views`|implementation of UI in JavaFX|
|Controllers|`src/main/.../controllers`|main logic responsible for accessing DB with SQL|

This pattern helps us distribute functionality in a cleaner, less coupled manner,
  such that we can independently test and display the content and not have the 
  SQL directly exposed to the UI.
