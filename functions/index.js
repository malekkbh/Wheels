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

exports.addMessage = functions.https.onRequest((req, res) => {
    // Grab the text parameter.
    const original = req.query.text;
    // Push the new message into the Realtime Database using the Firebase Admin SDK.
    admin.database().ref('/Notifications').push({original: original}).then(snapshot => {
      // Redirect with 303 SEE OTHER to the URL of the pushed object in the Firebase console.
      res.redirect(303, snapshot.ref);
    });
  });


