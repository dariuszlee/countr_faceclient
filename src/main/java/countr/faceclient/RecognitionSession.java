package countr.faceclient;

import java.util.HashMap;

public class RecognitionSession {
    HashMap<String, Float> scores;
    float[] embeddings;

    public RecognitionSession(float[] embeddings){
        this.embeddings = embeddings;
    }
}
