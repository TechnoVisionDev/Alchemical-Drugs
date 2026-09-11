#version 330
uniform sampler2D InSampler;
in vec2 texCoord;
out vec4 fragColor;
uniform sampler2D PrevSampler;
void main() {
    vec3 current = texture(InSampler, texCoord).rgb;
    vec3 previous = texture(PrevSampler, texCoord).rgb;
    fragColor = vec4(max(current, previous * 0.95), 1.0);
}
