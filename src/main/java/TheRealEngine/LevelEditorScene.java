package TheRealEngine;

import components.Sprite;
import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
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
        obj1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture(Static_Strings.blendImage1Path))));
        this.addGameObjectToScene(obj1);


        GameObject obj2 = new GameObject("Object 2",
                new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 2);

        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture(Static_Strings.blendImage2Path))));
        this.addGameObjectToScene(obj2);
    }

    private void loadResources(){
        // loading the default shader
        AssetPool.getShader(Static_Strings.defaultShaderPath);

        // example of loading a spritesheet
        AssetPool.addSpritesheet(Static_Strings.spritesheetPath, new Spritesheet(AssetPool.getTexture(Static_Strings.spritesheetPath),
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
}
