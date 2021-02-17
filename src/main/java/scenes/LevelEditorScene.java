package scenes;

import TheRealEngine.Camera;
import TheRealEngine.GameObject;
import TheRealEngine.Prefabs;
import TheRealEngine.Transform;
import components.*;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import statics.Static_Strings;
import util.AssetPool;

public class LevelEditorScene extends Scene {
    private Spritesheet sprites;

    GameObject levelEditorStuff = new GameObject("LevelEditor", new Transform(new Vector2f()), 0);

    public LevelEditorScene(){

    }

    @Override
    public void init(){
        levelEditorStuff.addComponent(new MouseControls());
        levelEditorStuff.addComponent(new GridLines());

        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpritesheet(Static_Strings.decorationsSheet);
        if (levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }
        GameObject object = new GameObject("a", new Transform(new Vector2f(0, 0), new Vector2f(59, 69)), 0);
        addGameObjectToScene(object);
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
        levelEditorStuff.update(dt);

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
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)){
                GameObject object = Prefabs.generateSpriteObject(sprite, 32, 32);
                // Attach to mouse cursor
                levelEditorStuff.getComponent(MouseControls.class).pickupObject(object);
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
