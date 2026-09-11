#version 330
uniform sampler2D InSampler;
in vec2 texCoord;
out vec4 fragColor;
void main() {
    vec2 pixel = 1.0 / vec2(textureSize(InSampler, 0));
    vec4 center = texture(InSampler, texCoord);
    vec4 normal = normalize(center);
    float vertical = dot(normal, normalize(texture(InSampler, texCoord - vec2(0.0, pixel.y))))
                   - dot(normal, normalize(texture(InSampler, texCoord + vec2(0.0, pixel.y))));
    float horizontal = dot(normal, normalize(texture(InSampler, texCoord + vec2(pixel.x, 0.0))))
                     - dot(normal, normalize(texture(InSampler, texCoord - vec2(pixel.x, 0.0))));
    fragColor = vec4(center.rgb * clamp(1.0 + 64.0 * (vertical + horizontal), 0.5, 2.0), 1.0);
}
