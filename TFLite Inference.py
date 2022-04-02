#Importing Libraries

import os
import librosa
import librosa.display
import matplotlib.pyplot as plt
import IPython.display as ipd
import random
import pandas as pd
import numpy as np

import tensorflow as tf
import tensorflow.keras as keras
from tensorflow.keras.layers import Dense, Dropout, Flatten
from tensorflow.keras import Input, layers, models
from tensorflow.keras import backend as K
from tensorflow.keras.utils import to_categorical
from sklearn.preprocessing import LabelEncoder

#Ignoring depriciated warnings for test purposes
import warnings
warnings.filterwarnings('ignore')

from google.colab import drive
drive.mount('/content/drive', force_remount=True)

UrduAssistantModel = tf.lite.Interpreter(model_path="drive/MyDrive/FYP/UrduAssistantLiteModel.h5.tflite")

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

  return(prediction)

"""# Testing"""

#Function to read Dataset
def readData_Librosa(path):
  
  try:
    audioData, audioSample_rate = librosa.load(path)
    Audio = (audioData, audioSample_rate)
    return Audio
            
  except:
    print("Couldn't Find: ", path)
    None


#Reading data
Audio = readData_Librosa("drive/MyDrive/FYP/Data/14/1. i191754.mp3")

def librosa_features_extractor(audios, sampleRates):

  mfccs_features = librosa.feature.mfcc(y=audios, sr=sampleRates, n_mfcc=40)
  mfccs_scaled_features = np.mean(mfccs_features.T,axis=0)

  return mfccs_scaled_features


extracted_features = librosa_features_extractor(Audio[0], Audio[1])
extracted_features_dataframe = pd.DataFrame(extracted_features,columns=['feature'])

X = np.array(extracted_features_dataframe['feature'].tolist())
X = X[..., np.newaxis]

X = np.reshape(X, (1,40,1,1), order='C')

output = runModel(UrduAssistantModel, X)

print("Predicted Class:" , output)