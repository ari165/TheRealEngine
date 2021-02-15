package scenes;

import TheRealEngine.Camera;
import TheRealEngine.GameObject;
import TheRealEngine.Prefabs;
import TheRealEngine.Transform;
import components.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector4f;
import statics.Static_Strings;
import util.AssetPool;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private SpriteRenderer obj1Sprite;
    private Spritesheet sprites;

    MouseControls mouseControls = new MouseControls();

    public LevelEditorScene(){

    }

    @Override
    public void init(){
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet(Static_Strings.decorationsSheet);
        if (levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        // create a game object, add a sprite renderer then add it to the scene
        obj1 = new GameObject("Object 1", new Transform(new Vector2f(200, 100), new Vector2f(256, 256)), 1);

        obj1Sprite = new SpriteRenderer();
        obj1Sprite.setColor(new Vector4f(1, 0, 0, 1));
        obj1.addComponent(obj1Sprite);
        obj1.addComponent(new Rigidbody());
        this.addGameObjectToScene(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)), 2);
        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
        Sprite obj2Sprite = new Sprite();
        obj2Sprite.setTexture(AssetPool.getTexture(Static_Strings.blendImage2));
        obj2SpriteRenderer.setSprite(obj2Sprite);
        obj2.addComponent(obj2SpriteRenderer);
        this.addGameObjectToScene(obj2);

    }

    private void loadResources(){
        // loading the default shader
        AssetPool.getShader(Static_Strings.defaultShader);

        // example of loading a spritesheet
        AssetPool.addSpritesheet(Static_Strings.decorationsSheet, new Spritesheet(AssetPool.getTexture(Static_Strings.decorationsSheet),
                16, 16, 81, 0));
        AssetPool.getTexture(Static_Strings.blendImage2);
    }

    @Override
    public void update(float dt) {
        mouseControls.update(dt);

        // if you remove the codes bellow, the renderer and the components will stop working
        for (GameObject go : this.gameObjects){
            go.update(dt);
        }
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("tester");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2.5f;
            float spriteHeight = sprite.getHeight() * 2.5f;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)){
                GameObject object = Prefabs.generateSpriteObject(sprite, spriteWidth, spriteHeight);
                // Attach to mouse cursor
                mouseControls.pickupObject(object);
            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < sprites.size() && nextButtonX2 < windowX2){
                ImGui.sameLine();
            }
        }

        ImGui.end();
    }

    @Override
    public void onExit(){

    }
}
