
precision mediump float;

varying vec3 v_Color;
varying float v_ElapsedTime;

void main()
{
//  Brighten up young particles and dim old particles by dividing the color by the elapsed time.
    gl_FragColor = vec4(v_Color / v_ElapsedTime, 1.0);
}
