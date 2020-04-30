package countr;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.opencv.core.Mat;

import countr.common.VerifyResult;
import countr.faceclient.FaceClient;
import countr.faceclient.IFaceClient;
import junit.framework.Assert;

public class ClientCameraTests {

    public ClientCameraTests(){
        System.out.println("For performing camera tests, please look at camera without accessories (glasses, hat, etc)");
    }
    
    @Test
    public void Test_CameraVerifyWithoutNoise() throws IOException {
        IFaceClient fc = null;
        try{
            fc = new FaceClient();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }

        // Step 1: Delete database
        int arbitraryGroup = 2;
        fc.DeleteGroup(arbitraryGroup);

        // Step 2: Add User
        String userId = "test_subject_ME";
        Mat mat = fc.ReadCamera(0);
        fc.AddPhoto(mat, userId, arbitraryGroup);

        // Step 3: Verify New Result
        float verifyThreshold = new Float(0.5);
        // Thread.sleep(1000);
        Mat mat2 = fc.ReadCamera(0);
        VerifyResult res = fc.Verify(mat2, userId, arbitraryGroup, verifyThreshold);
        System.out.println(res);
        Assert.assertTrue(res.isSuccess());
    }

    @Test
    public void Test_CameraVerifyWithNoise() throws IOException {
        IFaceClient fc = null;
        try{
            fc = new FaceClient();
        }
        catch (Exception e){
            System.out.println(e);
            return;
        }

        // Step 1: Delete database
        int arbitraryGroup = 2;
        fc.DeleteGroup(arbitraryGroup);

        // Step 2: Add User and Database
        String userId = "test_subject_ME";
        Mat mat = fc.ReadCamera(0);
        fc.AddPhoto(mat, userId, arbitraryGroup);
        // Add Database
        SimpleClientTests1.LoadYaleFacesWithDb(fc, arbitraryGroup);

        // Step 3: Verify New Result
        float verifyThreshold = new Float(0.5);
        // Thread.sleep(1000);
        Mat mat2 = fc.ReadCamera(0);
        VerifyResult res = fc.Verify(mat2, userId, arbitraryGroup, verifyThreshold);
        System.out.println(res);
        Assert.assertTrue(res.isSuccess());
        Assert.assertEquals(userId, res.getTopMatch().getId());
    }

    // @Test
    // public void Test_CameraRecognition() throws IOException {
    //     IFaceClient fc = null;
    //     try{
    //         fc = new FaceClient();
    //     }
    //     catch (Exception e){
    //         System.out.println(e);
    //         return;
    //     }

    //     HashMap<String, List<String>> pathes = SimpleClientTests1.LoadYaleFaces();
    // }
}
