## Run this to install the pre-installed opencv-jar

`
mvn install:install-file -Dfile=/usr/share/java/opencv4/opencv-420.jar -DgroupId=org -DartifactId=opencv -Dversion=4.2.0 -Dpackaging=jar
`

## OpenCV Build For Java: You need version 3.4.2

Follow these instructions: 

1. https://opencv-java-tutorials.readthedocs.io/en/latest/01-installing-opencv-for-java.html

- Set build prefix to /usr/java/packages

2. Copy /usr/java/packages/share/OpenCV-3.4.2/java/libopencv_java342.so /usr/java/packages/lib

## Install Java-mtcnn

1. Navigate {$projecthome}/mtcnn-java
2. mvn package
3. mvn install:install-file -Dfile=<path-to-file> -DgroupId=<group-id> \
    -DartifactId=<artifact-id> -Dversion=<version> -Dpackaging=<packaging>

## Run Time Server

1. mvn package && mvn exec:java -PServer
2. mvn package && mvn exec:java -PClient

## Downloading Pre-Trained model

Model is required to be in a certain format. You may be able to generate arcface-model from another format.

However, for the application to work you must configure the application to point to the model folder. In the folder there should be a model-symbol.json and a model-0000.params.

Here is the location of the model where I have downloaded working pre-trained models.

https://www.dropbox.com/s/tj96fsm6t6rq8ye/model-r100-arcface-ms1m-refine-v2.zip?dl=0

## Cross Compatability

In dependencies, you may need to add windows support for nd4j. Only linux-x86_64-avx2 is on now.

## Gstreamer Segmentation Fault

Build opencv and remove GStreamer support

## Recognition Threshold

1. User can control how many frames are sent to the server. He does so within a session so we can control how many 
2. User can control which "group of faces" he is operating with

1. Server limits number of attempts by a single session (configurable).
2. Server has configurable threshold limits.

## 2020-01-17 Todo

1. Jenkins "Clean" build. 
2. Simple Demo Code through command line
