package TheRealEngine;

import components.SpriteRenderer;
import components.Spritesheet;
import org.joml.Vector2f;
import statics.Static_Strings;
import util.AssetPool;

public class LevelEditorScene extends Scene{

    private GameObject obj1;
    Spritesheet sprites;
    public LevelEditorScene(){

    }

    @Override
    public void init(){
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpritesheet(Static_Strings.spritesheetPath);

        obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObjectToScene(obj1);


        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprites.getSprite(15)));
        this.addGameObjectToScene(obj2);
    }

    private void loadResources(){
        AssetPool.getShader(Static_Strings.defaultShaderPath);

        AssetPool.addSpritesheet(Static_Strings.spritesheetPath, new Spritesheet(AssetPool.getTexture(Static_Strings.spritesheetPath),
                16, 16, 26, 0));
    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0){
           spriteFlipTimeLeft = spriteFlipTime;
           spriteIndex++;
           if (spriteIndex > 4){
               spriteIndex = 0;
           }
           obj1.getComponent(SpriteRenderer.class).SetSprite(sprites.getSprite(spriteIndex));
        }

        for (GameObject go : this.gameObjects){
            go.update(dt);
        }

        this.renderer.render();
    }
}
