package touchable.texteria.element.impl;

import touchable.texteria.util.ByteMap;

@SuppressWarnings("unused")
public class Image extends Rectangle {
    private boolean useCache = true;
    private boolean useGifStreaming = true;
    private int loadingColor = 0xFFFFFF;
    private float gifSpeed = 1.0f;
    private TextureRenderHint textureRenderHint = TextureRenderHint.CLAMP;
    private String image;
    private byte[] data;

    public Image(String id, float width, float height, String image) {
        super(id, width, height);
        this.image = image;
    }

    public Image setUseCache(boolean useCache) {
        this.useCache = useCache;
        return this;
    }

    public Image setUseGifStreaming(boolean useGifStreaming) {
        this.useGifStreaming = useGifStreaming;
        return this;
    }

    public Image setLoadingColor(int loadingColor) {
        this.loadingColor = loadingColor;
        return this;
    }

    public Image setGifSpeed(float gifSpeed) {
        this.gifSpeed = gifSpeed;
        return this;
    }

    public Image setTextureRenderHint(TextureRenderHint textureRenderHint) {
        this.textureRenderHint = textureRenderHint;
        return this;
    }

    public Image setImage(String image) {
        this.image = image;
        return this;
    }

    public Image setData(byte[] data) {
        this.data = data;
        return this;
    }

    @Override
    public String getElementType() {
        return "Image";
    }

    @Override
    public ByteMap write(ByteMap map) {
        super.write(map);

        if (!useCache) {
            map.put("cache", false);
        }
        if (!useGifStreaming) {
            map.put("gst", false);
        }
        if (loadingColor != 0xFFFFFF) {
            map.put("lc", loadingColor);
        }
        if (gifSpeed != 1.0f) {
            map.put("gs", gifSpeed);
        }
        if (textureRenderHint != TextureRenderHint.CLAMP) {
            map.put("trh", textureRenderHint.name());
        }
        if (data != null) {
            map.put("idata", data);
        }

        map.put("image", image);

        return map;
    }

    public enum TextureRenderHint {
        CLAMP, REPEAT, REPEAT_MIPMAP
    }
}

