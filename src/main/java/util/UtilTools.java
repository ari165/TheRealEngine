package util;

// REMOVABLE
public class UtilTools {
    private static float count = 0, sum = 0;
    public static void getFps(float dt){
        float fps = 1.0f / dt;
        System.out.println("FPS: " + fps);
        sum += fps;
        count++;
    }
    public static void getAvgFps(){
        System.out.println("average fps: " + (sum / count));
    }
}
