// a = attribute, f = fragment, u = uniform
#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexId;

uniform mat4 uProjection; // uniform sticks around from object to object
uniform mat4 uView;

out vec4 fColor; // passes fColor to fragment
out vec2 fTexCoords;
out float fTexId;

void main()
{
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexId = aTexId;

    gl_Position = uProjection * uView * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor; // gets the out fColor from above
in vec2 fTexCoords;
in float fTexId;

uniform sampler2D uTextures[8]; // you can do more if needed

out vec4 color;

void main()
{
    if (fTexId > 0){
        int id = int(fTexId);
        color = fColor * texture(uTextures[id], fTexCoords);
    } else {
        color = fColor;
    }
}