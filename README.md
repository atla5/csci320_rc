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
|Model|`src/main/.../models`|shared programmatic understanding of db schema|
|View |`src/main/.../views`|implementation of UI in JavaFX|
|Controllers|`src/main/.../controllers`|main logic responsible for accessing DB with SQL|

This pattern helps us distribute functionality in a cleaner, less coupled manner,
  such that we can independently test and display the content and not have the 
  SQL directly exposed to the UI.
  
## Table Creation

### Customers


```sql
CREATE TABLE customers (
  account_number LONG PRIMARY KEY,
  cust_name VARCHAR(100) NOT NULL, 
  birth_date DATETIME NOT NULL, 
  male BOOLEAN default TRUE,
  ethnicity VARCHAR(20),
  phone_number INT,
  accumulated_points INT,
  
  check(accumulated_points >= 0),
);
```


### Store

```sql
CREATE TABLE stores (
  store_id LONG PRIMARY KEY,
  store_name VARCHAR(255) NOT NULL,
  opening_time TIME,
  closing_time TIME,
  addr_num INT,
  addr_street VARCHAR(255),
  addr_city VARCHAR(255),
  addr_state VARCHAR(255),
  addr_zipcode INT
);
```

### Purchases

```sql
CREATE TABLE store_purchases (
  purchase_id LONG PRIMARY KEY,
  store_id LONG NOT NULL,
  account_number LONG NOT NULL,
  date DATE NOT NULL, 
  total_price NUMERIC (15,2) NOT NULL,
  online BOOLEAN NOT NULL,

  FOREIGN KEY (store_id) REFERENCES stores(store_id),
  FOREIGN KEY (account_number) REFERENCES customers(account_number),
  check(total_price > 0)
);
```

```sql
CREATE TABLE product_purchases (
	purchase_id IDENTITY,
	product_id IDENTITY,
	quantity INT(255) NOT NULL,

	PRIMARY KEY (purchase_id, product_id),
	FOREIGN KEY (purchase_id) REFERENCES store_purchases(purchase_id),
	FOREIGN KEY (product_id) REFERENCES (product_id),
	check(quantity > 0)
);
```


### 