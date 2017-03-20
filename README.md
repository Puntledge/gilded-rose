# Overview

Gilded Rose is an online store that simulates Uber-style surge pricing.
It has a small inventory of items, GR1 through GR6, that can be viewed or purchased.
The is an initial stock of 10 of each item.
Retrieving all items doesn't affect the price of an item.
Retrieving one item is considered a "view" of the item which is is timestamped.
If an item has been viewed more than 10 times in the last hour, its price goes up by 10%.
If the number of views in the last hour drops below 10, the original price is restored.
Buying an item requires authentication and reduces its stock by the quantity purchased.
An item can only be purchased if there is sufficient stock.
Once the stock of an item drops to 0 it can no longer be purchased.

# How to install and run Gilded Rose on a Linux system

## Make sure you system has Java 1.8 and Maven 3.0

% yum install java maven

## Set JAVA_HOME environment variable

% export JAVA_HOME=/usr/lib/jvm/java

## Make local copy of gilded-rose and build it

% git clone http://github.com/Puntledge/gilded-rose

% cd gilded-rose

% mvn clean install

## Start the gilded-rose application

% mvn "-Dexec.args=-classpath %classpath com.gildedrose.store.Application" -Dexec.executable=/usr/lib/jvm/java/bin/java org.codehaus.mojo:exec-maven-plugin:1.2.1:exec

The application uses Spring Boot to launch a Tomcat web application server listening on localhost port 8080.

# The Gilded Rose REST API

## GET http:/localhost:8080/items

Returns array of items:

[
    {
        "name": "GR1",
        "description": "Item One",
        "price": 15
    }, {
        "name": "GR2",
        "description": "Item Two",
        "price": 25
    }, {
        "name": "GR3",
        "description": "Item Three",
        "price": 35
    }, {
        "name": "GR4",
        "description": "Item Four",
        "price": 45
    }, {
        "name": "GR5",
        "description": "Item Five",
        "price": 55
    }, {
        "name": "GR6",
        "description": "Item Six",
        "price": 65
    }
]

## GET http://localhost:8080/item/{name}

Returns item matching {name}

{
    "name": "GR1",
    "description": "Item One",
    "price": 15
}

## POST http://localhost:8080/item/{name}/{quantity}?login={login}&password={password}

Buys {quantity} items matching {name} using credentials {login} and {password}

Returns true if transaction successful; false if user not found, item not found, or insufficient stock

# Question? Comments?

To drop me a line, go to my GitHub [page](http://github.com/Puntledge) and click the email link below my profile/location info.
