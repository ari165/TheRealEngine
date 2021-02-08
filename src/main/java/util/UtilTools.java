package util;

public class UtilTools {
    private static float count = 0, sum = 0;
    public static float getFps(float dt){
        float fps = 1.0f / dt;
        sum += fps;
        count++;
        return fps;
    }

    public static void PrintAvgFps(){
        System.out.println("average fps: " + (sum / count));
    }
}
