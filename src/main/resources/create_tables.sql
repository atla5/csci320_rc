CREATE TABLE customers (
    account_number LONG PRIMARY KEY,
    cust_name VARCHAR(100) NOT NULL,
    phone_number VARCHAR(12),
    accumulated_points INT,

    check(accumulated_points >= 0),
    check(LENGTH(phone_number) >= 8)
);

CREATE TABLE stores (
    store_id LONG PRIMARY KEY,
    store_name VARCHAR(255) NOT NULL,
    opening_time VARCHAR(5),
    closing_time VARCHAR(5),
    addr_num INT,
    addr_street VARCHAR(255),
    addr_city VARCHAR(255),
    addr_state VARCHAR(255),
    addr_zipcode INT
);

CREATE TABLE products (
    upc_code LONG PRIMARY KEY,
    product_name VARCHAR(255) NOT NULL,
    weight INT,
    brand_name VARCHAR(255)
);

CREATE TABLE store_inventory (
    store_id LONG,
    product_id LONG,
    unit_price DECIMAL(15,2),
    quantity INT,

    PRIMARY KEY (store_id, product_id),
    FOREIGN KEY (store_id) REFERENCES stores(store_id),
    FOREIGN KEY (product_id) REFERENCES products(upc_code),
    check(unit_price >= 0),
    check(quantity >= 0)
);

CREATE TABLE store_purchases (
    purchase_id LONG PRIMARY KEY,
    store_id LONG NOT NULL,
    account_number LONG NOT NULL,
    online BOOLEAN NOT NULL,

    FOREIGN KEY (store_id) REFERENCES stores(store_id),
    FOREIGN KEY (account_number) REFERENCES customers(account_number)
);

CREATE TABLE product_purchases (
    purchase_id LONG,
    product_id LONG,
    quantity INT(255) NOT NULL,

    PRIMARY KEY (purchase_id, product_id),
    FOREIGN KEY (purchase_id) REFERENCES store_purchases(purchase_id),
    FOREIGN KEY (product_id) REFERENCES products(upc_code),
    check(quantity > 0)
);

CREATE TABLE vendors (
  vendor_id LONG PRIMARY KEY,
  vendor_name VARCHAR(255) NOT NULL,
  addr_num INT,
  addr_street VARCHAR(255),
  addr_city VARCHAR(255),
  addr_state VARCHAR(255),
  addr_zipcode VARCHAR(10)
);

CREATE TABLE vendor_distributions (
  vendor_id LONG,
  product_id LONG,
  unit_price NUMERIC(15,2),

  PRIMARY KEY (vendor_id, product_id),
  check(unit_price > 0)
);

CREATE TABLE shipments (
   shipment_id LONG PRIMARY KEY,
   store_id LONG,
   vendor_id LONG,

   FOREIGN KEY (store_id) REFERENCES stores(store_id),
   FOREIGN KEY (vendor_id)  REFERENCES vendors(vendor_id)
);

CREATE TABLE shipment_contents (
  shipment_id LONG,
  product_id LONG,
  quantity INT,

  PRIMARY KEY (shipment_id, product_id),
  check(quantity > 0)
);
