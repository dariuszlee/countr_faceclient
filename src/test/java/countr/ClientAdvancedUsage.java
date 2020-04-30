package countr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import countr.common.DetectFaceResult;
import countr.common.EmbeddingResponse;
import countr.common.MatchResult;
import countr.common.VerifyResult;
import countr.faceclient.FaceClient;
import countr.faceclient.IFaceClient;
import junit.framework.Assert;

public class ClientAdvancedUsage {
    @Test
    public void Test_MultipleFaces() throws IOException {
        // Step 1: Load Face Client
        IFaceClient fc = null;
        try{
            fc = new FaceClient();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String directoryPath = classloader.getResource("multi-face.jpg").getFile();
        
        DetectFaceResult res = fc.ContainsFace(directoryPath);
        System.out.println(res);

        int groupId = 2;

        fc.Recognize(directoryPath, groupId);
    }
}
