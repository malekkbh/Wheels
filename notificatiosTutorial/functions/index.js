const functions = require('firebase-functions');


const admin = require('firebase-admin' ); 

admin.initializeApp(functions.config().firebase);


exports.sendNotifications = functions.database
.ref ('/Notifications/{topic}')
.onWrite( event => {
    var request = event.data.val() ; 

    var payload = {
        data:{
                topic: request.topic , 
                text: request.text,
                title: request.title

        },//data
    };//payload

    admin.messaging().sendToCondition(request.topic , payload)
    .then(function(response){
        console.log("Done!" , response );
    })
    .catch(function(err){
        console.log("error" , err) ; 
    })

    return admin.messaging().sendToTopic(request.topic, payload);


})//event


