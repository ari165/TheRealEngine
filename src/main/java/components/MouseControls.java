package components;

import TheRealEngine.GameObject;
import TheRealEngine.MouseListener;
import TheRealEngine.Window;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component {
    GameObject holdingObject = null;

    public void pickupObject(GameObject go){
        this.holdingObject = go;
        Window.getScene().addGameObjectToScene(go);
    }

    public void place(){
        this.holdingObject = null;
    }

    @Override
    public void update(float dt) {
        if (holdingObject != null){
            holdingObject.transform.position.x = MouseListener.getOrthoX() - 16; // - 16 to be at the point of the cursor
            holdingObject.transform.position.y = MouseListener.getOrthoY() - 16; // - 16 to be at the point of the cursor

            if (MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)){
                place();
            }
        }
    }
}
