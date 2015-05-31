package com.developworlds.flockingsample.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.HashMap;

/**
 * Created by benjamin-sergent on 5/30/15.
 */
public class TextureManager {
    private HashMap<String, Texture> textureMap = new HashMap<String, Texture>();
    private static TextureManager instance = new TextureManager();

    public Texture getTexture(String name) {
        Texture tex = textureMap.get(name);

        if (tex == null) {
            tex = new Texture(Gdx.files.internal(name));
            textureMap.put(name, tex);
        }

        return tex;
    }

    public void unloadTextures() {
        for(Texture tex : textureMap.values()) {
            tex.dispose();
        }

        textureMap.clear();
    }

    // I know people hate singletons, but texture management is
    // actually a good use for them.
    public static TextureManager get() {
        return instance;
    }
}
