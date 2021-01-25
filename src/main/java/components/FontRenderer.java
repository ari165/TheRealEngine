package components;

import TheRealEngine.Component;

public class FontRenderer extends Component {

    @Override
    public void start() {
        if (gameObject.getComponent(SpriteRenderer.class) != null){
            System.out.println("DUDE! there is a sprite renderer WOOOOOOOW");
        }
    }

    @Override
    public void update(float dt) {

    }
}
