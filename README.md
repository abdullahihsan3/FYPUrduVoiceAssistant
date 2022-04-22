
# Urdu Voice Assistant


A Personal Voice Assistant in Urdu Language to control your mobile device and perform everyday tasks.


## Requirements

To install the following components, please follow the React Native guide for your platform

Android <= 8.0

Google server in Executing state

  IP: 34.131.143.93
  Port: 3389


### Pre installed Apps

com.google.android.calculator
com.graph.weather.forecast.channel
com.shaiban.audioplayer.mplayer
## Deployment

To deploy the server on google cloud

```bash
python3 final_server.py
```
## Interface

Login Screen

![alt text](https://firebasestorage.googleapis.com/v0/b/fypurduvoiceassistant.appspot.com/o/uploads%2F1.jpeg?alt=media&token=d9067fe3-9fae-40fb-9da2-158ddcd6f830)

Register Screen


![alt text](https://firebasestorage.googleapis.com/v0/b/fypurduvoiceassistant.appspot.com/o/uploads%2F2.jpeg?alt=media&token=c08a0b3d-529e-4bfc-8214-0cb3b4bc5700)

## Main Screen

![alt text](https://firebasestorage.googleapis.com/v0/b/fypurduvoiceassistant.appspot.com/o/uploads%2F3.png?alt=media&token=ba4c83d7-dded-4ba8-8927-fef085529b91)

To perform the task firstly click on the Microphone icon and start recording.
Once the recording has started you may choose any of the commands from the list below:


- Flash light on karo
- Flash light band karo
- Flash light off karo
- Airplane mode on karo
- Airplane mode off karo
- Screenshot lo
- Awaz band kardo
- Awaz kam karo
- Awaz ziada kardo
- Mobile data on karo
- Mobile data off karo
- Teen bajey ka alarm lagao
- Reminder lagao
- Aaj mousam kesa hai
- Map kholo
- Time batao
- bluetooth band kar do
- bluetooth on kar do
- wifi on Karo
- wifi off Karo
- Brightness ziada Karo
- brightness kam karo
- YouTube kholo
- FaceBook kholo
- Browser kholo
- muja email bajni ha
- Email app kholo
- Tmhara nam kia ha?
- Time batao
- Date batao
- Music Player kholo

If the model is able to understand the command, the desired action will be performed if it contains the desired key words to perform the command
otherwise it will print a toast message which will say that the command is not understood.
