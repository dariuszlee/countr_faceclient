package countr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import countr.common.EmbeddingResponse;
import countr.common.MatchResult;
import countr.common.VerifyResult;
import countr.faceclient.FaceClient;
import countr.faceclient.IFaceClient;
import junit.framework.Assert;

public class SimpleClientTests1 {
    @Test
    public void Test_VerifyFail() throws IOException {
        // Step 1: Load Face Client
        IFaceClient fc = null;
        try{
            fc = new FaceClient();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }
        int groupId = 20;
        Double threshold = 0.5;

        // Load some files to server instance
        Map<String, List<String>> resources = ClientUsageExample1.ClearAndLoadResources(fc, groupId);

        // Load and Verify Image not in CountR ids
        String id1 = (String)resources.keySet().toArray()[0];
        String id2 = (String)resources.keySet().toArray()[1];
        String id2Path = resources.get(id2).get(0);

        VerifyResult vRes = fc.Verify(id2Path, id1, groupId, threshold.floatValue());
        System.out.println(vRes);
        Assert.assertFalse(vRes.isSuccess());
        Assert.assertEquals(vRes.getMessage(), "Verify Threshold not reached.");
    }

    @Test
    public void Test_VerifyPass() throws IOException {
        // Step 1: Load Face Client
        IFaceClient fc = null;
        try{
            fc = new FaceClient();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }
        int groupId = 20;
        Double threshold = 0.5;

        // Load some files to server instance
        Map<String, List<String>> resources = ClientUsageExample1.ClearAndLoadResources(fc, groupId);

        // Load some test files to verify against
        Map<String, List<String>> testResources = ClientUsageExample1.ClearAndLoadTestResources();
        System.out.println(testResources.keySet());
        // String id2 = (String)testResources.keySet().toArray()[1];
        String id2 = "JorgP";
        String id2InDatabase = "JorgID";
        List<String> id2Paths = testResources.get(id2);

        for (String id2Path : id2Paths){
            VerifyResult vRes = fc.Verify(id2Path, id2InDatabase, groupId, threshold.floatValue());
            System.out.println(vRes);
            System.out.println(id2Path);
            Assert.assertTrue(vRes.isSuccess());
        }
    }

    public static HashMap<String, List<String>> LoadYaleFaces(){
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String directoryPath = classloader.getResource("yalefaces/").getFile();
        File directory = new File(directoryPath);
        File[] fileList = directory.listFiles();
        for(File file : fileList){
            String name = file.getName().split("\\.")[0];
            String path = file.getAbsolutePath();
            List<String> list = result.get(name);
            if(list == null){
                list = new ArrayList<String>();
            }
            list.add(path);
            result.put(name, list);
        }
        return result;
    }

    public static HashMap<String, List<String>> LoadYaleFacesWithDb(IFaceClient fc, int groupId){
        HashMap<String, List<String>> yaleFiles = LoadYaleFaces();
        System.out.println(yaleFiles);
        for(String key: yaleFiles.keySet()){
            for(String path: yaleFiles.get(key)){
                fc.AddPhoto(path, key, groupId);
            }
        }
        return yaleFiles;
    }
}
