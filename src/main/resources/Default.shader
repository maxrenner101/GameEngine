#type vertex
#version 330

layout(location = 0) in vec3 pos;

uniform mat4 proj;
uniform mat4 model;
uniform mat4 view;

void main() {
    gl_Position = proj * view * model * vec4(pos,1.0f);
}

#type fragment
#version 330

out vec4 color;

void main() {
    color = vec4(0.0f,1.0f,0.0f,1.0f);
}