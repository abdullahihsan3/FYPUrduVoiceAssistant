import os
from google.cloud import speech

os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = 'urdu-assistant-6f2d9f7b0ad8.json'
speech_client = speech.SpeechClient()

#Load Local Audio: File Size<10mb, Length<1minute
media_file_name_mp3 = 'Mobile-data-band-karo.flac'

with open(media_file_name_mp3, 'rb') as f1:
	bytes_data_mp3 = f1.read()

audio_mp3 = speech.RecognitionAudio(content=bytes_data_mp3)

# Configure Audio
config_mp3 = speech.RecognitionConfig(

	sample_rate_hertz = 44100,
	audio_channel_count = 2,
	language_code = 'ur-PK',
)

# Transcribing the Audio
response_standard_mp3 = speech_client.recognize(
	config = config_mp3,
	audio = audio_mp3
)

# Print the response
#print(response_standard_mp3.results)
output_string = ""
for result in response_standard_mp3.results:
	output_string += result.alternatives[0].transcript

print("\n\n")
for result in response_standard_mp3.results:
    print("Transcript: ", output_string)
print("\n\n")

from googletrans import Translator

translator = Translator()
result = translator.translate(output_string, src='ur', dest='en')

print("\n\n")
print(result.text)
print("\n\n")

tokenized_output = result.text.split()

print("\n\n")
print(tokenized_output)
print("\n\n")
