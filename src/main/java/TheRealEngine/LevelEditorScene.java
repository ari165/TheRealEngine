package TheRealEngine;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import imgui.ImGui;
import org.joml.Vector2f;
import org.joml.Vector4f;
import statics.Static_Strings;
import util.AssetPool;

public class LevelEditorScene extends Scene{

    private GameObject obj1;

    public LevelEditorScene(){

    }

    @Override
    public void init(){
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        // create a game object, add a sprite renderer then add it to the scene
        obj1 = new GameObject("Object 1",
                new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 1);
        obj1.addComponent(new SpriteRenderer(new Vector4f(0, 1, 0, 1)));
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 2);

        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture(Static_Strings.blendImage2))));
        this.addGameObjectToScene(obj2);
    }

    private void loadResources(){
        // loading the default shader
        AssetPool.getShader(Static_Strings.defaultShader);

        // example of loading a spritesheet
        AssetPool.addSpritesheet(Static_Strings.spritesheet, new Spritesheet(AssetPool.getTexture(Static_Strings.spritesheet),
                16, 16, 26, 0));
    }

    @Override
    public void update(float dt) {


        // if you remove the codes bellow, the renderer and the components will stop working
        for (GameObject go : this.gameObjects){
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("tester");
        ImGui.text("wow im testing im gui");
        ImGui.end();
    }

    @Override
    public void onExit(){
        System.out.println("exited, bye");
    }
}
