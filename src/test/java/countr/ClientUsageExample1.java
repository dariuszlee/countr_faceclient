package countr;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import countr.common.EmbeddingResponse;
import countr.common.MatchResult;
import countr.common.RecognitionResult;
import countr.faceclient.FaceClient;
import countr.faceclient.IFaceClient;

public class ClientUsageExample1 {
    @Test
    public void Test_AllCountRSamples() throws IOException {
        // Step 1: Load Face Client
        IFaceClient fc = null;
        try{
            fc = new FaceClient();
        }
        catch (Exception e){
            System.out.println(e);
            Assert.assertEquals(fc, "Server connection failed");
            return;
        }
        int groupId = 1;
        int maxResults = 2;

        // Step 2: Load embeddings and send to server
        Map<String, List<String>> resources = ClearAndLoadResources(fc, groupId);

        // Step 3: Load Test Resources and run match
        Map<String, List<String>> testResources = ClearAndLoadTestResources();
        for (String id : testResources.keySet()){
            System.out.println("Testing Id " + id);
            for (String path: testResources.get(id)){
                System.out.println("Testing path " + path);
                MatchResult mr = fc.Match(path, groupId, maxResults);
                System.out.println(mr);
            }
        }
    }

    public static HashMap<String, List<String>> idPaths(){
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String directoryPath = classloader.getResource("example_face_from_countr/ExampleDataFaceRecognition/ID_Images/").getFile();
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

    public static HashMap<String, List<String>> ClearAndLoadTestResources(){
        HashMap<String, List<String>> result = new HashMap<String, List<String>>();

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        String directoryPath = classloader.getResource("example_face_from_countr/ExampleDataFaceRecognition/Persons/").getFile();
        File directory = new File(directoryPath);
        File[] fileList = directory.listFiles();
        for(File file : fileList){
            String name = file.getName();
            File[] innerFiles = file.listFiles();
            for(File innerImage: innerFiles){
                if(innerImage.getName().contains(".png")){
                    String path = innerImage.getAbsolutePath();
                    List<String> list = result.get(name);
                    if(list == null){
                        list = new ArrayList<String>();
                    }
                    list.add(path);
                    result.put(name, list);
                }
            }
        }
        return result;
    }

    public static Map<String, List<String>> ClearAndLoadResources(IFaceClient fc, int groupId){

        // Step 1: Load Resources
        Map<String, List<String>> paths = ClientUsageExample1.idPaths();

        int pathCount = 0;
        for(List<String> idPaths: paths.values()){
            pathCount += idPaths.size();
        }

        EmbeddingResponse res = fc.GetEmbeddings(groupId);
        System.out.println("Embedding size " + res.getEmbeddings().size());
        System.out.println("Path size " + pathCount);
        if(res.getEmbeddings().size() != pathCount){
            // Step 2: Clear Group if all resources aren't loaded
            fc.DeleteGroup(groupId);

            for(String id : paths.keySet()){
                for(String path : paths.get(id)){
                    System.out.println("Adding to id: " + id + " path: " + path);
                    RecognitionResult rs = fc.AddPhoto(path, id, groupId);        
                    if (rs.isSuccess()){
                        System.out.println("Success!");
                    }
                    else{
                        System.out.println("Failed!");
                    }
                }
            }
        }
        else {
            System.out.println("Already Loaded!");
        }

        return paths;
    }
}
