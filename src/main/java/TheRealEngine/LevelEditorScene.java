package TheRealEngine;

import components.SpriteRenderer;
import org.joml.Vector2f;
import org.joml.Vector4f;
import statics.Static_Strings;
import util.AssetPool;

public class LevelEditorScene extends Scene{

    public LevelEditorScene(){

    }

    @Override
    public void init(){
        this.camera = new Camera(new Vector2f());


        loadResources();
    }

    private void loadResources(){
        AssetPool.getShader(Static_Strings.defaultShaderPath);
    }

    @Override
    public void update(float dt) {

        for (GameObject go : this.gameObjects){
            go.update(dt);
        }

        this.renderer.render();
    }
}
