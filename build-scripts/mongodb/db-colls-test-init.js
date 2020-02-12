conn = new Mongo("tpcn-mongodb");

db = conn.getDB("test");

names = db.getCollectionNames();

if(!names.find(n => n === "status")) {
    db.createCollection("status", {
        capped: true,
        size: 10485760,
        max: 1
    });
}

if(!names.find(n => n === "userconfig")) {
    db.createCollection("userconfig", {
        validator: {
            $jsonSchema: {
                "bsonType": "object",
                "required": ["email", "notifiedChallenges", "scheduleTime", "tags"],
                "properties": {
                    "email": {
                        "bsonType": "string"
                    },
                    "notifiedChallenges": {
                        "bsonType": "array"
                    },
                    "scheduleTime" : {
                        "bsonType": "number"
                    },
                    "tags" : {
                        "bsonType": "array"
                    }
                }
            } 
        },
        validationAction: "warn"
    });
    
    db.userconfig.createIndex({scheduleTime: 1, email: 1});
}

if(!names.find(n => n === "user")) {
    db.createCollection("user", {
        validator: {
            $jsonSchema: {
                "bsonType": "object",
                "required": ["email", "password", "admin", "confirmed", "confirmToken"],
                "properties": {
                    "email": {
                        "bsonType": "string"
                    },
                    "password": {
                        "bsonType": "string"
                    },
                    "admin" : {
                        "bsonType": "bool"
                    },
                    "confirmed" : {
                        "bsonType": "bool"
                    },
                    "confirmToken" : {
                        "bsonType": "string"
                    }
                }
            }
        },
        validationAction: "warn"
    });
    
    db.user.createIndex({email: 1});   
    
    db.user.insertOne({
        email: "i@i.com",
        password: "5oHc_6SClk3bqvW8Wq-qPwVcxetcil04ms4MqGWcdm8",
        admin: true,
        confirmed: true,
        confirmToken: ""
    });
}
