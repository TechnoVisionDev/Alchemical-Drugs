#version 330
uniform sampler2D InSampler;
in vec2 texCoord;
out vec4 fragColor;
void main() {
    vec3 color = texture(InSampler, texCoord).rgb;
    float luma = dot(color, vec3(0.30, 0.59, 0.11));
    fragColor = vec4(mix(vec3(luma), color, 0.2), 1.0);
}
