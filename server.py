from __future__ import print_function
import os
import librosa
import librosa.display
import matplotlib.pyplot as plt
import IPython.display as ipd
import random
import pandas as pd
import numpy as np
import sys
import soundfile as sf

import tensorflow as tf
import tensorflow.keras as keras
from tensorflow.keras.layers import Dense, Dropout, Flatten
from tensorflow.keras import Input, layers, models
from tensorflow.keras import backend as K
from tensorflow.keras.utils import to_categorical
from sklearn.preprocessing import LabelEncoder
import socket
import threading, wave, pickle, struct, time
from datetime import datetime
from datetime import timedelta
import socket  # Import socket module

# Ignoring depriciated warnings for test purposes
import warnings

warnings.filterwarnings('ignore')


import os
from google.cloud import speech
from googletrans import Translator

UrduAssistantModel = tf.lite.Interpreter(model_path="UrduAssistantLiteModel.h5.tflite")


def runModel(UrduAssistantModel, X):
    # Get input and output tensors.
    input_details = UrduAssistantModel.get_input_details()
    output_details = UrduAssistantModel.get_output_details()

    UrduAssistantModel.allocate_tensors()

    # Test model on random input data.
    input_data = np.array(X, dtype=np.float32)
    UrduAssistantModel.set_tensor(input_details[0]['index'], input_data)

    UrduAssistantModel.invoke()

    output_data = UrduAssistantModel.get_tensor(output_details[0]['index'])

    prediction = output_data.argmax()

    return (prediction)

    # Function to read Dataset


def readData_Librosa(path):
    try:
        audioData, audioSample_rate = librosa.load(path)
        Audio = (audioData, audioSample_rate)
        return Audio

    except:
        print("Couldn't Find: ", path)
        None


def librosa_features_extractor(audios, sampleRates):
    mfccs_features = librosa.feature.mfcc(y=audios, sr=sampleRates, n_mfcc=40)
    mfccs_scaled_features = np.mean(mfccs_features.T, axis=0)

    return mfccs_scaled_features


def modelInference(audio, sr):

    os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = 'urdu-assistant-6f2d9f7b0ad8.json'
    speech_client = speech.SpeechClient()

    with open("tempAudio.flac", 'rb') as f:
        mp3_data = f.read()

    audio_mp3 = speech.RecognitionAudio(content=mp3_data)

    # Configure Audio
    config_mp3 = speech.RecognitionConfig(

        sample_rate_hertz=sr,
        audio_channel_count=1,
        language_code='ur-PK'
    )

    # Transcribing the Audio
    response_standard_mp3 = speech_client.recognize(
        config=config_mp3,
        audio=audio_mp3
    )

    output_string = ""
    for result in response_standard_mp3.results:
        output_string += result.alternatives[0].transcript

    #print("\n\n")
    #for result in response_standard_mp3.results:
        #print("Transcript: ", output_string)
    #print("\n\n")

    translator = Translator()
    result = translator.translate(output_string, src='ur', dest='en')
    tokenized_output = result.text.split()
    return result


def predit_label(Audio, sr, UrduAssistantModel):
    extracted_features = librosa_features_extractor(Audio, sr)
    extracted_features_dataframe = pd.DataFrame(extracted_features, columns=['feature'])

    X = np.array(extracted_features_dataframe['feature'].tolist())
    X = X[..., np.newaxis]

    X = np.reshape(X, (1, 40, 1, 1), order='C')

    output = runModel(UrduAssistantModel, X)

    #print("Predicted Class from Model:", output)

    if output == 0:

        sf.write('tempAudio.flac', Audio, sr)
        #data1, samplerate1 = sf.read('existing_file.wav')
        result = modelInference(Audio, sr)
        os.remove("tempAudio.flac")

        #print("\n\n")
        #print(result.text)
        #print("\n\n")
        return result.text

    else:
        return output

## recieve audio

try:
    import cPickle as pickle
except ImportError:
    import pickle

sr = 22050

s = socket.socket()
s.bind((b'',3389))
s.listen(1)

def getAudio():

    while True:
        c,a = s.accept()
        data = b''
        while True:
            block = c.recv(4096)
            if not block: break
            data += block
        c.close()

        if sys.version_info.major < 3:
            unserialized_input = pickle.loads(data)
        else:
            unserialized_input = pickle.loads(data,encoding='bytes')

        output = predit_label(unserialized_input, sr, UrduAssistantModel)
        print("\n\nOutput: ", output)

        s.listen(1)
        c,a = s.accept()

        serialized_data1 = pickle.dumps(output, protocol=2)
        c.sendall(serialized_data1)
        c.close()

t1 = threading.Thread(target=getAudio, args=())
t1.start()